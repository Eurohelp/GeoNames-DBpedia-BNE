package es.eurohelp.fujitsu;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sparql.core.ResultBinding;

import es.eurohelp.fujitsu.linkeddata.Autor;
import es.eurohelp.fujitsu.linkeddata.Obra;

public class GeoNames_DBPedia_BNE {
	private String geonamesurl;
	private String geonamesuser;
	private String dbpediaendpoint;
	private String bneendpoint;

	public GeoNames_DBPedia_BNE(String GeoNamesURL, String GeoNamesUser,
			String DBPediaEndPoint, String BNEendpoint) {
		this.geonamesurl = GeoNamesURL;
		this.geonamesuser = GeoNamesUser;
		this.dbpediaendpoint = DBPediaEndPoint;
		this.bneendpoint = BNEendpoint;
	}

	public ArrayList<Autor> getAutores(String latitude, String longitude)
			throws ClientProtocolException, IOException {
		ArrayList<Autor> autores = new ArrayList<Autor>();

		// Get GeoNamesId
		GeoNames geonames = new GeoNames(geonamesurl, geonamesuser);
		String geonamesid = null;

		Object json_response = JSONValue.parse(geonames
				.findNearbyPlaceNameJSON(latitude, longitude));
		JSONArray geonames_array = (JSONArray) ((Map) json_response)
				.get("geonames");
		Long GeoNamesId = (Long) ((Map) geonames_array.get(0)).get("geonameId");
		geonamesid = String.valueOf(GeoNamesId);

//		System.out.println(geonamesid);

		// Get fcode: if FCODE is section of populated place (PPLX), obtain
		// parent
		String fcode = (String) ((Map) geonames_array.get(0)).get("fcode");
		if (fcode.equals("PPLX") || fcode.equals("PPL")) {
			geonamesid = geonames.obtainProperPopulatedPlace(geonamesid);
//			System.out.println(geonamesid);
		}

//		System.out.println(geonamesid);

		String sparqlQuery =
				"PREFIX owl:<http://www.w3.org/2002/07/owl#>"
				+ "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX dbpedia_prop:<http://dbpedia.org/property/>"
				+ "PREFIX dbpedia_ont:<http://dbpedia.org/ontology/>"
				+ "SELECT DISTINCT ?person ?nombre "
				+ "WHERE {"
				+ "?person rdfs:label ?nombre . " 
				+ "?town owl:sameAs <http://sws.geonames.org/" + geonamesid +"/> ."
//				+ "?town owl:sameAs <http://sws.geonames.org/3117735/>"
				+ "{?person dbpedia_prop:birthPlace ?town} "
				+ "UNION "
				+ "{?person dbpedia_ont:birthPlace ?town}"
				+ "FILTER( lang(?nombre) = \"es\" )"
				+ "}"
				;		

		Query query = QueryFactory.create(sparqlQuery);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(dbpediaendpoint, query);
		ResultSet results = qexec.execSelect();
		while(results.hasNext()){
			ResultBinding binding = (ResultBinding) results.next();
			String nombre = ((binding.get("?nombre")).asLiteral().getString());
			URI person = URI.create((binding.get("?person")).asResource().getURI());
			Autor autor = new Autor(person,nombre);
			autores.add(autor);
		}

		qexec.close();
		return autores;
	}

	public ArrayList<Obra> getObras(Autor autor) {
		ArrayList<Obra> obras = new ArrayList<Obra>();
		
		String sparqlQuery =
		"PREFIX owl:<http://www.w3.org/2002/07/owl#>"
		+ "PREFIX dbpedia:<http://dbpedia.org/resource/>"
		+ "PREFIX bne_prop:<http://datos.bne.es/def/>"
		+ "SELECT DISTINCT ?obra ?titulo "
		+ "WHERE {"
		+ "?autor owl:sameAs " + "<" + autor.getUri() + ">" + " ."
		+ "?autor bne_prop:OP5001 ?obra ."
		+ "?obra bne_prop:P1001 ?titulo  "
		+ "}"
		;
		
		

		Query query = QueryFactory.create(sparqlQuery);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(bneendpoint, query);
		ResultSet results = qexec.execSelect();
		while(results.hasNext()){
			ResultBinding binding = (ResultBinding) results.next();
			String titulo = ((binding.get("?titulo")).asLiteral().getString());
			URI uri_obra = URI.create((binding.get("?obra")).asResource().getURI());
			Obra obra = new Obra(uri_obra,titulo);
			obras.add(obra);
		}
		qexec.close();
		return obras;
	}
}
