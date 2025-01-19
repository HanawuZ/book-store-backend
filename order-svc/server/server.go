package server

import (
	"github.com/HanawuZ/book-store-backend/order-svc/config"
)

type IServer interface {
	Start()
	Setup(config config.IAppConfig)
}
