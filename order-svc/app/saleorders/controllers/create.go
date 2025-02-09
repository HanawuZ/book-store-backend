package controllers

import (
	"net/http"

	"github.com/HanawuZ/book-store-backend/order-svc/app/saleorders/models"
	"github.com/HanawuZ/book-store-backend/order-svc/pkgs/http/httpserve"
	"github.com/HanawuZ/book-store-backend/order-svc/pkgs/jwt"
	"github.com/gofiber/fiber/v2"
)

func (h *SaleOrderController) CreateSaleOrder(c *fiber.Ctx) error {
	customerId, err := jwt.GetCustomerId(c)
	if err != nil {
		return c.Status(http.StatusUnauthorized).JSON(httpserve.NewBaseResponse(http.StatusUnauthorized, err.Error(), ""))
	}

	var request models.CreateOrderRequest

	if err := c.BodyParser(&request); err != nil {
		return c.Status(http.StatusBadRequest).JSON(httpserve.NewBaseResponse(http.StatusBadRequest, err.Error(), ""))
	}

	response := h.SaleOrderService.CreateSaleOrder(request, customerId)

	return c.Status(response.Code).JSON(response)
}
