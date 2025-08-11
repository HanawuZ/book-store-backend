using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using UserService.Apps.CustomerAddresses.Models.Requests;
using UserService.Apps.CustomerAddresses.Services;
using UserService.Libs.Http;
using UserService.Libs.Security;
using UserService.Models.Entities;

namespace UserService.Apps.CustomerAddresses.Controllers
{
    [Route("api/customers/addresses")]
    [ApiController]
    public class CustomerAddressController : ControllerBase
    {
        private readonly ICustomerAddressService _customerAddressService;

        private readonly IJwtUtility _jwtUtility;

        public CustomerAddressController(
            ICustomerAddressService customerAddressService,
            IJwtUtility jwtUtility
        ) {
            _customerAddressService = customerAddressService;
            _jwtUtility = jwtUtility;
        }

        [HttpPost]
        // [Authorize]
        public IActionResult CreateCustomerAddress([FromBody] CreateCustomerAddress request)
        {
            try
            {
                string? customerId = _jwtUtility.GetCustomerId(HttpContext);

                HttpServe<string?> response = _customerAddressService.CreateCustomerAddress(customerId, request);
                if (response.Status != StatusCodes.Status201Created)
                {
                    return BadRequest(response);
                }

                return Created(String.Empty, response);
            }
            catch (Exception ex)
            {
                HttpServe<string?> errResponse = new HttpServe<string?>(StatusCodes.Status500InternalServerError, ex.Message, null);
                return StatusCode(StatusCodes.Status500InternalServerError, errResponse);

            }
        }

        [HttpGet]
        // [Authorize]
        public IActionResult GetCustomerAddress()
        {
            try
            {
                string? customerId = _jwtUtility.GetCustomerId(HttpContext);

                HttpServe<List<CustomerAddress>> response = _customerAddressService.GetCustomerAddress(customerId);
                if (response.Status != StatusCodes.Status200OK)
                {
                    return BadRequest(response);
                }

                return Ok(response);
            }
            catch (Exception ex) 
            {
                HttpServe<string?> errResponse = new HttpServe<string?>(StatusCodes.Status500InternalServerError, ex.Message, null);
                return StatusCode(StatusCodes.Status500InternalServerError, errResponse);
            }
        }
    }
}
