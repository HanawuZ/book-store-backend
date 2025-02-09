
using Microsoft.EntityFrameworkCore;
using UserService.Apps.Customer.Models.Query;
using UserService.Configs.Databases;

namespace UserService.Apps.Customer.Repository
{

    public interface ICustomerRepository
    {
        CustomerQuery? GetCustomerById(string customerId);
    }

    public class ConcretedCustomerRepository: ICustomerRepository
    {
        private readonly PostgresDatabaseContext _dbContext;

        public ConcretedCustomerRepository(PostgresDatabaseContext dbContext) 
        { 
            _dbContext = dbContext;
        }

        public CustomerQuery? GetCustomerById(string customerId) 
        { 
            try
            {
                Console.WriteLine("CustomeRepository | customerId:\t" + customerId);

                FormattableString queryString = $"""
                    SELECT c.firstname, c.lastname, c.phone_one, c.phone_two 
                    FROM customers c
                    WHERE c.id = {customerId}
                """;

                var result = _dbContext.Database.
                    SqlQuery<CustomerQuery>(queryString).
                    First();
                return result;
            }
            catch
            {
                throw;
            }

        }
    }
}
