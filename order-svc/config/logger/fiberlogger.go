package logger

import (
	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/logger"
)

func NewFiberLogger() func(*fiber.Ctx) error {
	loggerConfig := logger.Config{
		Format: "[${ip}]:${port} ${status} - ${method} ${path}\n",
	}
	logger := logger.New(loggerConfig)
	return logger
}
