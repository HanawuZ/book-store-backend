using Microsoft.EntityFrameworkCore;
using UserService.Models.Entities;


namespace UserService.Configs.Databases
{
    public class PostgresDatabaseContext : DbContext
    {
        public PostgresDatabaseContext(DbContextOptions<PostgresDatabaseContext> options) : base(options)
        {
            Console.WriteLine("Setting Npgsql.EnableLegacyTimestampBehavior -> true");
            AppContext.SetSwitch("Npgsql.EnableLegacyTimestampBehavior", true);
        }

        public DbSet<User> Users { get; set; }
        public DbSet<Customer> Customers { get; set; }

        public DbSet<UserMapping> UserMappings { get; set; }

    }
}
