using Grpc.Core;
using UserService.Apps.CustomerAddresses.Repository;
using UserService.Models.Entities;

namespace UserService.Apps.CustomerAddresses.Grpc.GrpcCustomerAddressServer
{
    public class CustomerAddressServerImpl: GrpcCustomerAddressService.GrpcCustomerAddressServiceBase
    {

        private readonly ICustomerAddressRepository _customerAddressRepository;

        public CustomerAddressServerImpl(ICustomerAddressRepository customerAddressRepository) 
        {
            _customerAddressRepository = customerAddressRepository;
        }

        public override Task<GetCustomerAddressResponseProto> GetCustomerAddress(
            GetCustomerAddressRequestProto request,
            ServerCallContext context
        ) {
            try
            {
                if (String.IsNullOrEmpty(request.CustomerAddressId))
                {
                    var error = new Status(StatusCode.InvalidArgument, "empty customer address id");
                    throw new RpcException(error);
                }

                Console.WriteLine("customer address id: "+ request.CustomerAddressId);

                CustomerAddress? customerAddress = _customerAddressRepository.GetCustomerAddressById(request.CustomerAddressId);
                if (customerAddress == null) 
                {
                    var error = new Status(StatusCode.NotFound, "no customer address found");
                    throw new RpcException(error);
                }

                GetCustomerAddressResponseProto responseProto = new GetCustomerAddressResponseProto()
                {
                    Address = customerAddress.Address,
                    Latitude = customerAddress.Latitude,
                    Longitude = customerAddress.Longitude,
                    Street = customerAddress.Street,
                    SubDistrict = customerAddress.SubDistrict,
                    District = customerAddress.District,
                    Province = customerAddress.Province,
                    Country = customerAddress.Country,
                    Zipcode = customerAddress.Zipcode,
                };

                return Task.FromResult(responseProto);
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
