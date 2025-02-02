using Grpc.Core;

namespace UserService.Apps.HealthCheck.PingPong
{
    public class PingPongGrpcServerImpl: PingPong.PingPongBase
    {
        public PingPongGrpcServerImpl() { }

        public override Task<Pong> StartPing(Ping request, ServerCallContext context)
        {
            Console.WriteLine("Called Start Ping in C#");
            Pong pong = new Pong()
            {
                Id = request.Id,
                Message = $"Pong, {request.Message}",
            };

            return Task.FromResult(pong);
        }
    }
}
