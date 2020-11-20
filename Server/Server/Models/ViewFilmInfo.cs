using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class ViewFilmInfo
    {
        public Guid? Id { get; set; }
        public string Name { get; set; }
        public string Poster { get; set; }
        public int? Year { get; set; }
        public string Country { get; set; }
        public string Description { get; set; }
        public int? Duration { get; set; }
        public string Genre { get; set; }
        public double? Rating { get; set; }
    }
}
