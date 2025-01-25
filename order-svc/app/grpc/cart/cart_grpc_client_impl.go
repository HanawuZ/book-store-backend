package cart

import (
	context "context"
	"time"
)

type grpcCartServiceClientWrapper struct {
	grpcCartServiceClient GrpcCartServiceClient
}

type IGrpcCartServiceClientWrapper interface {
	GetCartItemByCustomerProto(customerId string) ([]*CartItemProto, error)
}

func NewGrpcCartServiceClientWrapper(grpcCartServiceClient GrpcCartServiceClient) IGrpcCartServiceClientWrapper {
	return &grpcCartServiceClientWrapper{
		grpcCartServiceClient: grpcCartServiceClient,
	}
}

func (c *grpcCartServiceClientWrapper) contextWithTimeout() (context.Context, context.CancelFunc) {
	return context.WithTimeout(context.Background(), 10*time.Second)
}

func (c *grpcCartServiceClientWrapper) GetCartItemByCustomerProto(customerId string) ([]*CartItemProto, error) {
	ctx, cancel := c.contextWithTimeout()
	defer cancel()

	var inputArguments CartItemRequestProto
	inputArguments.CustomerId = customerId

	results, err := c.grpcCartServiceClient.GetCartItemByCustomerProto(ctx, &inputArguments)
	if err != nil {
		return nil, err
	}

	return results.Items, nil
}
