package databases

import (
	"fmt"

	"github.com/HanawuZ/book-store-backend/order-svc/config"
	"github.com/HanawuZ/book-store-backend/order-svc/models/entities"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

type GormDatabase struct {
	Database
	DatabaseConnection *gorm.DB
}

type IGormDatabase interface {
	IDatabase
	GetDatabase() *gorm.DB
}

func New(config config.DatabaseConfig) IGormDatabase {
	return &GormDatabase{
		Database: Database{
			Username:     config.Username,
			Password:     config.Password,
			Host:         config.Host,
			Port:         config.Port,
			DatabaseName: config.DatabaseName,
		},
	}
}

func (d *GormDatabase) Connect() error {

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
		return err
	}

	db = db.Debug()

	fmt.Println("Successfully connected to database....")
	d.DatabaseConnection = db

	return nil
}

func (d *GormDatabase) Migrate() error {
	if d.DatabaseConnection == nil {
		return fmt.Errorf("database not connected, please connect first")
	}

	err := d.DatabaseConnection.AutoMigrate(
		&entities.SaleOrder{},
		&entities.SaleItem{},
	)

	if err != nil {
		return err
	}

	return nil
}

func (d *GormDatabase) GetDatabase() *gorm.DB {
	return d.DatabaseConnection
}
