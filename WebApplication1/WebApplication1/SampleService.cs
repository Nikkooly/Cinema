using javax.jws;
using jdk.nashorn.@internal.codegen;
using Models;
using System;
using System.Xml.Linq;
public class Service1 : System.Web.Services.WebService
{

    [WebMethod]
    public string ConectarBaseDatos()
    {
        try
        {
            using (MySqlCommand cm = new MySqlCommand())
            {

                string connection = @"Server=localhost; Port=3306; Database=sistemadatosatletasfecovol; 
                                Uid=****; Pwd=*****";
                MySqlConnection cn = new MySqlConnection(connection);

                cm.CommandType = System.Data.CommandType.StoredProcedure;
                cm.CommandText = "ingresarUsuario";
                cm.Parameters.Add("_email", MySqlDbType.VarChar).Value = "asdafa@gmail.com";
                cm.Parameters.Add("_password", MySqlDbType.VarChar).Value = "*****";

                cn.Open();
                cm.Connection = cn;
                cm.ExecuteNonQuery();

                MySqlDataReader cr = cm.ExecuteReader();
                cr.Read();
                string id;
                id = cr["mensaje"].ToString();
                cn.Close();
                return id;
            }
        }
        catch (Exception ex)
        {
            return ex.ToString();
        }
    }

}
/*public class SampleService : ISampleService
{
	public string Test(string s)
	{
		//DateTime dateTime = new DateTime();
		String date = DateTime.Now.ToString("dd MMM yyyy");
		Console.WriteLine("Test Method Executed!");
		return s+$" made by SOAP. Current date is {date} ";
	}
	[WebMethod]
	public string XmlMethod(MyCustomModel myCustomModel)
	{
		return myCustomModel.Name;
	}

	public MyCustomModel TestCustomModel(MyCustomModel customModel)
	{
		return customModel;
	}
}*/
