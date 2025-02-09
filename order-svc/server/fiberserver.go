package server

import (
	"context"
	"log"
	"os/signal"
	"syscall"

	"github.com/HanawuZ/book-store-backend/order-svc/config"
	"github.com/HanawuZ/book-store-backend/order-svc/config/databases"
	"github.com/HanawuZ/book-store-backend/order-svc/config/logger"
	"github.com/HanawuZ/book-store-backend/order-svc/config/middleware/authorization"
	"github.com/HanawuZ/book-store-backend/order-svc/server/routes"
	"github.com/gofiber/fiber/v2"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
)

type Server struct {
	App                                *fiber.App
	Config                             *config.AppConfig
	Database                           databases.IDatabase
	AuthMiddleware                     authorization.IAuthorizationMiddleware
	GrpcCatalogServiceClientConnection *grpc.ClientConn
	GrpcUserServiceClientConnection    *grpc.ClientConn
}

func New() IServer {
	return &Server{
		App: fiber.New(),
	}
}

func (s *Server) Setup(config *config.AppConfig) {

	database := databases.New(config.Database)
	authMiddleware := authorization.New(config.Auth)

	s.AuthMiddleware = authMiddleware
	s.Database = database

	err := s.Database.Connect()
	if err != nil {
		panic(err)
	}

	err = s.Database.Migrate()
	if err != nil {
		panic(err)
	}

	s.App.Use(logger.NewFiberLogger())

	grpcCatalogServiceClientConnection, err := grpc.NewClient("localhost:9090", grpc.WithTransportCredentials(insecure.NewCredentials()))
	if err != nil {
		panic(err)
	}

	s.GrpcCatalogServiceClientConnection = grpcCatalogServiceClientConnection

	grpcUserServiceClientConnection, err := grpc.NewClient("localhost:5005", grpc.WithTransportCredentials(insecure.NewCredentials()))
	if err != nil {
		panic(err)
	}
	s.GrpcUserServiceClientConnection = grpcUserServiceClientConnection

	routers := s.App.Group("/api/v1")
	routes.SetupSaleOrderRoutes(routers, database, grpcCatalogServiceClientConnection, grpcUserServiceClientConnection, authMiddleware)

	s.App.Get("/", func(c *fiber.Ctx) error {
		return c.SendString("Hello, World!")
	})

	s.App.Use(authMiddleware.AuthorizationCustomerToken()).
		Get("/authorize", func(c *fiber.Ctx) error {
			return c.SendString("OK! Authorize!")
		})

}

func (s *Server) Start() {

	ctx, stop := signal.NotifyContext(context.Background(), syscall.SIGINT, syscall.SIGTERM)
	defer stop()

	go func() {
		if err := s.App.Listen(":3100"); err != nil {
			log.Fatalf("error starting server: %v\n", err)
		}
	}()

	<-ctx.Done()

	log.Println("got interruption signal.")

	if err := s.GrpcCatalogServiceClientConnection.Close(); err != nil {
		log.Printf("grpc catalog service client connection shutdown returned an err: %v\n", err)
	}
	log.Println("gracefully closed grpc catelog svc client connection.")

	if err := s.GrpcUserServiceClientConnection.Close(); err != nil {
		log.Printf("grpc user service client connection shutdown returned an err: %v\n", err)
	}
	log.Println("gracefully closed grpc user svc client connection.")

	if err := s.App.ShutdownWithContext(context.TODO()); err != nil {
		log.Printf("server shutdown returned an err: %v\n", err)
	}
	log.Println("gracefully closed server appilcation.")
}
