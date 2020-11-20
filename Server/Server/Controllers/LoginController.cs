using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Web.Providers.Entities;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Server.Models;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Server.Controllers
{
    [Route("api/[controller]")]
    public class LoginController : Controller
    {
        CinemaContext cinemaContext = new CinemaContext();

        // POST api/<controller>
        [HttpPost]
        public string Post([FromBody]UserData value)
        {
            var password = value.Password.ToString();
            var login = value.Login;
            if (cinemaContext.UserData.Any(user => user.Login.Equals(value.Login)))
            {
                try
                {
                    UserData userData = cinemaContext.UserData.Where(u => u.Login.Equals(value.Login)).First();
                   
                    if (login.Equals(userData.Login) && password.Equals(userData.Password.ToString()))
                    {
                        return JsonConvert.SerializeObject(cinemaContext.UserData.Where(u=> u.Login.Equals(value.Login) & u.Password.Equals(password)).Select(s=> new { s.RoleId, s.Id,s.Email,s.Login }));
                    }
                    else
                    {
                        return JsonConvert.SerializeObject(cinemaContext.UserData.Select(s => new UserData() { RoleId=4}));
                    }
                }
                catch(Exception ex)
                {
                    return JsonConvert.SerializeObject(ex.Message);
                }
            }
            else
            {
                return "[]";
            }
            password = "";
        }

       
    }
}
