using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Models
{
    public class FilmAllData
    {
        public Guid Id { get; set; }
        public string Poster { get; set; }
        public int Year { get; set; }
        public string Country { get; set; }
        public int Duration { get; set; }
        public string Genre { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public double Rating { get; set; }
    }
}
