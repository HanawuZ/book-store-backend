using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace UserService.Models.Entities
{
    [Table("customers")]
    public class Customer
    {
        [Key]
        [Column("id")]
        public required string Id { get; set; }

        [Column("firstname")]
        public required string FirstName { get; set; }

        [Column("lastname")]
        public string? LastName { get; set; }

        [Column("phone_one")]
        public string? PhoneOne { get; set; }

        [Column("phone_two")]
        public string? PhoneTwo { get; set; }

        [Column("created_date")]
        public required DateTime CreatedDate { get; set; }

        [Column("created_by")]
        public required string CreatedBy { get; set; }

        [Column("updated_date")]
        public required DateTime UpdatedDate { get; set; }

        [Column("updated_by")]
        public required string UpdatedBy { get; set; }
    }
}
