using Microsoft.EntityFrameworkCore;
using UserService.Configs.Databases;
using UserService.Libs.Bcrypt;

namespace UserService.Models.Entities.Seeds
{
    public class UserSeeder
    {
        public static void Seed(PostgresDatabaseContext context)
        {
            context.Database.Migrate();
            if (context.Users.Any())
            {
                return;
            }

            context.AddRange(GetUserList());
            context.SaveChanges();
        }

        static List<User> GetUserList()
        {
            var userList = new List<User>()
            {
                new User(
                    id: Guid.NewGuid().ToString(),
                    username: "usertest",
                    password: BcryptEncoder.EncodePassword("password"),
                    isActive: true,
                    createdDate: DateTime.Now,
                    createdBy: "admin",
                    updatedDate: DateTime.Now,
                    updatedBy: "admin"
                    )
            };

            return userList;
        }

    }
}
