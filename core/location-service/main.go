package main

import (
	"context"
	"location-service/domain/entity/driver/gateway"
	infrastructure "location-service/infastructure/location/gateway"
	"location-service/usecase"
	"os"

	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/log"
	"github.com/redis/go-redis/v9"
)

func main() {

	REDIS_URL := os.Getenv("REDIS_URL")

	rdb := redis.NewClient(&redis.Options{
		Addr: REDIS_URL,
	})

	if err := rdb.Ping(context.Background()).Err(); err != nil {
		log.Fatal(err)
	}

	repo := gateway.NewRedisLocationRepository(rdb)
	service := usecase.NewLocationService(repo)
	handler := infrastructure.NewLocationHandler(service)

	app := fiber.New()
	handler.RegisterRoutes(app)

	log.Fatal(app.Listen(":3000"))
}
