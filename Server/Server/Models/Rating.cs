using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class Rating
    {
        public Guid Id { get; set; }
        public Guid FilmId { get; set; }
        public Guid UserId { get; set; }
        public double Rating1 { get; set; }
        public string Review { get; set; }

        public virtual FilmInfo Film { get; set; }
        public virtual UserData User { get; set; }
    }
}
