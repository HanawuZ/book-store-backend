using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Reflection;

namespace UserService.Models.Entities
{
    [Table("user_mappings")]
    public class UserMapping
    {
        [Key]
        public required string Id { get; set; }

        [Required]
        public required string UserId { get; set; }

        public string? CustomerId { get; set; }


        public override string ToString()
        {
            var properties = GetType().GetProperties(BindingFlags.Public | BindingFlags.Instance);
            var values = properties.Select(p => $"{p.Name}: {p.GetValue(this) ?? "null"}");
            return $"User: {{ {string.Join(", ", values)} }}";
        }
    }
}
