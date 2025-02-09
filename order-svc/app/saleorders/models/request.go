package models

type CreateOrderRequest struct {
	CustomerAddressId string  `json:"customerAddressId"`
	Note              *string `json:"note"`
}
