package main

import (
	"github.com/HanawuZ/book-store-backend/order-svc/config"
	"github.com/HanawuZ/book-store-backend/order-svc/server"
)

func main() {

	config, err := config.LoadConfig()
	if err != nil {
		panic(err)
	}

	server := server.New()
	server.Setup(config)
	server.Start()
}
