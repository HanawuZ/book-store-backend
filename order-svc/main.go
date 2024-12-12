package main

import "github.com/HanawuZ/book-store-backend/order-svc/server"

func main() {
	server := server.New()
	server.Start()
}
