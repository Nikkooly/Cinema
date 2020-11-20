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
    public class GuestController : Controller
    {
        CinemaContext cinemaContext = new CinemaContext();

        // GET api/<controller>/5
        [HttpGet("{id}")]
        public string Get(int id)
        {
            if (id == 3 && cinemaContext.UserData.Count(s=>s.RoleId.Equals(3))==1)
            {
                return JsonConvert.SerializeObject(cinemaContext.UserData.Where(u=>u.RoleId.Equals(id)).Select(u=>new { u.Id, u.Login, u.RoleId, u.Email }));
            }
            else
            {
                return JsonConvert.SerializeObject("Error");
            }
        }

        }
}
