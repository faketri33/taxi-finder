package model

import (
	"context"
	"fmt"
	"location-service/domain/entity/driver/gateway"
	"location-service/domain/entity/driver/model"
	"time"

	"github.com/redis/go-redis/v9"
)

type redisLocationRepository struct {
	rdb          *redis.Client
	geoKey       string
	statusPrefix string
	typePrefix   string
	metaPrefix   string
}

func NewRedisLocationRepository(rdb *redis.Client) gateway.DriverLocateRepository {
	return &redisLocationRepository{
		rdb:          rdb,
		geoKey:       "drivers:geo",
		statusPrefix: "drivers:status:",
		typePrefix:   "drivers:carType:",
		metaPrefix:   "driver:",
	}
}

func (r *redisLocationRepository) SaveLocation(ctx context.Context, d model.DriverLocate) error {
	id := d.DriverId.String()
	metaKey := r.metaPrefix + id

	pipe := r.rdb.TxPipeline()

	pipe.GeoAdd(ctx, r.geoKey, &redis.GeoLocation{
		Name:      id,
		Longitude: d.Lon,
		Latitude:  d.Lat,
	})
	pipe.HSet(ctx, metaKey, map[string]any{
		"status":      d.Status,
		"carType":     d.CarType,
		"lastUpdated": d.LastUpdated.UTC().Format(time.RFC3339),
	})

	cmds, err := pipe.Exec(ctx)
	if err != nil {
		return err
	}

	prevStatus, prevType := parsePrevMeta(cmds[0])

	r.updateSetMembership(ctx, r.statusPrefix, id, prevStatus, d.Status)
	r.updateSetMembership(ctx, r.typePrefix, id, prevType, d.CarType)

	return nil
}

func parsePrevMeta(cmd redis.Cmder) (string, string) {
	slice, ok := cmd.(*redis.SliceCmd)
	if !ok {
		return "", ""
	}
	vals, _ := slice.Result()
	s1, _ := vals[0].(string)
	s2, _ := vals[1].(string)
	return s1, s2
}

func (r *redisLocationRepository) updateSetMembership(ctx context.Context, prefix, id, oldVal, newVal string) {
	if oldVal != "" && oldVal != newVal {
		r.rdb.SRem(ctx, prefix+oldVal, id)
	}
	if newVal != "" {
		r.rdb.SAdd(ctx, prefix+newVal, id)
	}
}

func (r *redisLocationRepository) FindNearbyLocation(
	ctx context.Context, params gateway.DriverSearchParams) ([]string, error) {
	geoTmp := tempKey("geo")
	filterTmp := tempKey("flt")
	resultTmp := tempKey("res")
	
	defer r.rdb.Del(ctx, geoTmp, filterTmp, resultTmp)

	if err := r.storeGeoNearby(ctx, geoTmp, float64(params.Radius), params.Lat, params.Lon); err != nil {
		return nil, err
	}

	if err := r.buildFilterSet(ctx, filterTmp, params.Status, params.CarType); err != nil {
		return nil, err
	}

	if err := r.intersectGeoAndFilter(ctx, resultTmp, geoTmp, filterTmp); err != nil {
		return nil, err
	}

	drivers, err := r.rdb.ZRange(ctx, resultTmp, 0, int64(params.Limit-1)).Result()
	if err != nil {
		return nil, err
	}

	return excludeDrivers(drivers, params.Exclude), nil
}

func tempKey(prefix string) string {
	return fmt.Sprintf("tmp:%s:%d", prefix, time.Now().UnixNano())
}

func (r *redisLocationRepository) storeGeoNearby(ctx context.Context, dst string, radius, lat, lon float64) error {
	_, err := r.rdb.GeoRadiusStore(ctx, r.geoKey, lon, lat, &redis.GeoRadiusQuery{
		Radius: radius,
		Unit:   "m",
		Sort:   "ASC",
		Store:  dst,
	}).Result()
	return err
}

func (r *redisLocationRepository) buildFilterSet(ctx context.Context, dst, status, carType string) error {
	keys := []string{
        r.statusPrefix + status,
        r.typePrefix + carType,
    }

    _, err := r.rdb.ZInterStore(ctx, dst, &redis.ZStore{
        Keys: keys,
    }).Result()

	return err;
}

func (r *redisLocationRepository) intersectGeoAndFilter(ctx context.Context, dst, geoTmp, fltTmp string) error {
	_, err := r.rdb.ZInterStore(ctx, dst, &redis.ZStore{
		Keys: []string{geoTmp, fltTmp},
	}).Result()

	return err
}

func excludeDrivers(drivers []string, exclude []string) []string {
	excludeMap := make(map[string]struct{}, len(exclude))
	for _, id := range exclude {
		excludeMap[id] = struct{}{}
	}

	result := make([]string, 0, len(drivers))
	for _, id := range drivers {
		if _, found := excludeMap[id]; !found {
			result = append(result, id)
		}
	}

	return result
}
