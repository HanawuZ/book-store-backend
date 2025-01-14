using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace UserService.Models.Entities
{
    [Table("users")]
    public class User
    {
        [Key]
        [Column("id")]
        public string Id { get; set; }
        public string Username { get; set; }

        public string Password { get; set; }

        [Column("profile_picture")]
        public string? ProfilePicture { get; set; }


        [Column("email")]
        public string Email { get; set; }

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

        public string CreatedBy { get; set; }

        public DateTime UpdatedDate { get; set; }

        public string UpdatedBy { get; set; }
    }
}
