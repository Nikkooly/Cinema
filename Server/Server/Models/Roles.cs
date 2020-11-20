using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class Roles
    {
        public Roles()
        {
            UserData = new HashSet<UserData>();
        }

        public int Id { get; set; }
        public int Role { get; set; }

        public virtual ICollection<UserData> UserData { get; set; }
    }
}
