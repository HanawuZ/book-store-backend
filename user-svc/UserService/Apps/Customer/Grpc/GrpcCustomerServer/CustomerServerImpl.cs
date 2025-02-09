using Grpc.Core;
using UserService.Apps.Customer.Models.Query;
using UserService.Apps.Customer.Repository;
using UserService.Models.Entities;

namespace UserService.Apps.Customer.Grpc.GrpcCustomerServer
{
    public class CustomerServerImpl: GrpcCustomerService.GrpcCustomerServiceBase
    {
        private readonly ICustomerRepository _customerRepository;

        public CustomerServerImpl(ICustomerRepository customerRepository)
        {
            _customerRepository = customerRepository;
        }

        public override Task<GetCustomerResponseProto> GetCustomerById(
            GetCustomerRequestProto request, 
            ServerCallContext context
        ) {
            try
            {
                if (String.IsNullOrEmpty(request.CustomerId)) 
                {
                    var error = new Status(StatusCode.InvalidArgument, "empty customer id");
                    throw new RpcException(error);
                }

                Console.WriteLine("customer id:\t"+request.CustomerId);

                CustomerQuery? customer = _customerRepository.GetCustomerById(request.CustomerId);
                if (customer == null)
                {
                    var error = new Status(StatusCode.NotFound, "no customer found");
                    throw new RpcException(error);
                }

                GetCustomerResponseProto response = new GetCustomerResponseProto()
                {
                    Firstname = customer.Firstname,
                    Lastname = customer.Lastname ?? "",
                    PhoneOne = customer.PhoneOne ?? "",
                    PhoneTwo = customer.PhoneTwo ?? "",
                };

                return Task.FromResult(response);
            }
            catch (RpcException)
            {
                throw;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                throw new RpcException(new Status(StatusCode.Internal, "Internal server error"));
            }
        }
    }
}
