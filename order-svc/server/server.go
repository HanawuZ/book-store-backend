package server

import "github.com/HanawuZ/book-store-backend/order-svc/config/databases"

type IServer interface {
	Start()
	Setup(db databases.IDatabase)
}
