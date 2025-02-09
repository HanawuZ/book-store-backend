package logger

import (
	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/logger"
)

func NewFiberLogger() func(*fiber.Ctx) error {
	logger := logger.New()
	return logger
}
