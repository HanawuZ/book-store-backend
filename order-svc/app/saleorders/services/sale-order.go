package services

import (
	_grpcCart "github.com/HanawuZ/book-store-backend/order-svc/app/grpc/cart"
	saleOrderRepo "github.com/HanawuZ/book-store-backend/order-svc/app/saleorders/repositories"
	"github.com/HanawuZ/book-store-backend/order-svc/pkgs/http/httpserve"
)

type ISaleOrderService interface {
	CreateSaleOrder(customerId string) *httpserve.BaseResponse[string]
}

type SaleOrderService struct {
	SaleOrderRepository          saleOrderRepo.ISaleOrderRepository
	GrpcCartServiceClientWrapper _grpcCart.IGrpcCartServiceClientWrapper
}

func NewSaleOrderService(
	saleOrderRepository saleOrderRepo.ISaleOrderRepository,
	grpcCartServiceClientWrapper _grpcCart.IGrpcCartServiceClientWrapper,
) ISaleOrderService {
	return &SaleOrderService{
		SaleOrderRepository:          saleOrderRepository,
		GrpcCartServiceClientWrapper: grpcCartServiceClientWrapper,
	}
}
