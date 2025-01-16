using System.Text.Json.Serialization;

namespace UserService.Apps.Users.Models.Responses
{
    public class SignInResponse
    {

        [JsonPropertyName("accessToken")]
        public required string AccessToken { get; set; }
    }
}
