package usecase

import (
	"context"
	"location-service/domain/entity/driver/gateway"
	"location-service/domain/entity/driver/model"
	"location-service/domain/exception"

	"github.com/google/uuid"
)

type LocationService interface {
	UpdateDriverLocation(ctx context.Context, loc model.DriverLocate) error
	GetNearbyDrivers(ctx context.Context, radius, lat, lon float64, status, carType string, limit int) ([]string, error)
}

type locationService struct {
	repo gateway.DriverLocateRepository
}

func NewLocationService(repo gateway.DriverLocateRepository) LocationService {
	return &locationService{repo: repo}
}

func (s *locationService) UpdateDriverLocation(ctx context.Context, loc model.DriverLocate) error {
	if loc.DriverId == uuid.Nil {
		return exception.ErrInvalidDriverID
	}
	if loc.Lat < -90 || loc.Lat > 90 || loc.Lon < -180 || loc.Lon > 180 {
		return exception.ErrInvalidCoordinate
	}
	return s.repo.SaveLocation(ctx, loc)
}

func (s *locationService) GetNearbyDrivers(ctx context.Context, radius, lat, lon float64, status, carType string, limit int) ([]string, error) {
	return s.repo.FindNearbyLocation(ctx, radius, lat, lon, status, carType, limit)
}
