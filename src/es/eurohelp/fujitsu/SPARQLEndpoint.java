package es.eurohelp.fujitsu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.sparql.core.ResultBinding;


public class SPARQLEndpoint {
	private String endpoint_url;
	public SPARQLEndpoint (String url){
		this.endpoint_url = url;
	}
	public void query (String GeoNamesId){
		
		ArrayList<String> autor_uris = new ArrayList<String>();
		
		String sparqlQuery =
				"PREFIX owl:<http://www.w3.org/2002/07/owl#>"
				+ "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX dbpedia_prop:<http://dbpedia.org/property/>"
				+ "PREFIX dbpedia_ont:<http://dbpedia.org/ontology/>"
				+ "SELECT DISTINCT ?person ?nombre "
				+ "WHERE {"
				+ "?person rdfs:label ?nombre . " 
				+ "?town owl:sameAs <http://sws.geonames.org/" + GeoNamesId +"/> ."
				+ "?person dbpedia_prop:birthPlace ?town "
				+ "{?person dbpedia_prop:birthPlace ?town} "
				+ "UNION "
				+ "{?person dbpedia_ont:birthPlace ?town}"
				+ "FILTER( lang(?nombre) = \"es\" )"
				+ "}"
				;
		Query query = QueryFactory.create(sparqlQuery);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		ResultSet results = qexec.execSelect();
//		System.out.println(results.getRowNumber());
//		List vars = results.getResultVars();
//		System.out.println(vars);
		while(results.hasNext()){
			ResultBinding binding = (ResultBinding) results.next();
			String nombre = ((binding.get("?nombre")).asLiteral().getString());
			String person = ((binding.get("?person")).asResource().getURI());
//			String obra = ((binding.get("?obra")).asResource().getURI());
//			String titulo  = (binding.get("?titulo")).asLiteral().getString();
//			System.out.println(nombre + " - " + person + " - " + obra + " - " + titulo);
//			System.out.println(nombre + " - " + person);
			autor_uris.add(person);
		}
//		ResultSetFormatter.out(System.out, results, query);       
		qexec.close() ;
		
		Iterator<String> autores = autor_uris.iterator();
		while (autores.hasNext()){
			String autor_uri = autores.next();
			String sparqlQuery1 =
					"PREFIX owl:<http://www.w3.org/2002/07/owl#>"
					+ "PREFIX dbpedia:<http://dbpedia.org/resource/>"
					+ "PREFIX bne_prop:<http://datos.bne.es/def/>"
					+ "SELECT DISTINCT ?obra ?titulo "
					+ "WHERE {"
					+ "?autor owl:sameAs " + "<" + autor_uri + ">" + " ."
					+ "?autor bne_prop:OP5001 ?obra ."
					+ "?obra bne_prop:P1001 ?titulo  "
					+ "}"
					;
			Query query1 = QueryFactory.create(sparqlQuery1);
			QueryExecution qexec1 = QueryExecutionFactory.sparqlService("http://datos.bne.es/sparql", query1);
			ResultSet results1 = qexec1.execSelect();
//			System.out.println(results.getRowNumber());
//			List vars = results.getResultVars();
//			System.out.println(vars);
			while(results1.hasNext()){
				ResultBinding binding1 = (ResultBinding) results1.next();
//				String nombre = ((binding.get("?nombre")).asLiteral().getString());
//				String person = ((binding.get("?person")).asResource().getURI());
				String obra = ((binding1.get("?obra")).asResource().getURI());
				String titulo  = (binding1.get("?titulo")).asLiteral().getString();
				System.out.println( obra + " - " + titulo);
//				System.out.println( obra);
			}
//			ResultSetFormatter.out(System.out, results, query);       
			qexec1.close() ;
			
		}
	}
}

