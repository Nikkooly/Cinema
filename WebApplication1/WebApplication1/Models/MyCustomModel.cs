using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace Models
{
	[Serializable]
	[DataContract]
	public class MyCustomModel
    {
        [DataMember]
		public int Id { get; set; }

		[DataMember]
		public string Name { get; set; }

        [DataMember]
		public string Email { get; set; }

    }
}
