using Microsoft.EntityFrameworkCore;
using UserService.Apps.Users.Models.Queries;
using UserService.Configs.Databases;
using UserService.Models.Entities;

namespace UserService.Apps.Users.Repository
{

    public interface IUserRepository
    {
        public bool CreateUser(User newUser, Customer newCustomer, UserMapping userMapping);
        public GetUserCustomerQuery? GetUserByUsernameOrEmail(string usernameOrEmail);
    }

    public class ConcretedUserRepository: IUserRepository
    {

        private readonly PostgresDatabaseContext _dbContext;

        public ConcretedUserRepository(PostgresDatabaseContext dbContext) 
        { 
            _dbContext = dbContext;
        }

        public bool CreateUser(User newUser, Customer newCustomer, UserMapping userMapping) 
        {
            try {
                var transaction = _dbContext.Database.BeginTransaction();

                Console.WriteLine("User Repository: Create User");
                
                _dbContext.Users.Add(newUser);
                _dbContext.Customers.Add(newCustomer);
                _dbContext.UserMappings.Add(userMapping);

                
                _dbContext.SaveChanges();

                transaction.Commit();
                return true;
            } catch (Exception ex) {
                throw new Exception(ex.Message);
            }
        }

        public GetUserCustomerQuery? GetUserByUsernameOrEmail(string usernameOrEmail)
        {
            try
            {
                FormattableString queryString = $"""
                    SELECT u.username, u.email, u.password, c.id AS customer_id
                    FROM users u 
                    JOIN user_mappings um ON u.id = um.user_id
                    JOIN customers c ON um.customer_id = c.id
                    WHERE u.username = {usernameOrEmail} OR u.email = {usernameOrEmail}
                """;

                GetUserCustomerQuery? result = _dbContext.Database.
                    SqlQuery<GetUserCustomerQuery>(queryString).
                    First();
                return result;
            }
            catch (Exception ex) {
                throw new Exception(ex.Message);
            }
        }
    }
}
