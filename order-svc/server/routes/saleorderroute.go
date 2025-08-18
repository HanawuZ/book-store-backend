package routes

import (
	_grpcCart "github.com/HanawuZ/book-store-backend/order-svc/app/grpc/cart"
	_grpcCustomer "github.com/HanawuZ/book-store-backend/order-svc/app/grpc/customer"
	_grpcCustomerAddress "github.com/HanawuZ/book-store-backend/order-svc/app/grpc/customer/address"
	_saleOrderController "github.com/HanawuZ/book-store-backend/order-svc/app/saleorders/controllers"
	_saleOrderRepo "github.com/HanawuZ/book-store-backend/order-svc/app/saleorders/repositories"
	_saleOrderSvc "github.com/HanawuZ/book-store-backend/order-svc/app/saleorders/services"
	"github.com/HanawuZ/book-store-backend/order-svc/config/databases"
	"github.com/HanawuZ/book-store-backend/order-svc/config/middleware/authorization"
	"github.com/gofiber/fiber/v2"
	"google.golang.org/grpc"
)

func SetupSaleOrderRoutes(
	router fiber.Router,
	database databases.IGormDatabase,
	grpcCatalogServiceClientConnection, grpcUserServiceClientConnection *grpc.ClientConn,
	authMiddleware authorization.IAuthorizationMiddleware,
) {

	grpcCartServiceClient := _grpcCart.NewGrpcCartServiceClient(grpcCatalogServiceClientConnection)
	grpcCartServiceClientWrapper := _grpcCart.NewGrpcCartServiceClientWrapper(grpcCartServiceClient)

	grpcCustomerAddressServiceClient := _grpcCustomerAddress.NewGrpcCustomerAddressServiceClient(grpcUserServiceClientConnection)
	grpcCustomerAddressServiceClientWrapper := _grpcCustomerAddress.NewGrpcCustomerAddressServiceClientWrapper(grpcCustomerAddressServiceClient)

	grpcCustomerServiceClient := _grpcCustomer.NewGrpcCustomerServiceClient(grpcUserServiceClientConnection)
	grpcCustomerServiceClientWrapper := _grpcCustomer.NewGrpcCustomerServiceClientWrapper(grpcCustomerServiceClient)

	db := database.GetDatabase()

	saleOrderRepository := _saleOrderRepo.NewSaleOrderRepository(db)
	saleOrderService := _saleOrderSvc.NewSaleOrderService(
		saleOrderRepository,
		grpcCartServiceClientWrapper,
		grpcCustomerAddressServiceClientWrapper,
		grpcCustomerServiceClientWrapper,
	)
	saleOrderController := _saleOrderController.NewSaleOrderController(saleOrderService)

	router.Get("/sale-order", func(c *fiber.Ctx) error {
		return c.SendString("GET Hello, World!, sale-order")
	})

	router.
		Use(authMiddleware.AuthorizationCustomerToken()).
		Post("/sale-order", saleOrderController.CreateSaleOrder).
		Get("/summary-order", saleOrderController.SummaryOrder)
	router.Put("/sale-order", func(c *fiber.Ctx) error {
		return c.SendString("PUT Hello, World!, sale-order")
	})

	router.Delete("/sale-order", func(c *fiber.Ctx) error {
		return c.SendString("DELETE Hello, World!, sale-order")
	})
}
