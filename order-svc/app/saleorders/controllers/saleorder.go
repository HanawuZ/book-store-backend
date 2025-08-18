package controllers

import (
	"net/http"

	"github.com/HanawuZ/book-store-backend/order-svc/app/saleorders/services"
	"github.com/HanawuZ/book-store-backend/order-svc/pkgs/http/httpserve"
	"github.com/HanawuZ/book-store-backend/order-svc/pkgs/jwt"
	"github.com/gofiber/fiber/v2"
)

type ISaleOrderController interface {
	CreateSaleOrder(c *fiber.Ctx) error
	SummaryOrder(c *fiber.Ctx) error
}

type SaleOrderController struct {
	SaleOrderService services.ISaleOrderService
}

func NewSaleOrderController(service services.ISaleOrderService) ISaleOrderController {
	return &SaleOrderController{
		SaleOrderService: service,
	}
}

func (s *SaleOrderController) SummaryOrder(c *fiber.Ctx) error {
	customerId, err := jwt.GetCustomerId(c)
	if err != nil {
		return c.Status(http.StatusUnauthorized).JSON(httpserve.NewBaseResponse(http.StatusUnauthorized, err.Error(), ""))
	}

	response := s.SaleOrderService.SummaryOrder(customerId)

	return c.Status(response.Code).JSON(response)

}
