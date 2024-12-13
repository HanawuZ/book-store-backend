package dbutil

import (
	"context"
	"time"
)

func WithTimeout() (context.Context, context.CancelFunc) {
	return context.WithTimeout(context.Background(), 10*time.Second)
}
