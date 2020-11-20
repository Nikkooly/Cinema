using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class Tickets
    {
        public Guid Id { get; set; }
        public Guid UserId { get; set; }
        public Guid SeanceId { get; set; }
        public int Place { get; set; }

        public virtual Seance Seance { get; set; }
        public virtual UserData User { get; set; }
    }
}
