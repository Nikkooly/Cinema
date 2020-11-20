using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Models
{
    public class SeanceResult
    {
        public Guid Id { get; set; }
        public string HallName { get; set;}
        public string StartTime { get; set; }
        public Guid HallId { get; set; }
        public Guid FilmId { get; set; }
        public string EndTime { get; set; }
    }
}
