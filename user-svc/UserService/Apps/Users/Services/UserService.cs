using System.IdentityModel.Tokens.Jwt;
using UserService.Apps.Users.Models.Queries;
using UserService.Apps.Users.Models.Requests;
using UserService.Apps.Users.Models.Responses;
using UserService.Apps.Users.Repository;
using UserService.Libs.Bcrypt;
using UserService.Libs.Http;
using UserService.Libs.Security;
using UserService.Models.Entities;

namespace UserService.Apps.Users.Services
{
    public interface IUserService
    {
        public HttpServe<string?> SignUp(SignUpRequest request);
        public HttpServe<SignInResponse?> SignIn(SignInRequest request);
    }
    public class ConcretedUserService : IUserService
    {

        private readonly IUserRepository _userRepository;

        private readonly IJwtUtility _jwtUtility;

        public ConcretedUserService(IUserRepository userRepository, IJwtUtility jwtUtility)
        {
            _userRepository = userRepository;
            _jwtUtility = jwtUtility;

        }

        public HttpServe<string?> SignUp(SignUpRequest request)
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
                    Provider = null,
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

                bool complete = _userRepository.CreateUser(newUser, newCustomer, userMapping);

                if (complete)
                {
                    return new HttpServe<string?>(StatusCodes.Status201Created, "สมัครสมาชิกสำเร็จ!", null);
                }

                return new HttpServe<string?>(StatusCodes.Status400BadRequest, "อุ๊บ! สมัครสมาชิกไม่สำเร็จ...", null);


            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);

            }

        }

        public HttpServe<SignInResponse?> SignIn(SignInRequest request)
        {
            try
            {
                if (String.IsNullOrEmpty(request.UsernameOrEmail))
                {
                    return new HttpServe<SignInResponse?>(StatusCodes.Status400BadRequest, "คุณไม่ได้กรอกชื่อผู้ใช้หรืออีเมล", null);

                }

                if (String.IsNullOrEmpty(request.Password))
                {
                    return new HttpServe<SignInResponse?>(StatusCodes.Status400BadRequest, "กรุณากรอกรหัสผ่าน", null);
                }


                GetUserCustomerQuery? existedUser = _userRepository.GetUserByUsernameOrEmail(request.UsernameOrEmail);
                if (existedUser == null)
                {
                    return new HttpServe<SignInResponse?>(StatusCodes.Status400BadRequest, "ไม่พบข้อมูลผู้ใช้", null);
                }

                bool passwordCorrect = BcryptEncoder.ComparePassword(request.Password, existedUser.Password);
                if (!passwordCorrect)
                {
                    return new HttpServe<SignInResponse?>(StatusCodes.Status400BadRequest, "รหัสผ่านไม่ถูกต้อง", null);
                }

                string token = _jwtUtility.GenerateUserToken(existedUser);
                SignInResponse response = new SignInResponse { AccessToken = token };

                return new HttpServe<SignInResponse?>(StatusCodes.Status201Created, "เข้าสู่ระบบสำเร็จ!", response);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }

        }
    }
}
