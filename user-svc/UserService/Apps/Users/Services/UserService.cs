using UserService.Apps.Users.Models.Requests;
using UserService.Apps.Users.Repository;
using UserService.Libs.Bcrypt;
using UserService.Libs.Http;
using UserService.Models.Entities;

namespace UserService.Apps.Users.Services
{
    public interface IUserService
    {
        public HttpServe<string> SignUp(SignUpRequest request);
    }
    public class ConcretedUserService: IUserService
    {

        private readonly IUserRepository _userRepository;

        public ConcretedUserService(IUserRepository userRepository)
        {
            _userRepository = userRepository;
        }

        public HttpServe<string> SignUp(SignUpRequest request)
        {
            try
            {
                string encodePassword = BcryptEncoder.EncodePassword(request.Password);
                DateTime currentTime = DateTime.UtcNow;

                User newUser = new User
                {
                    Id = Guid.NewGuid().ToString(),
                    Username = request.Username,
                    Password = encodePassword,
                    ProfilePicture = null,
                    Email = request.Email,
                    AccountNonExpired = false,
                    AccountNonLocked = false,
                    CredentialsNonExpired = false,
                    IsUsing2FA = false,
                    Secret = null,
                    Provider= null,
                    ProviderId = null,
                    IsActive = true,
                    CreatedDate = currentTime,
                    CreatedBy = "admin",
                    UpdatedDate = currentTime,
                    UpdatedBy = "admin"
                };

                Customer newCustomer = new Customer
                {
                    Id = Guid.NewGuid().ToString(),
                    FirstName = request.Firstname,
                    LastName = request.Lastname,
                    PhoneOne = request.PhoneOne,
                    PhoneTwo = null,
                    CreatedDate = currentTime,
                    CreatedBy = "admin",
                    UpdatedDate = currentTime,
                    UpdatedBy = "admin"
                };

                UserMapping userMapping = new UserMapping
                {
                    Id = Guid.NewGuid().ToString(),
                    UserId = newUser.Id,
                    CustomerId = newCustomer.Id,
                };

                Console.WriteLine("UserService");

                bool complete = _userRepository.CreateUser(newUser, newCustomer, userMapping);
                
                if (complete)
                {
                    return new HttpServe<string>(StatusCodes.Status201Created, "สมัครสมาชิกสำเร็จ!", null);
                }

                return new HttpServe<string>(StatusCodes.Status400BadRequest, "อุ๊บ! สมัครสมาชิกไม่สำเร็จ...", null);


            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);

            }

        }
    }
}
