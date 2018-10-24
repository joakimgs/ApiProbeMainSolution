package com.infrasight.tests.apiprobe;

public class ApiProbeMain {

	/**
	 * Arbetsformedligen REST API documentation: https://jobtechdev.se/swagger
	 * 
	 * The County and Office Java classes have several fields that need data.
	 * The task at hand is to use the API to fill in the fields for Skane lan (county).
	 * You will need to use several of the API endpoints to fill all fields.
	 * 
	 * You may retrieve the data and populate the classes in any way you like.
	 * 
	 * Use any open source libraries you like to complete the task. 
	 * For example, there are several libraries for JSON processing: 
	 * Jackson, Google Gson, Org.JSON.
	 * 
	 * It is recommended to use Maven for library dependencies.
	 * 
	 * As a final step, print out the County object with the fields filled in.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		HttpConnection hc = new HttpConnection();
		hc.connect(County.class);
		
//		// TODO: Use the Arbetsformedligen API and create a new County object for Skane lan
//		County scaniaCounty = null;
//		
//		// Print out information
//		if (scaniaCounty == null)
//			System.out.println("Scania county is null, please create the object and fill in some fields");
//		else
//			System.out.println(scaniaCounty);
	}
}
