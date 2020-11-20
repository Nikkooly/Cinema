using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class FilmInfo
    {
        public FilmInfo()
        {
            Rating = new HashSet<Rating>();
            Seance = new HashSet<Seance>();
        }

        public Guid Id { get; set; }
        public string Poster { get; set; }
        public int Year { get; set; }
        public string Country { get; set; }
        public int Duration { get; set; }
        public string Genre { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }

        public virtual ICollection<Rating> Rating { get; set; }
        public virtual ICollection<Seance> Seance { get; set; }
    }
}
