package services

import (
	"fmt"
	"time"

	_grpcCart "github.com/HanawuZ/book-store-backend/order-svc/app/grpc/cart"
	_grpcCustomer "github.com/HanawuZ/book-store-backend/order-svc/app/grpc/customer"
	_grpcCustomerAddress "github.com/HanawuZ/book-store-backend/order-svc/app/grpc/customer/address"

	"github.com/HanawuZ/book-store-backend/order-svc/app/saleorders/models"
	saleOrderRepo "github.com/HanawuZ/book-store-backend/order-svc/app/saleorders/repositories"
	"github.com/HanawuZ/book-store-backend/order-svc/pkgs/http/httpserve"
)

type ISaleOrderService interface {
	CreateSaleOrder(request models.CreateOrderRequest, customerId string) *httpserve.BaseResponse[string]
}

type SaleOrderService struct {
	SaleOrderRepository                     saleOrderRepo.ISaleOrderRepository
	GrpcCartServiceClientWrapper            _grpcCart.IGrpcCartServiceClientWrapper
	GrpcCustomerAddressServiceClientWrapper _grpcCustomerAddress.IGrpcCustomerAddressServiceClientWrapper
	GrpcCustomerServiceClientWrapper        _grpcCustomer.IGrpcCustomerServiceClientWrapper
}

func NewSaleOrderService(
	saleOrderRepository saleOrderRepo.ISaleOrderRepository,
	grpcCartServiceClientWrapper _grpcCart.IGrpcCartServiceClientWrapper,
	grpcCustomerAddressServiceClientWrapper _grpcCustomerAddress.IGrpcCustomerAddressServiceClientWrapper,
	grpcCustomerServiceClientWrapper _grpcCustomer.IGrpcCustomerServiceClientWrapper,
) ISaleOrderService {
	return &SaleOrderService{
		SaleOrderRepository:                     saleOrderRepository,
		GrpcCartServiceClientWrapper:            grpcCartServiceClientWrapper,
		GrpcCustomerAddressServiceClientWrapper: grpcCustomerAddressServiceClientWrapper,
		GrpcCustomerServiceClientWrapper:        grpcCustomerServiceClientWrapper,
	}
}

func (s *SaleOrderService) generateOrderNo() string {
	currentDate := time.Now()
	formattedDate := currentDate.Format("20060102150405")
	return fmt.Sprintf("ORDER%s", formattedDate)
}
