using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Linq.Expressions;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Data.SqlClient;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;
using Server.Models;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Server.Controllers
{
    [Route("api/[controller]")]
    public class SeanceController : Controller
    {
        CinemaContext cinemaContext = new CinemaContext();
        // GET: api/<controller>
        [HttpGet]
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET api/<controller>/5
        [HttpGet("{id}")]
        public string Get(string id)
         {
            DateTime now = DateTime.Now;
            string date =now.ToString("yyyy-MM-ddThh:mm:ss");
            DateTime startDate = DateTime.ParseExact(date, "yyyy-MM-ddTHH:mm:ss", null);
            using (var db = new CinemaContext())
            {
                return JsonConvert.SerializeObject(db.Seance.Where(x => x.FilmId == new Guid(id) & x.StartTime.CompareTo(startDate)==1)
                    .Join(
                    db.HallInfo,
                    s=>s.HallId,
                    h=>h.Id,
                    (s,h)=> h.CinemaId
                   ).Distinct().ToList()
                    .Join(
                    db.CinemaInfo,
                    h=>h,
                    c=>c.Id,
                    (h,c)=> new CinemaInfoForTicket() {
                        Id=c.Id.ToString(),
                        Name=c.Name,
                        Adress=c.Adress
                        }
                    ).ToList());
               
            }
        }
        [HttpGet("loadDataSeance/{id}")]
        public string GetSeanceData(string id)
        {
            return JsonConvert.SerializeObject(cinemaContext.Seance.Where(u => u.Id.ToString().Equals(id))
                .Join(cinemaContext.HallInfo,
                s => s.HallId,
                h => h.Id,
                (s, h) => new
                {
                    h.Places,
                    s.TicketPrice
                }).ToList());
        }
        [HttpGet("loadDataTicket/{id}")]
        public string GetTicketData(string id)
        {
            return JsonConvert.SerializeObject(cinemaContext.Tickets.Where(u => u.SeanceId.ToString().Equals(id)).Select(s=>new { s.SeanceId, s.Place, s.UserId }));
        }

        // POST api/<controller>
        [HttpPost]
        public async Task<string> Post([FromBody]SeanceData value)
        {
            Seance seance = new Seance();
            Guid guid = Guid.NewGuid();
            DateTime startDate = DateTime.ParseExact(value.StartTime.Replace(' ', 'T'), "yyyy-MM-ddTHH:mm:ss", null);
            DateTime endDate = DateTime.ParseExact(value.EndTime.Replace(' ', 'T'), "yyyy-MM-ddTHH:mm:ss", null);
            using var db = new CinemaContext();
            {
                if ((await db.Seance.Where(x => x.HallId == value.HallId).ToListAsync())?.All(x=> startDate.CompareTo(x.StartTime) == -1 && endDate.CompareTo(x.StartTime) == -1
                        || startDate.CompareTo(x.EndTime) == 1) == true) //&& !db.Seance.Any(u=>u.HallId.Equals(value.HallId)))
                {
                    seance.StartTime = DateTime.ParseExact(value.StartTime, "yyyy-MM-dd HH:mm:ss", null);
                    seance.EndTime = DateTime.ParseExact(value.EndTime, "yyyy-MM-dd HH:mm:ss", null);
                    seance.HallId = value.HallId;
                    seance.FilmId = value.FilmId;
                    seance.Id = guid;
                    seance.TicketPrice = value.TicketPrice;
                    try
                    {
                        cinemaContext.Add(seance);
                        await cinemaContext.SaveChangesAsync();
                        return guid.ToString();
                    }
                    catch (Exception ex)
                    {
                        return JsonConvert.SerializeObject(ex.Message);
                    }
                }
                else
                {
                    return "Сеанс уже существует";
                }
            }

        }

        private static Expression<Func<Seance, bool>> CheckExistSeanceAtThisTime(DateTime startDate, DateTime endDate)
        {
            return x => startDate.CompareTo(x.StartTime) == 1 && startDate.CompareTo(x.EndTime) == -1
                        && endDate.CompareTo(x.EndTime) == -1 && endDate.CompareTo(x.StartTime) == 1;
    }

        // PUT api/<controller>/5
        [HttpPut("{id}")]
        public void Put(string id, [FromBody]string value)
        {
        }

        // DELETE api/<controller>/5
        [HttpDelete("{id}")]
        public void Delete(string id)
        {
        }
    }   
}
