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
    public class CheckController : Controller
    {
        CinemaContext cinemaContext = new CinemaContext();

       /* [HttpGet("{id}")]
        public string Get(string id)
        {
            return JsonConvert.SerializeObject(cinemaContext.Tickets.Where(u => u.Id.ToString().Equals(id)).Select(x => new { x.Place }).OrderBy(s => s.Place));
        }*/

        // POST api/<controller>
        [HttpPost]
        public string Post([FromBody]Seance value)
        {
            string date = DateTime.Now.ToString("yyyy-MM-ddTHH:mm:ss");
            DateTime startDate = DateTime.ParseExact(date, "yyyy-MM-ddTHH:mm:ss", null);

            if (cinemaContext.Seance.Any(seance => seance.FilmId.Equals(value.FilmId) & seance.EndTime.CompareTo(startDate)==1))
            {
                return "OK";
            }
            else
            {
                return JsonConvert.SerializeObject("Сеансы на данный фильм отсутствует");
            }
        }
    }
}
