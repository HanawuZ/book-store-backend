using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using Microsoft.Net.Http.Headers;
using System.Text;
using Microsoft.IdentityModel.Tokens;
using UserService.Libs.Http;
using System.Text.Json;

namespace UserService.Configs.Middlewares
{
    public class JwtMiddleware: IMiddleware
    {
        public async Task InvokeAsync(HttpContext context, RequestDelegate next)
        {
            var token = context.Request.Headers[HeaderNames.Authorization].ToString().Replace("Bearer ", "");
            Console.WriteLine("Invoke JWT Middleware");
            Console.WriteLine($"{token}");

            if (String.IsNullOrEmpty(token)) {
                context.Response.ContentType = "application/json";
                context.Response.StatusCode = StatusCodes.Status401Unauthorized;

                HttpServe<string?> errResponse = new HttpServe<string?>(StatusCodes.Status401Unauthorized, "No access token", null);

                string serializedResponse = JsonSerializer.Serialize(errResponse);
                await context.Response.WriteAsync(serializedResponse);
                await context.Response.CompleteAsync();
                return;
            }


            // Call the next middleware in the pipeline
            await next.Invoke(context);
        }

    }
}
