package customer

import (
	context "context"
	"time"
)

type grpcCustomerServiceClientWrapper struct {
	grpcCustomerServiceClient GrpcCustomerServiceClient
}

type IGrpcCustomerServiceClientWrapper interface {
	GetCustomerById(customerId string) (*GetCustomerResponseProto, error)
}

func NewGrpcCustomerServiceClientWrapper(grpcCustomerServiceClient GrpcCustomerServiceClient) IGrpcCustomerServiceClientWrapper {
	return &grpcCustomerServiceClientWrapper{
		grpcCustomerServiceClient: grpcCustomerServiceClient,
	}
}

func (c *grpcCustomerServiceClientWrapper) contextWithTimeout() (context.Context, context.CancelFunc) {
	return context.WithTimeout(context.Background(), 10*time.Second)
}

func (c *grpcCustomerServiceClientWrapper) GetCustomerById(customerId string) (*GetCustomerResponseProto, error) {
	ctx, cancel := c.contextWithTimeout()
	defer cancel()

	var inputArguments GetCustomerRequestProto
	inputArguments.CustomerId = customerId

	results, err := c.grpcCustomerServiceClient.GetCustomerById(ctx, &inputArguments)
	if err != nil {
		return nil, err
	}

	return results, nil

}
