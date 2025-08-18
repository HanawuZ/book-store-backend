using Microsoft.IdentityModel.Tokens;
using UserService.Apps.CustomerAddresses.Models.Requests;
using UserService.Apps.CustomerAddresses.Repository;
using UserService.Apps.Users.Models.Requests;
using UserService.Apps.Users.Models.Responses;
using UserService.Libs.Http;
using UserService.Models.Entities;

namespace UserService.Apps.CustomerAddresses.Services
{
    public interface ICustomerAddressService
    {
        public HttpServe<List<CustomerAddress>> GetCustomerAddress(string? customerId);

        public HttpServe<string?> CreateCustomerAddress(string? customerId, CreateCustomerAddress customerAddress);
        public HttpServe<string?> UpdateCustomerAddress(CreateCustomerAddress updatedCustomerAddress);
    }

    public class ConcretedCustomerAddressService: ICustomerAddressService
    {
         private readonly ICustomerAddressRepository _customerAddressRepository;

        public ConcretedCustomerAddressService(ICustomerAddressRepository customerAddressRepository) 
        {
             _customerAddressRepository = customerAddressRepository;
        }

        public HttpServe<List<CustomerAddress>> GetCustomerAddress(string? customerId)
        {
            try
            {
                if (String.IsNullOrEmpty(customerId)) 
                { 
                    return new HttpServe<List<CustomerAddress>>(StatusCodes.Status400BadRequest, "customer id is empty", []);
                }

                 List<CustomerAddress> customerAddresses = _customerAddressRepository.GetCustomerAddress(customerId);
                
                return new HttpServe<List<CustomerAddress>>(StatusCodes.Status200OK, "success", customerAddresses);
            }
            catch
            {
                throw;
            }
        }

        public HttpServe<string?> CreateCustomerAddress(string? customerId, CreateCustomerAddress customerAddress)
        {
            try
            {
                if (String.IsNullOrEmpty(customerId))
                {
                    return new HttpServe<string?>(StatusCodes.Status400BadRequest, "customer id is empty.", null);
                }

                string? errorMessage = ValidateCustomerAddress(customerAddress);
                if (!String.IsNullOrEmpty(errorMessage))
                {
                    return new HttpServe<string?>(StatusCodes.Status400BadRequest, errorMessage, null);

                }

                DateTime currentTime = DateTime.UtcNow;
                CustomerAddress newAddress = new CustomerAddress
                {
                    Id = Guid.NewGuid().ToString(),
                    CustomerId = customerId,
                    Address = customerAddress.Address,
                    Latitude = customerAddress.Latitude,
                    Longitude = customerAddress.Longitude,
                    Street = customerAddress.Street,
                    SubDistrict = customerAddress.SubDistrict,
                    District = customerAddress.District,
                    Province = customerAddress.Province,
                    Country = customerAddress.Country,
                    Zipcode = customerAddress.Zipcode,
                    IsActive = true,
                    CreatedDate = currentTime,
                    CreatedBy = customerId,
                    UpdatedDate = currentTime,
                    UpdatedBy = customerId,
                };

                //Console.WriteLine(newAddress.Address);
                //Console.WriteLine(newAddress.Longitude);
                //Console.WriteLine(newAddress.Latitude);
                //Console.WriteLine(newAddress.Street);
                //Console.WriteLine(newAddress.SubDistrict);
                //Console.WriteLine(newAddress.District);
                //Console.WriteLine(newAddress.Province);
                //Console.WriteLine(newAddress.Country);
                //Console.WriteLine(newAddress.Zipcode);
                //Console.WriteLine(newAddress.IsActive);

                bool completed = _customerAddressRepository.CreateCustomerAddress(newAddress);
                if (completed) 
                { 
                    return new HttpServe<string?>(StatusCodes.Status201Created, "เพิ่มข้อมูลที่อยู่สำเร็จ", null);
                }

                return new HttpServe<string?>(StatusCodes.Status400BadRequest, "ไม่สามารถเพิ่มข้อมูลที่อยู่ได้", null);

            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public HttpServe<string?> UpdateCustomerAddress(CreateCustomerAddress updatedCustomerAddress)
        {
            try
            {
                string? errorMessage = ValidateCustomerAddress(updatedCustomerAddress);
                if (!string.IsNullOrEmpty(errorMessage))
                {
                    return new HttpServe<string?>(StatusCodes.Status400BadRequest, errorMessage, null);

                }

                if (updatedCustomerAddress.Id.IsNullOrEmpty())
                {
                    return new HttpServe<string?>(StatusCodes.Status400BadRequest, "customer address id is empty.", null);
                }

                CustomerAddress? address = _customerAddressRepository.GetCustomerAddressById(updatedCustomerAddress.Id);
                if (address == null) 
                {
                    return new HttpServe<string?>(StatusCodes.Status400BadRequest, "customer address not found.", null);
                }

                if (address.Address != updatedCustomerAddress.Address)
                {
                    address.Address = updatedCustomerAddress.Address;
                }

                if (address.Latitude != updatedCustomerAddress.Latitude)
                {
                    address.Latitude = updatedCustomerAddress.Latitude;
                }

                if (address.Longitude != updatedCustomerAddress.Longitude) 
                { 
                    address.Longitude = updatedCustomerAddress.Longitude;
                }

                if (address.Street != updatedCustomerAddress.Street) 
                { 
                    address.Street = updatedCustomerAddress.Street;
                }

                if (address.SubDistrict != updatedCustomerAddress.SubDistrict) 
                { 
                    address.SubDistrict = updatedCustomerAddress.SubDistrict;
                }

                if (address.District != updatedCustomerAddress.District) 
                { 
                    address.District = updatedCustomerAddress.District;
                }

                if (address.Province != updatedCustomerAddress.Province) 
                {
                    address.Province = updatedCustomerAddress.Province; 
                }

                if (address.Zipcode != updatedCustomerAddress.Zipcode) 
                { 
                    address.Zipcode = updatedCustomerAddress.Zipcode;
                }


                address.UpdatedDate = DateTime.UtcNow;
                address.UpdatedBy = "admin update";

                bool completed = _customerAddressRepository.UpdateCustomerAddress(address);
                if (completed)
                {
                    return new HttpServe<string?>(StatusCodes.Status200OK, "แก้ไขข้อมูลที่อยู่สำเร็จ", null);
                }

                return new HttpServe<string?>(StatusCodes.Status400BadRequest, "ไม่สามารถแก้ไขข้อมูลที่อยู่ได้", null);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        private string? ValidateCustomerAddress(CreateCustomerAddress customerAddress)
        {
            if (customerAddress == null)
            {
                return "request body is empty.";
            }

            if (String.IsNullOrEmpty(customerAddress.Address)) 
            {
                return "customer address is empty.";
            }

            if (customerAddress.Latitude > 90 || customerAddress.Latitude < -90)
            {
                return "latitude is out of range between -90 and 90";
            }

            if (customerAddress.Longitude > 180 || customerAddress.Longitude < -180)
            {
                return "longitude is out of range between -180 and 180";
            }

            return null;
        }
    }
}
