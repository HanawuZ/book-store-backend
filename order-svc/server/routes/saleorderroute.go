package routes

import (
	"github.com/gofiber/fiber/v2"
)

func SetupSaleOrderRoutes(router fiber.Router) {
	router.Get("/sale-order", func(c *fiber.Ctx) error {
		return c.SendString("GET Hello, World!, sale-order")
	})

	router.Post("/sale-order", func(c *fiber.Ctx) error {
		return c.SendString("POST Hello, World!, sale-order")
	})

	router.Put("/sale-order", func(c *fiber.Ctx) error {
		return c.SendString("PUT Hello, World!, sale-order")
	})

	router.Delete("/sale-order", func(c *fiber.Ctx) error {
		return c.SendString("DELETE Hello, World!, sale-order")
	})
}
