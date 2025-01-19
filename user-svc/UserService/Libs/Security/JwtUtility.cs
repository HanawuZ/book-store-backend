
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using Microsoft.EntityFrameworkCore.Metadata.Internal;
using Microsoft.IdentityModel.Tokens;
using UserService.Apps.Users.Models.Queries;
using UserService.Models.Entities;

namespace UserService.Libs.Security
{
    public interface IJwtUtility
    {
        public string GenerateUserToken(GetUserCustomerQuery user);
    }
    public class JwtUtility: IJwtUtility
    {
        private readonly string SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

        public JwtUtility() 
        { 
        }

        public string GenerateUserToken(GetUserCustomerQuery user)
        {
            var tokenHandler = new JwtSecurityTokenHandler();
            byte[] securityKey = Encoding.ASCII.GetBytes(SECRET);
            var credentials = new SigningCredentials(
                new SymmetricSecurityKey(securityKey),
                SecurityAlgorithms.HmacSha256Signature
            );

            var claim = new ClaimsIdentity();
            claim.AddClaim(new Claim(ClaimTypes.Name, user.Username));
            claim.AddClaim(new Claim(ClaimTypes.Email, user.Email));
            claim.AddClaim(new Claim("customer_id", user.CustomerId));
            claim.AddClaim(new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()));

            DateTime expiredTime = DateTime.Now.AddMinutes(120);

            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = claim,
                Expires = expiredTime,
                SigningCredentials = credentials
            };

            var token = tokenHandler.CreateToken(tokenDescriptor);
            return tokenHandler.WriteToken(token);

            //{
            //    Subject = new ClaimsIdentity(new[] {
            //    new Claim(ClaimTypes.Name, user.Username),
            //    new Claim(ClaimTypes.Email, user.Email),
            //    new Claim(JwtRegisteredClaimNames.Exp, expiredDate.ToString("yyyyMMddHHmmss")),
            //    new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString())
            //}),
            //    Expires = expiredDate,
            //    SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(securityKey), SecurityAlgorithms.HmacSha256Signature)
            //};

            //Console.WriteLine("Generating access token");

            //var tokenHandler = new JwtSecurityTokenHandler();
            //var token = tokenHandler.CreateToken(tokenDescriptor);

            //return tokenHandler.WriteToken(token);

        }
    }
}
