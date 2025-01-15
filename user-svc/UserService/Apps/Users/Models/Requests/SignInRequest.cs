using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace UserService.Apps.Users.Models.Requests
{
    public class SignInRequest
    {
        [JsonPropertyName("usernameOrEmail")]
        public string UsernameOrEmail { get; set; }

        [JsonPropertyName("password")]
        public required string Password { get; set; }

        [JsonPropertyName("rememberMe")]
        public bool RememberMe { get; set; } = false;
    }
}
