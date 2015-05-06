package es.eurohelp.fujitsu;

public class Engine {
	public static void main(String[] args) {
		
		// GeoNames REST service config
		String geonames_url = "http://api.geonames.org/";
		String username = "mikel_egana";
		
		// Bilbo location
		String latitude = "43.256943";
		String longitude = "-2.923611";
		
		GeoNames geonames = new GeoNames(geonames_url, username);
		String geonamesid = String.valueOf(geonames.findNearbyPlaceNameJSON(latitude, longitude));
		System.out.println(geonamesid);

		
	}
}
