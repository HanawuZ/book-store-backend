using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using UserService.Apps.Users.Models.Requests;
using UserService.Apps.Users.Services;
using UserService.Libs.Http;

namespace UserService.Apps.Users.Controllers
{
    [Route("api/users")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly IUserService _userService;

        public UserController(IUserService userService) {
            _userService = userService;
        }

        [HttpPost]
        [Route("sign-up")]
        public IActionResult SignUp([FromBody] SignUpRequest request)
        {
            try
            {
                HttpServe<string> response = _userService.SignUp(request);
                if (response.Status != StatusCodes.Status201Created)
                {
                    return BadRequest(response);
                }

                return Created(String.Empty, response);
            }
            catch (Exception ex)
            {
                HttpServe<string> errResponse = new HttpServe<string>(StatusCodes.Status500InternalServerError, ex.Message, null);
                return StatusCode(StatusCodes.Status500InternalServerError, errResponse);
            }

        }

        [HttpPost]
        [Route("sign-in")]
        public IActionResult SignIn([FromBody] SignInRequest request)
        {
            Console.WriteLine($"Data => {request.Email}, {request.Username}, {request.Password}, {request.RememberMe}");
            HttpServe<string?> response = new HttpServe<string?>(201, "Sign in", null);
            return Created(string.Empty, response);

        }
    }
}
