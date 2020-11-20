using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Models
{
    public class TicketStatic
    {
        public string TicketId { get; set; }
        public string SeanceId { get; set; }
        public string Cinema { get; set; }
        public string Hall { get; set; }
        public string Film { get; set; }
        public string Date { get; set; }
        public string StartTime { get; set; }
        public string EndTime { get; set; }
        public string Places { get; set; }
    }
}
