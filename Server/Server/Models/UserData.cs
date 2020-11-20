using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class UserData
    {
        public UserData()
        {
            Rating = new HashSet<Rating>();
            Tickets = new HashSet<Tickets>();
        }

        public Guid Id { get; set; }
        public string Login { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
        public int RoleId { get; set; }

        public virtual Roles Role { get; set; }
        public virtual ICollection<Rating> Rating { get; set; }
        public virtual ICollection<Tickets> Tickets { get; set; }
    }
}
