using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Models
{
    public class BoughtTicket
    {
        public Guid IdUser { get; set; }
        public string EmailUser { get; set; }
        public string Date { get; set; }
        public string Film { get; set; }
        public string StartTime { get; set; }
        public string EndTime { get; set; }
        public string CinemaInfo { get; set; }
        public string HallName { get; set; }
        public Guid SeanceId { get; set; }
        public List<string> Place { get; set; }
    }
}
