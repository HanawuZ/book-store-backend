using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Reflection;

namespace UserService.Models.Entities
{
    [Table("customer_addresses")]
    public class CustomerAddress
    {
        [Key]
        [Column("id")]
        [Required]
        public required string Id { get; set; }

        [Column("customer_id")]
        [Required]
        public required string CustomerId { get; set; }

        public Customer Customer { get; set; }

        [Column("address")]
        [Required]
        public required string Address { get; set; }

        [Column("latitude")]
        [Required]
        public double Latitude { get; set; }

        [Column("longitude")]
        [Required]
        public double Longitude { get; set; }

        [Column("street")]
        public string? Street { get; set; }

        [Column("sub_district")]
        public string? SubDistrict { get; set; }

        [Column("district")]
        public string? District { get; set; }

        [Column("province")]
        public string? Province { get; set; }

        [Column("country")]
        public string? Country { get; set; }

        [Column("zipcode")]
        public string? Zipcode { get; set; }

        [Column("is_active")]
        [Required]
        public bool IsActive { get; set; } = true;

        [Column("created_date")]
        [Required]
        public DateTime CreatedDate { get; set; }

        [Column("created_by")]
        [Required]
        public required string CreatedBy { get; set; }

        [Column("updated_date")]
        [Required]
        public DateTime UpdatedDate { get; set; }

        [Column("updated_by")]
        [Required]
        public required string UpdatedBy { get; set; }

        public override string ToString()
        {
            var properties = GetType().GetProperties(BindingFlags.Public | BindingFlags.Instance);
            var values = properties.Select(p => $"{p.Name}: {p.GetValue(this) ?? "null"}");
            return $"User: {{ {string.Join(", ", values)} }}";
        }
    }

}
