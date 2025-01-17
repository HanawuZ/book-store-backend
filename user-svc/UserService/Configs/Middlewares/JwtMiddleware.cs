using System.Text;
using Microsoft.IdentityModel.Tokens;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using UserService.Libs.Http;
using System.Text.Json;

namespace UserService.Configs.Middlewares { 
    
    public class JwtBearerMiddleware
    {
        public static void ConfigureJwtBearerOptions(JwtBearerOptions options)
        {
            options.TokenValidationParameters = new TokenValidationParameters
            {
                IssuerSigningKey = new SymmetricSecurityKey(Encoding.ASCII.GetBytes("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437")),
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

            string errorMessage = $"Internal server error: {context.Exception.ToString()}";

            HttpServe<string?> errResponse = new HttpServe<string?>(StatusCodes.Status500InternalServerError, errorMessage, null);

            return context.Response.WriteAsync(JsonSerializer.Serialize(errResponse));
        }

        private static Task HandleChallenge(JwtBearerChallengeContext context)
        {
            context.HandleResponse();
            context.Response.StatusCode = 401;
            context.Response.ContentType = "application/json";
            string errorMessage = $"Unauthorized: {context.Error}";
            HttpServe<string?> errResponse = new HttpServe<string?>(StatusCodes.Status401Unauthorized, errorMessage, null);

            return context.Response.WriteAsync(JsonSerializer.Serialize(errResponse));
        }

    }

}
