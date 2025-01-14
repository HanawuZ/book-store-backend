using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace UserService.Apps.Users.Models.Requests
{
    public class SignInRequest
    {
        public string? Email { get; set; }

        public string? Username { get; set; }

        public required string Password { get; set; }

        [JsonPropertyName("rememberMe")]
        public bool RememberMe { get; set; } = false;
    }
}
