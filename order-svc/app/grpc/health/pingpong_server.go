package pingpong

import context "context"

type pingpongServer struct {
}

func NewPingPongServer() PingPongServer {
	return &pingpongServer{}
}

func (s *pingpongServer) mustEmbedUnimplementedPingPongServer() {}

func (s *pingpongServer) StartPing(ctx context.Context, ping *Ping) (*Pong, error) {
	return &Pong{
		Id:      ping.Id,
		Message: "Received " + ping.Message,
	}, nil
}
