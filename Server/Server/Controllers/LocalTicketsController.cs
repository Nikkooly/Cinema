using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Server.Models;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Server.Controllers
{
    [Route("api/[controller]")]
    public class LocalTicketsController : Controller
    {
        CinemaContext cinemaContext = new CinemaContext();
        // GET api/<controller>/5
        [HttpGet("{id}")]
        public string Get(string id)
        {
            return JsonConvert.SerializeObject((from t in cinemaContext.Tickets
                                                join s in cinemaContext.Seance on t.SeanceId equals s.Id
                                                join f in cinemaContext.FilmInfo on s.FilmId equals f.Id
                                                join h in cinemaContext.HallInfo on s.HallId equals h.Id
                                                join c in cinemaContext.CinemaInfo on h.CinemaId equals c.Id
                                                where t.UserId.ToString() == id & s.StartTime>DateTime.Now
                                                select new UserTicket()
                                                {
                                                    SeanceId = s.Id.ToString(),
                                                    Date = s.StartTime.ToString("dd MMM"),
                                                    Time = s.StartTime.ToString("HH:mm"),
                                                    Name = f.Name,
                                                    FilmId = f.Id.ToString(),
                                                    EndTime = s.EndTime.ToString("yyyy-MM-dd HH:mm"),
                                                    Cinema = c.Name + " " + c.Adress,
                                                    Hall = h.Name
                                                }).Distinct());
        }

    }
}
