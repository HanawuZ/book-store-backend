using System.Text.Json.Serialization;

namespace UserService.Apps.CustomerAddresses.Models.Requests
{
    public class CreateCustomerAddress
    {
        [JsonPropertyName("address")]
        public required string Address { get; set; }

        [JsonPropertyName("latitude")]
        public double Latitude { get; set; }

        [JsonPropertyName("longitude")]
        public double Longitude { get; set; }

        [JsonPropertyName("street")]
        public string? Street {  get; set; }

        [JsonPropertyName("subDistrict")]
        public string? SubDistrict { get; set; }

        [JsonPropertyName("district")]
        public string? District { get; set; }

        [JsonPropertyName("province")]
        public string? Province { get; set; }

        [JsonPropertyName("country")]
        public string? Country { get; set; }

        [JsonPropertyName("zipcode")]
        public string? Zipcode { get; set; }
    }
}
