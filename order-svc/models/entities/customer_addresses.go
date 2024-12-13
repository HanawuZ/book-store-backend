package entities

import (
	"time"
)

// CustomerAddress represents the customer_addresses table.
type CustomerAddress struct {
	ID         string `gorm:"column:id;primaryKey"`
	CustomerID string `gorm:"column:customer_id;not null"` // Foreign Key to Customer
	// Customer    *Customer  `gorm:"foreignKey:CustomerID;constraint:OnUpdate:CASCADE,OnDelete:SET NULL"`
	Address     *string   `gorm:"column:address"`      // Nullable
	Latitude    *float64  `gorm:"column:latitude"`     // Nullable
	Longitude   *float64  `gorm:"column:longitude"`    // Nullable
	Street      *string   `gorm:"column:street"`       // Nullable
	SubDistrict *string   `gorm:"column:sub_district"` // Nullable
	District    *string   `gorm:"column:district"`     // Nullable
	Province    *string   `gorm:"column:province"`     // Nullable
	Country     *string   `gorm:"column:country"`      // Nullable
	Zipcode     *string   `gorm:"column:zipcode"`      // Nullable
	IsActive    bool      `gorm:"column:is_active;default:true"`
	CreatedDate time.Time `gorm:"column:created_date;autoCreateTime"`
	CreatedBy   string    `gorm:"column:created_by;not null"`
	UpdatedDate time.Time `gorm:"column:updated_date;autoUpdateTime"`
	UpdatedBy   string    `gorm:"column:updated_by;not null"`
}

// TableName overrides the default table name.
func (CustomerAddress) TableName() string {
	return "customer_addresses"
}
