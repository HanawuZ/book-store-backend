package main

import (
	"github.com/HanawuZ/book-store-backend/order-svc/config"
	"github.com/HanawuZ/book-store-backend/order-svc/server"
)

func main() {

	config := config.New()
	database := config.GetDatabase()

	database.Connect()
	database.Migrate()

	server := server.New()
	server.Setup(config)
	server.Start()
}
