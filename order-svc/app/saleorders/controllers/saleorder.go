package controllers

import (
	"github.com/HanawuZ/book-store-backend/order-svc/app/saleorders/services"
	"github.com/gofiber/fiber/v2"
)

type ISaleOrderController interface {
	CreateSaleOrder(c *fiber.Ctx) error
}

type SaleOrderController struct {
	SaleOrderService services.ISaleOrderService
}

func NewSaleOrderController(service services.ISaleOrderService) ISaleOrderController {
	return &SaleOrderController{
		SaleOrderService: service,
	}
}
