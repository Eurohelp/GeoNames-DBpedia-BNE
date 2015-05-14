package es.eurohelp.fujitsu;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import es.eurohelp.fujitsu.linkeddata.Autor;
import es.eurohelp.fujitsu.linkeddata.Obra;

public class App {
	public static void main(String[] args) {

		String GeoNamesURL = "http://api.geonames.org/";
		String GeoNamesUser = "mikel_egana";
		String DBPediaEndPoint = "http://dbpedia.org/sparql";
		String BNEendpoint = "http://datos.bne.es/sparql";
		
		// Astigarraga?
//		String latitude = "43.00612";  
//		String longitude = "-2.17663";
		
		// Salamanca-Madrid
		String latitude = "40.429824"; 
		String longitude = "-3.683133"; 
		
		GeoNames_DBPedia_BNE app = new GeoNames_DBPedia_BNE(GeoNamesURL, GeoNamesUser, DBPediaEndPoint, BNEendpoint);
		try {
			ArrayList<Autor> autores = app.getAutores(latitude, longitude);
			
			Iterator<Autor> autores_iterator = autores.iterator();
			while (autores_iterator.hasNext()){
				Autor autor = autores_iterator.next();
				System.out.println(autor.getUri() + autor.getName());
				ArrayList<Obra> obras = app.getObras(autor);
				Iterator<Obra> obras_iterator = obras.iterator();
				while(obras_iterator.hasNext()){
					Obra obra = obras_iterator.next();
					System.out.println(" --- " + obra.getTitulo());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
