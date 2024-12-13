package repositories

import (
	"github.com/HanawuZ/book-store-backend/order-svc/models/entities"
	"github.com/HanawuZ/book-store-backend/order-svc/pkgs/dbutil"
	"gorm.io/gorm"
)

func (r *SaleOrderRepository) CreateSaleOrder(saleOrder entities.SaleOrder, saleItems []entities.SaleItem) error {
	ctx, cancel := dbutil.WithTimeout()
	defer cancel()

	if err := dbutil.WithTransaction(r.database, ctx, func(tx *gorm.DB) error {
		if err := tx.Create(&saleOrder).Error; err != nil {
			return err
		}

		if err := tx.Create(&saleItems).Error; err != nil {
			return err
		}

		return nil
	}); err != nil {
		return err
	}

	return nil
}
