package pingpong

import (
	context "context"
	"fmt"
)

type PingPongServerImpl struct {
	UnimplementedPingPongServer
}

func (s *PingPongServerImpl) StartPing(ctx context.Context, ping *Ping) (*Pong, error) {

	fmt.Println("Ping Received")

	resp := Pong{
		Id:      ping.Id,
		Message: "Received " + ping.Message,
	}

	return &resp, nil
}
