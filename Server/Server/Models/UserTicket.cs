using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Models
{
    public class UserTicket
    {
        public string SeanceId { get; set; }
        public string Date { get; set; }
        public string Time { get; set; }
        public string Name { get; set; }
        public string Cinema { get; set; }
        public string Hall { get; set; }
        public string EndTime { get; set; }
        public string FilmId { get; set; }
    }
}
