using System.Text;
using Microsoft.IdentityModel.Tokens;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using UserService.Libs.Http;
using System.Text.Json;

namespace UserService.Configs.Middlewares { 
    
    public class JwtBearerMiddleware
    {

        public static void ConfigureJwtBearerOptions(JwtBearerOptions options, IConfiguration configuration)
        {
            var secretKey = configuration["Jwt:Key"];

            options.TokenValidationParameters = new TokenValidationParameters
            {
                IssuerSigningKey = new SymmetricSecurityKey(Encoding.ASCII.GetBytes(secretKey)),
                ValidateIssuer = false,
                ValidateAudience = false
            };

            options.Events = new JwtBearerEvents
            {
                OnAuthenticationFailed = HandleAuthenticationFailed,
                OnChallenge = HandleChallenge
            };
        }

        private static Task HandleAuthenticationFailed(AuthenticationFailedContext context)
        {
            context.NoResult();
            context.Response.StatusCode = 500;
            context.Response.ContentType = "application/json";

            string errorMessage = $"Internal server error: {context.Exception.Message}";

            var options = new JsonSerializerOptions { PropertyNamingPolicy = JsonNamingPolicy.CamelCase };
            HttpServe<string?> errResponse = new HttpServe<string?>(StatusCodes.Status500InternalServerError, errorMessage, null);

            return context.Response.WriteAsync(JsonSerializer.Serialize(errResponse, options));
        }

        private static Task HandleChallenge(JwtBearerChallengeContext context)
        {
            
            context.HandleResponse();
            context.Response.StatusCode = 401;
            context.Response.ContentType = "application/json";

            string? authorizationHeader = context.Request.Headers.Authorization;            
            
            string errorMessage = "Unauthorized: ";
            if (authorizationHeader.IsNullOrEmpty())
            {
                errorMessage += "No token found.";
            }
            else
            {
                errorMessage += "";
            }
            HttpServe<string?> errResponse = new HttpServe<string?>(StatusCodes.Status401Unauthorized, errorMessage, null);

            var options = new JsonSerializerOptions { PropertyNamingPolicy = JsonNamingPolicy.CamelCase };
            return context.Response.WriteAsync(JsonSerializer.Serialize(errResponse, options));
        }

    }

}
