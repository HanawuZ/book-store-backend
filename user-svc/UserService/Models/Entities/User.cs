using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Reflection;

namespace UserService.Models.Entities
{
    [Table("users")]
    public class User
    {
        [Key]
        [Column("id")]
        public required string Id { get; set; }

        [Required]
        public required string Username { get; set; }

        [Required]
        public required string Password { get; set; }

        [Column("profile_picture")]
        public string? ProfilePicture { get; set; }


        [Column("email")]
        [Required]
        public required string Email { get; set; }

        [Column("account_non_expired")]
        public bool AccountNonExpired { get; set; }

        [Column("account_non_locked")]
        public bool AccountNonLocked { get; set; }

        [Column("credentials_non_expired")]
        public bool CredentialsNonExpired { get; set; }

        [Column("is_using_2fa")]
        public bool IsUsing2FA { get; set; }

        [Column("secret")]
        public string? Secret { get; set; }

        [Column("provider_id")]
        public string? ProviderId { get; set; }

        [Column("provider")]
        public string? Provider { get; set; }

        [Column("is_active")]
        public bool IsActive { get; set; }

        public DateTime CreatedDate { get; set; }

        public required string CreatedBy { get; set; }

        public DateTime UpdatedDate { get; set; }

        public required string UpdatedBy { get; set; }

        
        public override string ToString()
        {
            var properties = GetType().GetProperties(BindingFlags.Public | BindingFlags.Instance);
            var values = properties.Select(p => $"{p.Name}: {p.GetValue(this) ?? "null"}");
            return $"User: {{ {string.Join(", ", values)} }}";
        }
    }
}
