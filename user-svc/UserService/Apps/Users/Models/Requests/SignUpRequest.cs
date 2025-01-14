using System.Text.Json.Serialization;

namespace UserService.Apps.Users.Models.Requests
{
    /* 
     {
        "username": "firstuser",
        "firstname": "First",
        "lastname": "lastName",
        "password": "pass@word1",
        "email": "example@gmail.com",
        "phoneOne": "011459844874"
    }
     */
    public class SignUpRequest
    {
        [JsonPropertyName("username")]
        public required string Username { get; set; }
    
        [JsonPropertyName("firstname")]
        public required string Firstname { get; set; }

        [JsonPropertyName("lastname")]
        public string? Lastname { get; set; }

        [JsonPropertyName("email")]
        public required string Email { get; set; }

        [JsonPropertyName("password")]
        public required string Password { get; set; }

        [JsonPropertyName("phoneOne")]
        public string? PhoneOne { get; set; }
    }
}
