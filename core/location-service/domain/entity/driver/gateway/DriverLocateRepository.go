package gateway

import (
	"context"
	"fmt"
	"location-service/domain/entity/driver/model"
	"time"

	"github.com/redis/go-redis/v9"
)

type DriverLocateRepository interface {
	SaveLocation(ctx context.Context, loc model.DriverLocate) error
	FindNearbyLocation(ctx context.Context, radius, lat, lon float64, status, carType string, limit int) ([]string, error)
}
type redisLocationRepository struct {
	client *redis.Client
	key    string
}

const DRIVER_STATUS = "drivers:status:"

const DRIVER_CAR_TYPE = "drivers:carType:"

func NewRedisLocationRepository(client *redis.Client) DriverLocateRepository {
	return &redisLocationRepository{
		client: client,
		key:    "drivers",
	}
}

func (r *redisLocationRepository) SaveLocation(ctx context.Context, d model.DriverLocate) error {
	pipe := r.client.TxPipeline()

	oldData, _ := pipe.HMGet(context.Background(), "driver:"+d.DriverId.String(), "status", "carType").Result()
	oldStatus, _ := oldData[0].(string)
	oldCarType, _ := oldData[1].(string)

	addGeo(pipe, d)
	updateStatus(pipe, d.DriverId.String(), oldStatus, d.Status)
	updateCarType(pipe, d.DriverId.String(), oldCarType, d.CarType)
	updateMeta(pipe, d)
	_, err := pipe.Exec(ctx)
	return err
}

func (r *redisLocationRepository) FindNearbyLocation(ctx context.Context, radius, lat, lon float64, status, carType string, limit int) ([]string, error) {
	tempKey := fmt.Sprintf("temp:geo:%d", time.Now().UnixNano())
	defer r.client.Del(ctx, tempKey)

	_, err := r.client.GeoRadiusStore(ctx, "geo:drivers", lon, lat, &redis.GeoRadiusQuery{
		Radius: radius,
		Unit:   "m",
		Count:  limit * 3,
		Store:  tempKey,
		Sort:   "ASC",
	}).Result()
	if err != nil {
		return nil, err
	}

	ids, err := r.client.SInter(ctx, tempKey, DRIVER_STATUS+status, DRIVER_CAR_TYPE+carType).Result()
	if err != nil {
		return nil, err
	}
	return ids[:limit], nil
}

func addGeo(pipe redis.Pipeliner, d model.DriverLocate) {
	pipe.GeoAdd(context.Background(), "geo:drivers", &redis.GeoLocation{
		Name: d.DriverId.String(), Latitude: d.Lat, Longitude: d.Lon,
	})
}

func updateStatus(pipe redis.Pipeliner, driverID, oldStatus, status string) {
	updateMetaIfHasDifference(pipe, driverID, DRIVER_STATUS, oldStatus, status)
}

func updateCarType(pipe redis.Pipeliner, driverID, oldCarType, carType string) {
	updateMetaIfHasDifference(pipe, driverID, DRIVER_CAR_TYPE, oldCarType, carType)
}

func updateMetaIfHasDifference(pipe redis.Pipeliner, driverID, field, oldData, newData string) {
	if oldData != "" && oldData != newData {
		pipe.SRem(context.Background(), field+oldData, driverID)
		pipe.SAdd(context.Background(), field+newData, driverID)
	}
}

func updateMeta(pipe redis.Pipeliner, d model.DriverLocate) {
	pipe.HSet(context.Background(), "driver:"+d.DriverId.String(), map[string]any{
		"status":      d.Status,
		"carType":     d.CarType,
		"lastUpdated": d.LastUpdated.UTC().Format(time.RFC3339),
	})
}
