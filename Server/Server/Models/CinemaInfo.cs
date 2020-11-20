using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class CinemaInfo
    {
        public CinemaInfo()
        {
            HallInfo = new HashSet<HallInfo>();
        }

        public Guid Id { get; set; }
        public string Name { get; set; }
        public string Adress { get; set; }

        public virtual ICollection<HallInfo> HallInfo { get; set; }
    }
}
