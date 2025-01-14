using UserService.Configs.Databases;
using UserService.Models.Entities;

namespace UserService.Apps.Users.Repository
{

    public interface IUserRepository
    {
        public bool CreateUser(User newUser, Customer newCustomer, UserMapping userMapping);
        public void GetUserByUsernameOrEmail(string username, string email);
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

        public void GetUserByUsernameOrEmail(string username, string email)
        {

        }
    }
}
