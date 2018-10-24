package com.infrasight.tests.apiprobe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class HttpConnection {
	public void connect(Class<County> class1) throws Exception {
		
		String url = "https://api.arbetsformedlingen.se/af/v0/arbetsformedling/soklista/lan"; 		//api request URL
		URL obj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");		// set request method, (GET/POST)
		con.setRequestProperty("Accept", "application/json");		// set required headers for request
		con.setRequestProperty("Accept-Language", "charset=utf-8; qs=1");

		// check if connection is OK anything but 200 is bad
	//	int responseCode = con.getResponseCode();
	//	System.out.println("Response code is: " + responseCode);	
		
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		// go into nested JSON response and grab the Skåne county from the API, posting this info at the end of the class
		JSONObject json = new JSONObject(response.toString());
		JSONObject json_Soklista = json.getJSONObject("soklista");
		JSONArray json_Sokdata = json_Soklista.getJSONArray("sokdata");
		JSONObject json_SkaneCounty = json_Sokdata.getJSONObject(10);

		// Another connection to grab Offices and put them in a list
		String office_url = "https://api.arbetsformedlingen.se/af/v0/arbetsformedling/platser?lanid=12";
		URL office_obj = new URL(office_url);
		
		HttpURLConnection office_con = (HttpURLConnection) office_obj.openConnection();
		office_con.setRequestMethod("GET");		// set request method, (GET/POST)
		office_con.setRequestProperty("Accept", "application/json");		// set required headers for request
		office_con.setRequestProperty("Accept-Language", "charset=utf-8");
		
		// check if connection is OK anything but 200 is bad
//		int office_responseCode = office_con.getResponseCode();
//		System.out.println("Office response code is: "+ office_responseCode);
		
		
		BufferedReader office_in = new BufferedReader(
				new InputStreamReader(office_con.getInputStream()));
		String office_inputLine;
		StringBuffer office_response = new StringBuffer();
		while ((office_inputLine = office_in.readLine()) != null) {
			office_response.append(office_inputLine);
		}
		office_in.close();
		
		// go into nested JSON response and grab all the offices in the Skåne county, posting this info at the end of the class
		JSONObject json_office = new JSONObject(office_response.toString());
		JSONObject json_Arbetsformedlingslista = json_office.getJSONObject("arbetsformedlingslista");
		JSONArray json_Arbetsformedlingplatsdata = json_Arbetsformedlingslista.getJSONArray("arbetsformedlingplatsdata");
		
		List<Office> officeList = new ArrayList<Office>();
			
		// loop to put all the offices in a list
		for (int i = 0; i < json_Arbetsformedlingplatsdata.length(); i++) {
				JSONObject jsonObject = json_Arbetsformedlingplatsdata.getJSONObject(i);
				Office office = new Office(jsonObject.getString("afplatsnamn"), jsonObject.getString("afplatskod"));
				officeList.add(office);
			}		
		
		// last required API end point to get an HashMap with all the cities and jobAdverts 
		String jobAdverts_url = "https://api.arbetsformedlingen.se/af/v0/platsannonser/soklista/kommuner?lanid=12";
						
		URL jobAdverts_url_obj = new URL(jobAdverts_url);
		
		HttpURLConnection jobAdverts_con = (HttpURLConnection) jobAdverts_url_obj.openConnection();
		jobAdverts_con.setRequestMethod("GET");		// set request method, (GET/POST)
		jobAdverts_con.setRequestProperty("Accept", "application/json");		// set required headers for request
		jobAdverts_con.setRequestProperty("Accept-Language", "charset=utf-8");
		
		// check if connection is OK anything but 200 is bad		
//		int jobAdverts_responseCode = jobAdverts_con.getResponseCode();
//		System.out.println("jobAdverts code is: "+ jobAdverts_responseCode);
		
		
		BufferedReader jobAdverts_in = new BufferedReader(
				new InputStreamReader(jobAdverts_con.getInputStream()));
		String jobAdverts_inputLine;
		StringBuffer jobAdverts_response = new StringBuffer();
		while ((jobAdverts_inputLine = jobAdverts_in.readLine()) != null) {
			jobAdverts_response.append(jobAdverts_inputLine);
		}
		jobAdverts_in.close();
		
			
		JSONObject jobAdverts_json = new JSONObject(jobAdverts_response.toString());
		JSONObject jobAdverts_Soklista = jobAdverts_json.getJSONObject("soklista");
		JSONArray jobAdverts_Sokdata = jobAdverts_Soklista.getJSONArray("sokdata");

		
		Map<String, Integer> jobAdvertsPerCity = new HashMap<String, Integer>();
		
		//loop to map keys & values to the HashMap (only name and number of jobAdverts)
		for (int i = 0; i < jobAdverts_Sokdata.length(); i++) {
			JSONObject jobAdverts_jsonObject = jobAdverts_Sokdata.getJSONObject(i);
			String name = jobAdverts_jsonObject.getString("namn");
			Integer jobAdverts = jobAdverts_jsonObject.getInt("antal_platsannonser");
			jobAdvertsPerCity.put(name, jobAdverts);
		}

		// post info
		County scaniaCounty = new County();
		scaniaCounty.id = (String) json_SkaneCounty.get("id");
		scaniaCounty.name = (String) json_SkaneCounty.get("namn");
		scaniaCounty.numJobAdverts = (int) json_SkaneCounty.get("antal_platsannonser");
		scaniaCounty.offices = officeList;
		scaniaCounty.jobAdvertsPerCity = (Map<String, Integer>) jobAdvertsPerCity;
		System.out.println(scaniaCounty);
	}
}
