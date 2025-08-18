using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using UserService.Apps.CustomerAddresses.Models.Requests;
using UserService.Configs.Databases;
using UserService.Models.Entities;

namespace UserService.Apps.CustomerAddresses.Repository
{
    public interface ICustomerAddressRepository
    {
        public List<CustomerAddress> GetCustomerAddress(string customerId);

        public CustomerAddress? GetCustomerAddressById(string customerAddressId);

        public bool CreateCustomerAddress(CustomerAddress customerAddress);

        public bool UpdateCustomerAddress(CustomerAddress customerAddress);

    }

    public class ConcretedCustomerAddressRepository: ICustomerAddressRepository
    {

        private readonly PostgresDatabaseContext _dbContext;

        public ConcretedCustomerAddressRepository(PostgresDatabaseContext dbContext)
        {
            _dbContext = dbContext;
        }

        public List<CustomerAddress> GetCustomerAddress(string customerId) {
            try
            {
                FormattableString queryString = $"""
                    SELECT * FROM 
                    customer_addresses 
                    WHERE customer_id = {customerId}
                    ORDER BY created_date DESC
                """;
                var results = _dbContext.CustomerAddresses.FromSql(queryString).ToList();

                return results;
            }
            catch 
            {
                throw;
            }
        }

        public CustomerAddress? GetCustomerAddressById(string customerAddressId)
        {
            try
            {
                FormattableString queryString = $"""
                    SELECT * FROM 
                    customer_addresses 
                    WHERE id = {customerAddressId}
                """;
                return _dbContext.CustomerAddresses.FromSql(queryString).FirstOrDefault();
            }
            catch
            {
                throw;
            }
        }

        public bool CreateCustomerAddress(CustomerAddress customerAddress)
        {
            try
            {
                var transaction = _dbContext.Database.BeginTransaction();

                _dbContext.CustomerAddresses.Add(customerAddress);
                _dbContext.SaveChanges();

                transaction.Commit();
                return true;
            }
            catch
            {
                throw;
            }
        }

        public bool UpdateCustomerAddress(CustomerAddress customerAddress)
        {
            try
            {
                var transaction = _dbContext.Database.BeginTransaction();

                _dbContext.CustomerAddresses.Update(customerAddress);
                _dbContext.SaveChanges();

                transaction.Commit();
                return true;

            }
            catch
            {
                throw;
            }
        }


    }
}
