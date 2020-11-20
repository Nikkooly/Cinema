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
    public class ReviewController : Controller
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
                return JsonConvert.SerializeObject(cinemaContext.Rating.Where(u => u.FilmId.ToString().Equals(id)).Select(s => new LoadReviews { Review=s.Review }));
        }

        // POST api/<controller>
        [HttpPost]
        public string Post([FromBody]Rating value)
        {
            if(!cinemaContext.Rating.Any(u=>u.FilmId.Equals(value.FilmId) & u.UserId.Equals(value.UserId))){
                Rating rating = new Rating();
                rating.Id = Guid.NewGuid();
                rating.UserId = value.UserId;
                rating.FilmId = value.FilmId;
                rating.Rating1 = value.Rating1;
                rating.Review = value.Review;
                try
                {
                    cinemaContext.Add(rating);
                    cinemaContext.SaveChanges();
                    return JsonConvert.SerializeObject("Отзыв успешно оставлен");
                }
                catch (Exception ex)
                {
                    return JsonConvert.SerializeObject(ex.Message);
                }
            }
            else
            {
                return "No";
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
