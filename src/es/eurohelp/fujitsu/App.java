package es.eurohelp.fujitsu;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import es.eurohelp.fujitsu.linkeddata.Autor;

public class App {
	public static void main(String[] args) {
		
		Autor mi = new Autor();
		mi.setUri(URI.create("http://api.geonames.org/"));
		mi.setName("yo");
		System.out.println(mi.getName() + mi.getUri());
		
//		// GeoNames REST service config
//		String geonames_url = "http://api.geonames.org/";
//		String username = args[0];
//		
//		// Location
//		String latitude = args[2];
//		String longitude = args[3];
//		
//		// Get GeoNamesId
//		GeoNames geonames = new GeoNames(geonames_url, username);
//		String geonamesid = null;
//		try {
//			Object json_response = JSONValue.parse(geonames.findNearbyPlaceNameJSON(latitude, longitude));
//			JSONArray geonames_array = (JSONArray) ((Map)json_response).get("geonames");
//			Long GeoNamesId = (Long) ((Map)geonames_array.get(0)).get("geonameId");
//			geonamesid = String.valueOf(GeoNamesId);
//			
//			System.out.println(geonamesid);
//			
//			// Get fcode: if FCODE is section of populated place (PPLX), obtain parent
//			String fcode = (String) ((Map)geonames_array.get(0)).get("fcode");
//			if(fcode.equals("PPLX") || fcode.equals("PPL")){
//				geonamesid = geonames.obtainProperPopulatedPlace(geonamesid);
//				System.out.println(geonamesid);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
////		System.out.println(geonamesid);
//		
//		// Use Id to query endpoint
//		String eurohelp_endpoint_url = args[1];
//		SPARQLEndpoint eurohelp_endpoint = new SPARQLEndpoint(eurohelp_endpoint_url);
//		eurohelp_endpoint.query(geonamesid);
	}
}
