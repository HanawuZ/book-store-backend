package main

import (
	"github.com/HanawuZ/book-store-backend/order-svc/config/databases"
	"github.com/HanawuZ/book-store-backend/order-svc/server"
)

func main() {
	database := databases.NewDatabase()
	database.Connect()
	database.Migrate()

	server := server.New()
	server.Setup(database)
	server.Start()
}
