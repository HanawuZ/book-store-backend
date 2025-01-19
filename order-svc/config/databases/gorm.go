package databases

import (
	"fmt"

	"github.com/HanawuZ/book-store-backend/order-svc/models/entities"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

type GormDatabase struct {
	DatabaseConfig
	Database *gorm.DB
}

type IGormDatabase interface {
	IDatabase
	GetDatabase() *gorm.DB
}

func New(databaseConfig DatabaseConfig) IGormDatabase {
	return &GormDatabase{
		DatabaseConfig: databaseConfig,
	}
}

func (d *GormDatabase) Connect() {

	dsn := fmt.Sprintf("%s:%s@tcp(%s:%s)/%s",
		d.Username,
		d.Password,
		d.Host,
		d.Port,
		d.DatabaseName,
	)

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

func (d *GormDatabase) GetDatabase() *gorm.DB {
	return d.Database
}
