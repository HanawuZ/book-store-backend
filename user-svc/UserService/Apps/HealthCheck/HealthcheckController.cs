using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using UserService.Libs.Security;

namespace UserService.Apps.HealthCheck
{
    [Route("health")]
    [ApiController]
    public class HealthcheckController : ControllerBase
    {
        private readonly IJwtUtility _jwtUtility;

        public HealthcheckController(IJwtUtility jwtUtility)
        {
            _jwtUtility = jwtUtility;
        }

        [HttpGet]
        [Authorize]
        public string AuthenticatedGet() 
        {
            string? customerId = _jwtUtility.GetCustomerId(HttpContext);
            Console.WriteLine(customerId);
            return "Authenticated check!";
        }

    }
}
