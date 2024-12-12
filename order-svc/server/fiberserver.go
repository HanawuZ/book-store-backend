package server

import (
	"github.com/HanawuZ/book-store-backend/order-svc/config/logger"
	"github.com/gofiber/fiber/v2"
)

type Server struct {
	app *fiber.App
}

func New() IServer {
	return &Server{
		app: fiber.New(),
	}
}

func (s *Server) Start() {
	s.app.Use(logger.NewFiberLogger())

	s.app.Get("/", func(c *fiber.Ctx) error {
		return c.SendString("Hello, World!")
	})
	s.app.Listen(":3000")
}
