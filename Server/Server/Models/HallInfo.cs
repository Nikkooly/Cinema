using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class HallInfo
    {
        public HallInfo()
        {
            Seance = new HashSet<Seance>();
        }

        public Guid Id { get; set; }
        public Guid CinemaId { get; set; }
        public string Name { get; set; }
        public int Places { get; set; }

        public virtual CinemaInfo Cinema { get; set; }
        public virtual ICollection<Seance> Seance { get; set; }
    }
}
