package databases

import (
	"fmt"

	"github.com/HanawuZ/book-store-backend/order-svc/models/entities"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

type GormDatabase struct {
	Database *gorm.DB
}

func NewDatabase() IDatabase {
	return &GormDatabase{}
}

func (d *GormDatabase) Connect() {
	dsn := "root:G5!kT@2y9B#zU8%w@tcp(localhost:3306)/bookshop-order"
	dial := mysql.Open(dsn)
	db, err := gorm.Open(dial)
	if err != nil {
		fmt.Println("Error connecting to database")
		panic(err)
	}

	fmt.Println("Successfully connected to database....")
	d.Database = db
}

func (d *GormDatabase) Migrate() {
	if d.Database == nil {
		panic("Database not connected, please connect first")
	}

	err := d.Database.AutoMigrate(
		&entities.SaleOrder{},
		&entities.SaleItem{},
	)

	if err != nil {
		panic(err)
	}
}
