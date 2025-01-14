using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace UserService.Models.Entities
{
    [Table("user_mappings")]
    public class UserMapping
    {
        [Key]
        public string Id { get; set; }

        [Required]
        public string UserId { get; set; }

        public string? CustomerId { get; set; }
    }
}
