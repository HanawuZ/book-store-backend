package entities

import (
	"time"
)

// OrderStatus is an enum type for the order status field.
// OrderStatus represents the various states an order can have.
type OrderStatus string

const (
	AwaitingPayment     OrderStatus = "AWAITING_PAYMENT"
	AwaitingFulfillment OrderStatus = "AWAITING_FULLFILLMENT"
	Refunded            OrderStatus = "REFUNDED"
	AwaitingShipment    OrderStatus = "AWAITING_SHIPMENT"
	Shipping            OrderStatus = "SHIPPING"
	Completed           OrderStatus = "COMPLETED"
	CancelledByCustomer OrderStatus = "CANCELLED_BY_CUSTOMER"
	CancelledBySystem   OrderStatus = "CANCELLED_BY_SYSTEM"
)

// SaleOrder represents the sale_orders table.
type SaleOrder struct {
	ID                string      `gorm:"column:id;type:varchar(36);primaryKey"`
	OrderNo           string      `gorm:"column:order_no;not null"`
	OrderStatus       OrderStatus `gorm:"column:order_status;type:enum('AWAITING_PAYMENT', 'AWAITING_FULLFILLMENT', 'REFUNDED', 'AWAITING_SHIPMENT', 'SHIPPING', 'COMPLETED', 'CANCELLED_BY_CUSTOMER', 'CANCELLED_BY_SYSTEM');not null"`
	TotalQuantity     int         `gorm:"column:total_quantity;not null"`
	TotalPrice        float64     `gorm:"column:total_price;not null"`
	NetPrice          float64     `gorm:"column:net_price;not null"`
	ShippingAddress   *string     `gorm:"column:shipping_address;type:text"`
	ShippingLatitude  *float64    `gorm:"column:shipping_latitude"`
	ShippingLongitude *float64    `gorm:"column:shipping_longitude"`
	Street            *string     `gorm:"column:street;type:text"`
	SubDistrict       *string     `gorm:"column:sub_district;type:text"`
	District          *string     `gorm:"column:district;type:text"`
	Province          *string     `gorm:"column:province;type:text"`
	Country           *string     `gorm:"column:country;type:text"`
	Zipcode           *string     `gorm:"column:zipcode;type:text"`
	CreatedDate       time.Time   `gorm:"column:created_date;type:timestamp;autoCreateTime"`
	UpdatedDate       time.Time   `gorm:"column:updated_date;type:timestamp;autoUpdateTime"`
	Note              *string     `gorm:"column:note;type:text"`
	CustomerFirstName string      `gorm:"column:customer_firstname;type:text;not null"`
	CustomerLastName  *string     `gorm:"column:customer_lastname;type:text"`
	CustomerPhoneOne  *string     `gorm:"column:customer_phone_one;type:text"`
	CustomerPhoneTwo  *string     `gorm:"column:customer_phone_two;type:text"`
}

// TableName overrides the default table name.
func (SaleOrder) TableName() string {
	return "sale_orders"
}
