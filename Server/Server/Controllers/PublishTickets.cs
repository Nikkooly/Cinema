using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Mail;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Server.Models;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Server.Controllers
{
    [Route("api/[controller]")]
    public class PublishTickets : Controller
    {
        CinemaContext cinemaContext = new CinemaContext();
        [HttpGet("{id}")]
        public string Get(string id)
        {
            return cinemaContext.Tickets.Where(u=>u.Id.ToString().Equals(id)).Select(s=>s.Place).First().ToString();
        }
        // POST api/<controller>
        [HttpPost]
        public string Post([FromBody]BoughtTicket value)
        {
            List<string> idTickets = new List<string>();
            List<int> placesId=new List<int>();
            for(int i = 0; i < value.Place.Count(); i++)
            {
                placesId.Add(Convert.ToInt32(value.Place.ElementAt(i)));
            }
            string emailUser = value.EmailUser;
            bool status = false;
            StringBuilder stringBuilder = new StringBuilder();
            Tickets tickets=new Tickets();
            using (var transaction = cinemaContext.Database.BeginTransaction())
            {
                try
                {
                    for(int i = 0; i < placesId.Count(); i++)
                    {
                        if (!cinemaContext.Tickets.Any(u=> u.Place.Equals(placesId.ElementAt(i)) && u.SeanceId.Equals(value.SeanceId))) //TicketId.Equals(placesId.ElementAt(i))))
                        {
                            Guid id = Guid.NewGuid();
                            tickets.Id =id;
                            tickets.SeanceId = value.SeanceId;
                            tickets.UserId = value.IdUser;
                            tickets.Place = placesId.ElementAt(i);                           
                            cinemaContext.Add(tickets);
                            cinemaContext.SaveChanges();
                            idTickets.Add(id.ToString());
                        }
                        else
                        {
                            status = false;
                            transaction.Rollback();
                            return "No";
                        }
                        stringBuilder.Append(value.Place.ElementAt(i).ToString()+",");
                    }
                   
                    transaction.Commit();
                    status = true;
                    
                    SendEmailAsync(value.EmailUser, value.CinemaInfo, value.HallName,value.Film, value.Date, value.StartTime, value.EndTime,stringBuilder.ToString()).GetAwaiter();
                  
                }
                catch (Exception ex)
                {
                    transaction.Rollback();
                }
                if (status)
                {
                    return "OK";
                }
                else
                {
                   return "No";
                }
            }
        }


        private static async Task SendEmailAsync(string toUser, string cinema,string hall,string film, string date,string startTime,string EndTime, string places)
        {

            MailAddress from = new MailAddress("MagnifisentCinema@yandex.ru", "Magnifisent cinema");
            MailAddress to = new MailAddress(toUser);
            MailMessage m = new MailMessage(from, to);
            m.Subject = "Покупка билета";
            m.Body = $"Спасибо за покупку билетов в нашем кинотеатре. Информация по билету:<br> Кинотеатр: {cinema}  <br> Зал: {hall} <br> Фильм: {film} <br> Дата: {date} <br> Начало сеанса: {startTime} <br> Конец сеанса: {EndTime}  <br> Места: {places} <br> QR код находится во вложении письма";
            m.IsBodyHtml = true;
            m.Attachments.Add(new Attachment("F:/University/3-2/kursovoi/images/qr-code.png"));
            SmtpClient smtp = new SmtpClient("smtp.yandex.by", 587);
            smtp.Credentials = new NetworkCredential("MagnifisentCinema@yandex.ru", "123456789Nick");
            smtp.EnableSsl = true;
            await smtp.SendMailAsync(m);
        }
    }
}
