using System;
using System.ServiceModel;

namespace Models
{
    [ServiceContract]
	public interface ISampleService
	{
		[OperationContract]
		string Test(string s);

		[OperationContract]
		string XmlMethod(MyCustomModel myCustomModel);

		[OperationContract]
		MyCustomModel TestCustomModel(MyCustomModel inputModel);
	}
}
