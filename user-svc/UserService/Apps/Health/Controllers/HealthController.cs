using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using UserService.Apps.Users.Models.Requests;
using UserService.Apps.Users.Models.Responses;
using UserService.Apps.Users.Services;
using UserService.Libs.Http;
namespace UserService.Apps.Health.Controllers
{   
    [ApiController]
    public class HealthController : ControllerBase
    {

        [HttpGet]
        [Route("health/authorize")]
        [Authorize]
        public IActionResult AuthorizeHealthChecks()
        {
            return Ok("authorize check!");
        }
    }
}