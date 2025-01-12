using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;


namespace UserService.Models.Entities
{
    [Table("users")]
    public class User
    {
        public User(
            string id, 
            string username, 
            string password, 
            bool isActive, 
            DateTime createdDate,
            string createdBy,
            DateTime updatedDate,
            string updatedBy
            ) { 
            this.Id = id;
            this.Username = username;
            this.Password = password;
            this.IsActive = isActive;
            this.CreatedDate = createdDate;
            this.CreatedBy = createdBy;
            this.UpdatedDate = updatedDate;
            this.UpdatedBy = updatedBy;
        }


        public string Id { get; set; }
        public string Username { get; set; }

        public string Password { get; set; }

        public bool IsActive { get; set; }

        public DateTime CreatedDate { get; set; }

        public string CreatedBy { get; set; }

        public DateTime UpdatedDate { get; set; }

        public string UpdatedBy { get; set; }
    }
}
