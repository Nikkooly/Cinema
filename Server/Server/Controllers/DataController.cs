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
    public class DataController : Controller
    {
        // GET: api/<controller>
        [HttpGet]
        public IEnumerable<string> Get(SeanceInfo value)
        {
            return new string[] { "value1", "value2" };
        }
        // GET api/<controller>/5
        [HttpGet("{id}")]
        public string Get(string id)
        {
            return "value";
        }

        // POST api/<controller>
        [HttpPost]
        public string Post([FromBody]SeanceInfo value)
        {
            try
            {
                String date = value.Date + "T00:00:00";
                DateTime startDate = DateTime.ParseExact(date, "yyyy-MM-ddTHH:mm:ss", null);
                DateTime endDate = startDate.AddDays(1);
                using (var db = new CinemaContext())
                {
                    return JsonConvert.SerializeObject(
                        db.Seance.Where(s => s.FilmId.ToString().Equals(value.FilmId) & s.StartTime > startDate & s.EndTime < endDate)
                        .Join(
                            db.HallInfo.Where(x => x.CinemaId.ToString().Equals(value.CinemaId)),
                            s => s.HallId,
                            h => h.Id,
                            (s, h) => new SeanceResult()
                            {
                                Id = s.Id,
                                HallName=h.Name,
                                StartTime = s.StartTime.ToString("HH:mm"),
                                FilmId = s.FilmId,
                                HallId = s.HallId,
                                EndTime = s.EndTime.ToString("HH:mm")
                            })

                        );

                }
            }
            catch(Exception ex)
            {
                return ex.Message;
            }
        }

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
