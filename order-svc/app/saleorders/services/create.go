package services

import (
	"fmt"
	"net/http"
	"time"

	"github.com/HanawuZ/book-store-backend/order-svc/pkgs/http/httpserve"
)

func (s *SaleOrderService) CreateSaleOrder(customerId string) *httpserve.BaseResponse[string] {

	startTime := time.Now()

	defer func() {
		// Calculate and print the elapsed time
		elapsedTime := time.Since(startTime)
		fmt.Printf("Execution time for CreateSaleOrder: %s\n", elapsedTime)
	}()

	cartItem, err := s.GrpcCartServiceClientWrapper.GetCartItemByCustomerProto(customerId)
	if err != nil {
		response := httpserve.NewBaseResponse(http.StatusInternalServerError, err.Error(), "")
		return &response
	}

	for _, c := range cartItem {
		fmt.Println("cart item: ", c.Title, c.Genre)
	}

	response := httpserve.NewBaseResponse(http.StatusCreated, "success", "")
	return &response
}
