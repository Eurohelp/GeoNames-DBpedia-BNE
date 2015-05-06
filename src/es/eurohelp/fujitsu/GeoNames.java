package es.eurohelp.fujitsu;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public class GeoNames {
	private String GeoNamesURL; 
	private String UserName;
	public GeoNames(String geonamesurl, String username) {
		this.GeoNamesURL = geonamesurl;
		this.UserName = username;
	}

	// http://hc.apache.org/httpcomponents-client-ga/quickstart.html
	public Long findNearbyPlaceNameJSON (String latitude, String longitude){
		Long GeoNamesId = null;		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				GeoNamesURL + 
				"findNearbyPlaceNameJSON?" +
				"lat=" + latitude + 
				"&lng=" + longitude + 
				"&username=" + UserName);
		CloseableHttpResponse response1 = null;
		try {
			response1 = httpclient.execute(httpGet);
//		    System.out.println(response1.getStatusLine());
		    HttpEntity entity1 = response1.getEntity();
		    String entity1_string = EntityUtils.toString(entity1);
		    System.out.println(entity1_string);
		    		    
			Object json_response = JSONValue.parse(entity1_string);
			JSONArray geonames_array = (JSONArray) ((Map)json_response).get("geonames");
			GeoNamesId = (Long) ((Map)geonames_array.get(0)).get("geonameId");

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				response1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return GeoNamesId;
	}
}
