package customer_address

import (
	context "context"
	"time"
)

type grpcCustomerAddressServiceClientWrapper struct {
	grpcCustomerAddressServiceClient GrpcCustomerAddressServiceClient
}

type IGrpcCustomerAddressServiceClientWrapper interface {
	GetCustomerAddress(customerAddressId string) (*GetCustomerAddressResponseProto, error)
}

func NewGrpcCustomerAddressServiceClientWrapper(grpcCustomerAddressServiceClient GrpcCustomerAddressServiceClient) IGrpcCustomerAddressServiceClientWrapper {
	return &grpcCustomerAddressServiceClientWrapper{
		grpcCustomerAddressServiceClient: grpcCustomerAddressServiceClient,
	}
}

func (c *grpcCustomerAddressServiceClientWrapper) contextWithTimeout() (context.Context, context.CancelFunc) {
	return context.WithTimeout(context.Background(), 10*time.Second)
}

func (c *grpcCustomerAddressServiceClientWrapper) GetCustomerAddress(customerAddressId string) (*GetCustomerAddressResponseProto, error) {
	ctx, cancel := c.contextWithTimeout()
	defer cancel()

	var inputArguments GetCustomerAddressRequestProto
	inputArguments.CustomerAddressId = customerAddressId

	results, err := c.grpcCustomerAddressServiceClient.GetCustomerAddress(ctx, &inputArguments)
	if err != nil {
		return nil, err
	}

	return results, nil
}
