package services

import (
	"fmt"
	"net/http"
	"time"

	"github.com/HanawuZ/book-store-backend/order-svc/app/saleorders/models"
	"github.com/HanawuZ/book-store-backend/order-svc/models/entities"
	"github.com/HanawuZ/book-store-backend/order-svc/pkgs/helper"
	"github.com/HanawuZ/book-store-backend/order-svc/pkgs/http/httpserve"
	"github.com/google/uuid"
)

func (s *SaleOrderService) CreateSaleOrder(request models.CreateOrderRequest, customerId string) *httpserve.BaseResponse[string] {

	if request.CustomerAddressId == "" {
		response := httpserve.NewBaseResponse(http.StatusBadRequest, "customer address id is required", "")
		return &response
	}

	customerAddress, err := s.GrpcCustomerAddressServiceClientWrapper.GetCustomerAddress(request.CustomerAddressId)
	if err != nil {
		response := httpserve.NewBaseResponse(http.StatusInternalServerError, err.Error(), "")
		return &response
	}

	customer, err := s.GrpcCustomerServiceClientWrapper.GetCustomerById(customerId)
	if err != nil {
		response := httpserve.NewBaseResponse(http.StatusInternalServerError, err.Error(), "")
		return &response
	}

	cartItem, err := s.GrpcCartServiceClientWrapper.GetCartItemByCustomerProto(customerId)
	if err != nil {
		response := httpserve.NewBaseResponse(http.StatusInternalServerError, err.Error(), "")
		return &response
	}

	if len(cartItem) == 0 {
		response := httpserve.NewBaseResponse(http.StatusNoContent, "no cart item", "")
		return &response
	}

	var lastname, phoneOne, phoneTwo *string
	if customer.Lastname != nil && *customer.Lastname != "" {
		lastname = helper.Ptr(*customer.Lastname)
	}

	if customer.PhoneOne != nil && *customer.PhoneOne != "" {
		phoneOne = helper.Ptr(*customer.PhoneOne)
	}

	if customer.PhoneTwo != nil && *customer.PhoneTwo != "" {
		phoneTwo = helper.Ptr(*customer.PhoneTwo)
	}

	saleOrder := entities.SaleOrder{
		ID:                uuid.New().String(),
		OrderNo:           s.generateOrderNo(),
		OrderStatus:       entities.OrderStatus(entities.AwaitingPayment),
		TotalQuantity:     0,
		TotalPrice:        0,
		NetPrice:          0,
		ShippingAddress:   helper.Ptr(customerAddress.Address),
		ShippingLatitude:  helper.Ptr(customerAddress.Latitude),
		ShippingLongitude: helper.Ptr(customerAddress.Longitude),
		Street:            customerAddress.Street,
		SubDistrict:       customerAddress.SubDistrict,
		District:          customerAddress.District,
		Province:          customerAddress.Province,
		Country:           customerAddress.Country,
		Zipcode:           customerAddress.Zipcode,
		CreatedDate:       time.Now(),
		UpdatedDate:       time.Now(),
		Note:              request.Note,
		CustomerId:        customerId,
		CustomerFirstName: customer.Firstname,
		CustomerLastName:  lastname,
		CustomerPhoneOne:  phoneOne,
		CustomerPhoneTwo:  phoneTwo,
	}

	var saleItems []entities.SaleItem

	for _, ci := range cartItem {

		saleOrder.TotalQuantity += int(ci.Quantity)
		saleOrder.TotalPrice += (float64(ci.Quantity) * ci.Price)
		saleOrder.NetPrice += (float64(ci.Quantity) * ci.Price)

		publicationYear, err := time.Parse("2006-01-02", ci.PublicationYear)
		if err != nil {
			response := httpserve.NewBaseResponse(http.StatusInternalServerError, err.Error(), "")
			return &response
		}

		saleItems = append(saleItems, entities.SaleItem{
			ID:              uuid.New().String(),
			SaleOrderID:     saleOrder.ID,
			ProductID:       ci.BookId,
			Price:           ci.Price,
			Quantity:        int(ci.Quantity),
			ISBN:            ci.Isbn,
			Title:           ci.Title,
			Genre:           ci.Genre,
			PublicationYear: publicationYear,
			PublisherName:   ci.PublisherName,
			CreatedDate:     time.Now(),
			CreatedBy:       customerId,
			UpdatedDate:     time.Now(),
			UpdatedBy:       customerId,
		})
	}

	fmt.Printf("%#v\n", saleOrder)
	fmt.Printf("%#v\n", saleItems)

	err = s.SaleOrderRepository.CreateSaleOrder(saleOrder, saleItems)
	if err != nil {
		response := httpserve.NewBaseResponse(http.StatusInternalServerError, err.Error(), "")
		return &response
	}

	err = s.GrpcCartServiceClientWrapper.DeleteCartItemProto(customerId)
	if err != nil {
		response := httpserve.NewBaseResponse(http.StatusInternalServerError, err.Error(), "")
		return &response
	}
	response := httpserve.NewBaseResponse(http.StatusCreated, "success", "")
	return &response
}
