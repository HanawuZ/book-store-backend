package entities

import (
	"time"
)

// Customer represents the customers table.
type Customer struct {
	ID          string    `gorm:"column:id;type:varchar(36);primaryKey"`
	FirstName   string    `gorm:"column:firstname;type:text;not null"`
	LastName    *string   `gorm:"column:lastname;type:text"`  // Nullable
	PhoneOne    *string   `gorm:"column:phone_one;type:text"` // Nullable
	PhoneTwo    *string   `gorm:"column:phone_two;type:text"` // Nullable
	CreatedDate time.Time `gorm:"column:created_date;type:timestamp;autoCreateTime"`
	CreatedBy   string    `gorm:"column:created_by;type:text;not null"`
	UpdatedDate time.Time `gorm:"column:updated_date;type:timestamp;autoUpdateTime"`
	UpdatedBy   string    `gorm:"column:updated_by;type:text;not null"`
}

// TableName overrides the default table name.
func (Customer) TableName() string {
	return "customers"
}
