package es.eurohelp.fujitsu;

public class Engine {
	public static void main(String[] args) {
		
		// GeoNames REST service config
		String geonames_url = "http://api.geonames.org/";
		String username = "mikel_egana";
		
		// Bilbo location
		String latitude = "43.256943";
		String longitude = "-2.923611";
		
		// Get GeoNamesId for Bilbo
		GeoNames geonames = new GeoNames(geonames_url, username);
		String geonamesid = String.valueOf(geonames.findNearbyPlaceNameJSON(latitude, longitude));
		System.out.println(geonamesid);
		
		// Use Id for querying endpoint
		String eurohelp_endpoint_url = "http://172.16.250.249:8890/sparql";
		SPARQLEndpoint eurohelp_endpoint = new SPARQLEndpoint(eurohelp_endpoint_url);
		eurohelp_endpoint.query(geonamesid);
		
	}
}
