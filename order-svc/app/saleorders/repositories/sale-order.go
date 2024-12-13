package repositories

import (
	"github.com/HanawuZ/book-store-backend/order-svc/models/entities"
	"gorm.io/gorm"
)

type ISaleOrderRepository interface {
	CreateSaleOrder(saleOrder entities.SaleOrder, saleItems []entities.SaleItem) error
}

type SaleOrderRepository struct {
	database *gorm.DB
}

func NewSaleOrderRepository(database *gorm.DB) ISaleOrderRepository {
	return &SaleOrderRepository{
		database: database,
	}
}
