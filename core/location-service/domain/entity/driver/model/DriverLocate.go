package model

import (
	"time"

	"github.com/google/uuid"
)

type DriverLocate struct {
	DriverId    uuid.UUID
	Lat         float64
	Lon         float64
	Status      string
	CarType     string
	LastUpdated time.Time
}
