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
    public class TicketsController : Controller
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
            // return JsonConvert.SerializeObject(from  cinemaContext.Ticke
           return JsonConvert.SerializeObject((from t in cinemaContext.Tickets
                          join s in cinemaContext.Seance on t.SeanceId equals s.Id
                          join f in cinemaContext.FilmInfo on s.FilmId equals f.Id
                          join h in cinemaContext.HallInfo on s.HallId equals h.Id
                          join c in cinemaContext.CinemaInfo on h.CinemaId equals c.Id
                          where t.UserId.ToString() == id
                          select new UserTicket()
                          {
                             SeanceId=  s.Id.ToString(),
                             Date=s.StartTime.ToString("dd MMM"),
                             Time=s.StartTime.ToString("HH:mm"),
                             Name =f.Name,
                             FilmId=f.Id.ToString(),
                             EndTime=s.EndTime.ToString("yyyy-MM-dd HH:mm"),
                             Cinema=c.Name+" "+c.Adress,
                             Hall=h.Name
                          }).Distinct());
        }
        [HttpGet("placesData/{user_id}&{seance_id}")]
        public string GetPlaces(string user_id,string seance_id)
        {
            // return JsonConvert.SerializeObject(from  cinemaContext.Ticke
            return JsonConvert.SerializeObject(cinemaContext.Tickets.Where(u=>u.SeanceId.ToString().Equals(seance_id) &u.UserId.ToString().Equals(user_id)).Select(p=>new { p.Place, p.Id }));
        }


        // POST api/<controller>
        /* [HttpPost]
         public string Post([FromBody]Tickets value)
         {
             Tickets tickets = new Tickets()
             {
                 Id = Guid.NewGuid(),
                 Place = value.Place,
                 Price = value.Price,
                 Status = value.Status,
                 SeanceId = value.SeanceId
             };
             try
             {
                 cinemaContext.Add(tickets);
                 cinemaContext.SaveChanges();
                 return JsonConvert.SerializeObject("Сеанс успешно добавлен");
             }
             catch (Exception ex)
             {
                 return JsonConvert.SerializeObject(ex.Message);
             }

         }*/


        // PUT api/<controller>/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/<controller>/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
