package services

import (
	"fmt"
	"net/http"
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
	SummaryOrder(customerId string) *httpserve.BaseResponse[*models.OrderSummary]
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

func (s *SaleOrderService) SummaryOrder(customerId string) *httpserve.BaseResponse[*models.OrderSummary] {
	if customerId == "" {
		var emptyOrderSummary *models.OrderSummary
		response := httpserve.NewBaseResponse(http.StatusBadRequest, "customer id is required", emptyOrderSummary)
		return &response
	}

	cartItem, err := s.GrpcCartServiceClientWrapper.GetCartItemByCustomerProto(customerId)
	if err != nil {
		var emptyOrderSummary *models.OrderSummary
		response := httpserve.NewBaseResponse(http.StatusInternalServerError, err.Error(), emptyOrderSummary)
		return &response
	}

	if len(cartItem) == 0 {
		var emptyOrderSummary *models.OrderSummary
		response := httpserve.NewBaseResponse(http.StatusNoContent, "no cart item", emptyOrderSummary)
		return &response
	}

	items := make([]models.OrderSummaryItem, 0)
	totalPrice := 0.0
	totalQuantity := 0

	for _, item := range cartItem {
		totalPrice += float64(item.Quantity) * item.Price
		totalQuantity += int(item.Quantity)
		items = append(items, models.OrderSummaryItem{
			BookId:          item.BookId,
			Quantity:        item.Quantity,
			CopiesAvailable: item.CopiesAvailable,
			Genre:           item.Genre,
			Isbn:            item.Isbn,
			Price:           item.Price,
			PublicationYear: item.PublicationYear,
			Title:           item.Title,
			PublisherName:   item.PublisherName,
		})
	}

	orderSummary := &models.OrderSummary{
		TotalPrice:    0,
		TotalQuantity: 0,
		Items:         items,
	}

	response := httpserve.NewBaseResponse(http.StatusOK, "ok", orderSummary)
	return &response
}
