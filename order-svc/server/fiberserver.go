package server

import (
	"github.com/HanawuZ/book-store-backend/order-svc/config/databases"
	"github.com/HanawuZ/book-store-backend/order-svc/config/logger"
	"github.com/HanawuZ/book-store-backend/order-svc/server/routes"
	"github.com/gofiber/fiber/v2"
)

type Server struct {
	App *fiber.App
}

func New() IServer {
	return &Server{
		App: fiber.New(),
	}
}

func (s *Server) Setup(db databases.IDatabase) {

	s.App.Use(logger.NewFiberLogger())

	routers := s.App.Group("/api/v1")
	routes.SetupSaleOrderRoutes(routers)

	s.App.Get("/", func(c *fiber.Ctx) error {
		return c.SendString("Hello, World!")
	})
}

func (s *Server) Start() {

	s.App.Listen(":3100")
}
