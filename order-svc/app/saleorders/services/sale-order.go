package services

import (
	saleOrderRepo "github.com/HanawuZ/book-store-backend/order-svc/app/saleorders/repositories"
)

type ISaleOrderService interface {
	CreateSaleOrder()
}

type SaleOrderService struct {
	SaleOrderRepository saleOrderRepo.ISaleOrderRepository
}

func NewSaleOrderService(saleOrderRepository saleOrderRepo.ISaleOrderRepository) ISaleOrderService {
	return &SaleOrderService{SaleOrderRepository: saleOrderRepository}
}
