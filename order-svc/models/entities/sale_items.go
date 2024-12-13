package entities

import (
	"time"
)

// SaleItem represents the sale_items table.
type SaleItem struct {
	ID              string    `gorm:"column:id;type:varchar(36);primaryKey"`
	SaleOrderID     string    `gorm:"column:order_id;type:text;not null"` // Foreign key to SaleOrder
	ProductID       string    `gorm:"column:product_id;type:text;not null"`
	Price           float64   `gorm:"column:price;not null"`
	Quantity        int       `gorm:"column:quantity;not null"`
	ISBN            string    `gorm:"column:isbn;type:text"`
	Title           string    `gorm:"column:title;type:text"`
	Genre           string    `gorm:"column:genre;type:text"`
	PublicationYear time.Time `gorm:"column:publication_year;type:date"` // Date field
	PublisherName   string    `gorm:"column:publisher_name;type:text"`
	CreatedDate     time.Time `gorm:"column:created_date;type:timestamp;autoCreateTime"`
	CreatedBy       string    `gorm:"column:created_by;type:text"`
	UpdatedDate     time.Time `gorm:"column:updated_date;type:timestamp;autoUpdateTime"`
	UpdatedBy       string    `gorm:"column:updated_by;type:text"`
}

// TableName overrides the default table name.
func (SaleItem) TableName() string {
	return "sale_items"
}
