package es.eurohelp.fujitsu.linkeddata;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;

import es.eurohelp.fujitsu.GeoNames_DBPedia_BNE;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class autor_lugar {

	public static void main(String[] args) throws Exception {

		String GeoNamesURL = "http://api.geonames.org/";
		String GeoNamesUser = args[0];
//		String GeoNamesUser = "mikel_egana"; // 12:20
//		String GeoNamesUser = "eurohelp.bilbao"; // 12:10
//		String GeoNamesUser = "mikel_ni"; // 12:15
		String DBPediaEndPoint = "http://dbpedia.org/sparql";
		String BNEendpoint = "http://datos.bne.es/sparql";

		// String latitude = null;
		// String longitude = null;

		JSONParser parser = new JSONParser();
		Object obj = parser
				.parse(new FileReader(
						args[1]));
		JSONArray array = (JSONArray) obj;

		Iterator iterator = array.iterator();
		while (iterator.hasNext()) {
			JSONObject town = (JSONObject) iterator.next();
			// System.out.println(town);
			String town_as_key = (String) town.keySet().toArray()[0];
			// System.out.println(town_as_key.trim());
			JSONObject lat_long = (JSONObject) town.get(town_as_key);
			// System.out.println(lat_long.get("lon"));
			// System.out.println(lat_long.get("lat"));
			String latitude = String.valueOf(lat_long.get("lat"));
			String longitude = String.valueOf(lat_long.get("lon"));

			GeoNames_DBPedia_BNE app = new GeoNames_DBPedia_BNE(GeoNamesURL,
					GeoNamesUser, DBPediaEndPoint, BNEendpoint);

			boolean autor_town = false;

			ArrayList<Autor> autores = app.getAutores(latitude, longitude);
			Iterator<Autor> autores_iterator = autores.iterator();
			while (autores_iterator.hasNext()) {
				Autor autor = autores_iterator.next();
				ArrayList<Obra> obras = app.getObras(autor);
				if (!obras.isEmpty()) {
					autor_town = true;
					// System.out.println("==>" + autor.getUri() + " - "
					// + autor.getName());
					Iterator<Obra> obras_iterator = obras.iterator();
					while (obras_iterator.hasNext()) {
						Obra obra = obras_iterator.next();
						// System.out.println("      " + obra.getUri() + " - "
						// + obra.getTitulo());
					}
				}
			}

			if (autor_town) {
				System.out.println(town);
			}

		}
	}
}
