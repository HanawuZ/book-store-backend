using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace UserService.Apps.HealthCheck
{
    [Route("health")]
    [ApiController]
    public class HealthcheckController : ControllerBase
    {

        [HttpGet]
        [Authorize]
        public string AuthenticatedGet() {
            return "Authenticated check!";
        }

    }
}
