package infrastructure

import (
	"context"
	"location-service/domain/entity/driver/gateway"
	"location-service/domain/entity/driver/model"
	"location-service/usecase"
	"strconv"

	"strings"

	"github.com/gofiber/fiber/v2"
)

type LocationHandler struct {
	service usecase.LocationService
}

func NewLocationHandler(service usecase.LocationService) *LocationHandler {
	return &LocationHandler{service: service}
}

func (h *LocationHandler) RegisterRoutes(app *fiber.App) {
	app.Post("/drivers/location", h.UpdateLocation)
	app.Get("/drivers/nearby", h.GetNearby)
}

func (h *LocationHandler) UpdateLocation(c *fiber.Ctx) error {
	var loc model.DriverLocate
	if err := c.BodyParser(&loc); err != nil {
		return fiber.NewError(fiber.StatusBadRequest, err.Error())
	}
	
	err := h.service.UpdateDriverLocation(context.Background(), loc)
	if err != nil {
		return fiber.NewError(fiber.StatusBadRequest, err.Error())
	}

	return c.JSON(fiber.Map{"status": "updated"})
}

func (h *LocationHandler) GetNearby(c *fiber.Ctx) error {
	lat, _ := strconv.ParseFloat(c.Query("lat"), 64)
	lon, _ := strconv.ParseFloat(c.Query("lon"), 64)
	excludeStr := strings.Split(c.Query("exclude", ""), ",")

	distanceStr := c.Query("distance", "3000")
	carType := c.Query("carType", "")
	status := c.Query("status", "")
	limitStr := c.Query("limit", "10")

	distance, err := strconv.ParseFloat(distanceStr, 64)
	if err != nil {
		distance = 3000
	}

	limit, err := strconv.Atoi(limitStr)
	if err != nil {
		limit = 10
	}

	drivers, err := h.service.GetNearbyDrivers(context.Background(), gateway.DriverSearchParams{
		Radius:  int(distance),
		Lat:     lat,
		Lon:     lon,
		CarType: carType,
		Status:  status,
		Limit:   limit,
		Exclude: excludeStr,
	})

	if err != nil {
		return fiber.NewError(fiber.StatusInternalServerError, err.Error())
	}
	return c.JSON(drivers)
}
