package dbutil

import (
	"context"

	"gorm.io/gorm"
)

func WithTransaction(db *gorm.DB, ctx context.Context, fn func(tx *gorm.DB) error) error {
	tx := db.Begin().WithContext(ctx)
	if tx.Error != nil {
		return tx.Error
	}

	defer func() {
		if r := recover(); r != nil {
			tx.Rollback()
			panic(r)
		} else if err := tx.Commit().Error; err != nil {
			tx.Rollback()
		}
	}()

	if err := fn(tx); err != nil {
		tx.Rollback()
		return err
	}

	return nil
}
