using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class SaledTickets
    {
        public Guid Id { get; set; }
        public Guid UserId { get; set; }
        public Guid TicketId { get; set; }

        public virtual Tickets Ticket { get; set; }
        public virtual UserData User { get; set; }
    }
}
