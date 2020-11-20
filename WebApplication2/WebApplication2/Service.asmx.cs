using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Web.UI;
using Microsoft.VisualBasic;
using System.Collections;
using System.Data;
using System.Diagnostics;
using System.Net;
using System.Security.Cryptography;
using System.IO;
using Newtonsoft.Json.Linq;
using System.Web.Script.Serialization;
using Newtonsoft.Json;

namespace WebApplication2
{
    /// <summary>
    /// Сводное описание для Service
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // Чтобы разрешить вызывать веб-службу из скрипта с помощью ASP.NET AJAX, раскомментируйте следующую строку. 
    // [System.Web.Script.Services.ScriptService]
    public class Service : System.Web.Services.WebService
    {
        [WebMethod]
        public string SoapService(string x,string y)
        {
            string result = "";
            string apiKey = "Z6NTGOCFXWWOBMWYLNB6PZLCHZ08R3KX";
            string format = "json";
            WebRequest request = WebRequest.Create("https://api.geodatasource.com/city?key=" + apiKey + "&format=" + System.Web.HttpUtility.UrlEncode(format) + "&lat=" + System.Web.HttpUtility.UrlEncode(x) + "&lng=" + System.Web.HttpUtility.UrlEncode(y));
            WebResponse response = request.GetResponse();
            using (Stream stream = response.GetResponseStream())
            {
                using (StreamReader reader = new StreamReader(stream))
                {
                    string line = "";
                    while ((line = reader.ReadLine()) != null)
                    {
                        result = line;
                    }
                }
            }
            response.Close();
            dynamic jsonObj = JsonConvert.DeserializeObject(result);
            string city = jsonObj.city;
            string region=jsonObj.region;
            return "Ваш регион: "+region +"\n"+ "Ваш город:  "+city+"\n"+"Текущее время:"+ DateTime.Now.ToString("dd MMM yyy HH:mm");
        }
    }
}
