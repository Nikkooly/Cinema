using Microsoft.VisualBasic.CompilerServices;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Models
{
    public class TicketAllData
    {
        public int Place { get; set; }
        public DateTime StartTime { get; set; }
        public DateTime EndTime { get; set; }
        public string HallName { get; set; }
        public string CinemaName { get; set; }
        public string CinemaAddress { get; set; }
    }
}
