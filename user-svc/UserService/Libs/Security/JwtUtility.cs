
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using Microsoft.EntityFrameworkCore.Metadata.Internal;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using UserService.Apps.Users.Models.Queries;
using UserService.Models.Entities;

namespace UserService.Libs.Security
{
    public interface IJwtUtility
    {
        public string GenerateUserToken(GetUserCustomerQuery user);
        public string? GetCustomerId(HttpContext context);
    }
    public class JwtUtility: IJwtUtility
    {
        private readonly string secretKey;
        private readonly string issuer;
        private readonly IConfiguration _configuration;

        public JwtUtility(IConfiguration configuration) 
        { 
            try
            {
                _configuration = configuration;
                if (_configuration["Jwt:Key"].IsNullOrEmpty())
                {
                    throw new Exception("secret key is empty");
                }
                secretKey = _configuration["Jwt:Key"]!;

                if (_configuration["Jwt:Issuer"].IsNullOrEmpty())
                {
                    throw new Exception("issuer is empty.");
                }

                issuer = _configuration["Jwt:Issuer"]!;
            } 
            catch
            {
                throw;
            }

        }

        public string GenerateUserToken(GetUserCustomerQuery user)
        {

            var tokenHandler = new JwtSecurityTokenHandler();
            byte[] securityKey = Encoding.ASCII.GetBytes(secretKey);
            var credentials = new SigningCredentials(
                new SymmetricSecurityKey(securityKey),
                SecurityAlgorithms.HmacSha256Signature
            );

            var claim = new ClaimsIdentity();
            claim.AddClaim(new Claim(ClaimTypes.Name, user.Username));
            claim.AddClaim(new Claim(ClaimTypes.Email, user.Email));
            claim.AddClaim(new Claim("customer_id", user.CustomerId));
            claim.AddClaim(new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()));

            DateTime expiredTime = DateTime.Now.AddMinutes(240);

            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = claim,
                Expires = expiredTime,
                SigningCredentials = credentials
            };

            var token = tokenHandler.CreateToken(tokenDescriptor);
            return tokenHandler.WriteToken(token);

        }

        public string? GetCustomerId(HttpContext context)
        {
            try
            {
                string authorization = context.Request.Headers["Authorization"].ToString();
                if (String.IsNullOrEmpty(authorization)) {
                    throw new Exception("no authorization header found.");
                }

                string token = authorization.Split(" ")[1];

                var handler = new JwtSecurityTokenHandler();
                var jwtSecurityToken = handler.ReadJwtToken(token);

                var claims = jwtSecurityToken.Claims.ToList();

                string? customerId = null;
                foreach (var claim in claims)
                {
                    if (claim.Type == "customer_id")
                    {
                        customerId = claim.Value;
                        break;
                    }
                }
                return customerId;
            } 
            catch 
            {
                throw;
            }
        }

    }
}
