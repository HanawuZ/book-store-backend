namespace UserService.Apps.Users.Models.Queries
{
    public class GetUserCustomerQuery
    {
        public string Username { get; set; }
        public string Password { get; set; }
        public string Email { get; set; }

        public string CustomerId { get; set; }
    }
}
