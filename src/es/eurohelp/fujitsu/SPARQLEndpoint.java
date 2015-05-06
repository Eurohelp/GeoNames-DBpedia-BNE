package es.eurohelp.fujitsu;

import com.hp.hpl.jena.query.*;


public class SPARQLEndpoint {
	private String endpoint_url;
	public SPARQLEndpoint (String url){
		this.endpoint_url = url;
	}
	public void query (String GeoNamesId){
		String sparqlQuery =
				"PREFIX owl:<http://www.w3.org/2002/07/owl#> "
				+ "PREFIX foaf:<http://xmlns.com/foaf/0.1/> "
				+ "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX dbpedia:<http://dbpedia.org/resource/> "
				+ "PREFIX dbpedia_prop:<http://dbpedia.org/property/> "
				+ "PREFIX bne_prop:<http://datos.bne.es/def/> "
				+ "PREFIX dbpedia_ont:<http://dbpedia.org/ontology/> "
				+ "SELECT DISTINCT ?nombre ?titulo "
				+ "WHERE "
				+ "{SERVICE <http://dbpedia.org/sparql> "
				+ "{?person rdfs:label ?nombre . "
				+ "?town owl:sameAs <http://sws.geonames.org/" + GeoNamesId +"/> "
				+ "{?person dbpedia_prop:birthPlace ?town}UNION{?person dbpedia_ont:birthPlace ?town} "
				+ "}SERVICE <http://datos.bne.es/sparql> "
				+ "{?autor owl:sameAs ?person . "
				+ "?autor bne_prop:OP5001 ?obra . "
				+ "?obra bne_prop:P1001 ?titulo "
				+ "}"
				+ "FILTER( lang(?nombre) = \"es\" )}"
				;
		Query query = QueryFactory.create(sparqlQuery);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint_url, query);
		ResultSet results = qexec.execSelect();
		ResultSetFormatter.out(System.out, results, query);       
		qexec.close() ;
	}
}


//String sparqlQuery =
//"SELECT * \n" +
//"WHERE { \n" +
//"?s ?p ?o . \n" +
//"} \n";
//
//
//Query query = QueryFactory.create(sparqlQuery);
//QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint_url, query);
//
//ResultSet results = qexec.execSelect();
//ResultSetFormatter.out(System.out, results, query);       
//
//qexec.close() ;
