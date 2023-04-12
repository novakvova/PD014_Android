using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Security.Cryptography.X509Certificates;

namespace Sim23.Data.Entitties
{
    [Table("tblCategories")]
    public class CategoryEntity : BaseEntity<int>
    {
        [Required, StringLength(255)]
        public string Name { get; set; }
        [StringLength(255)]
        public string Image { get; set; }
        public int Priotity { get; set; }
        [StringLength(4000)]
        public string Description { get; set; }
    }
}
