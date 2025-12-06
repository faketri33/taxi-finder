package gateway

import (
	"context"
	"location-service/domain/entity/driver/model"
)

type DriverLocateRepository interface {
	SaveLocation(ctx context.Context, loc model.DriverLocate) error
	FindNearbyLocation(ctx context.Context, params DriverSearchParams ) ([]string, error)
}

type DriverSearchParams struct {
    Lat      float64
    Lon      float64
    Radius   int
    Limit    int
    CarType  string
    Status   string
    Exclude  []string
}