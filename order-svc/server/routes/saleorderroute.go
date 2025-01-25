package routes

import (
	_grpcCart "github.com/HanawuZ/book-store-backend/order-svc/app/grpc/cart"
	_saleOrderController "github.com/HanawuZ/book-store-backend/order-svc/app/saleorders/controllers"
	_saleOrderRepo "github.com/HanawuZ/book-store-backend/order-svc/app/saleorders/repositories"
	_saleOrderSvc "github.com/HanawuZ/book-store-backend/order-svc/app/saleorders/services"
	"github.com/HanawuZ/book-store-backend/order-svc/config/databases"
	"github.com/HanawuZ/book-store-backend/order-svc/pkgs/middlewares"
	"github.com/gofiber/fiber/v2"
	"google.golang.org/grpc"
)

func SetupSaleOrderRoutes(
	router fiber.Router,
	database databases.IGormDatabase,
	grpcConnection *grpc.ClientConn,
	authMiddleware middlewares.IAuthorizationMiddleware,
) {

	grpcCartServiceClient := _grpcCart.NewGrpcCartServiceClient(grpcConnection)
	grpcCartServiceClientWrapper := _grpcCart.NewGrpcCartServiceClientWrapper(grpcCartServiceClient)

	db := database.GetDatabase()

	saleOrderRepository := _saleOrderRepo.NewSaleOrderRepository(db)
	saleOrderService := _saleOrderSvc.NewSaleOrderService(saleOrderRepository, grpcCartServiceClientWrapper)
	saleOrderController := _saleOrderController.NewSaleOrderController(saleOrderService)

	router.Get("/sale-order", func(c *fiber.Ctx) error {
		return c.SendString("GET Hello, World!, sale-order")
	})

	router.
		Use(authMiddleware.AuthorizationCustomerToken()).
		Post("/sale-order", saleOrderController.CreateSaleOrder)

	router.Put("/sale-order", func(c *fiber.Ctx) error {
		return c.SendString("PUT Hello, World!, sale-order")
	})

	router.Delete("/sale-order", func(c *fiber.Ctx) error {
		return c.SendString("DELETE Hello, World!, sale-order")
	})
}
