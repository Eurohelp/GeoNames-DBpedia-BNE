package es.eurohelp.fujitsu.linkeddata;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;

public class autor_lugar {

	public static void main(String[] args) {
		String sparqlQuery =
				"PREFIX owl:<http://www.w3.org/2002/07/owl#>"
				+ "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX dbpedia_prop:<http://dbpedia.org/property/>"
				+ "PREFIX dbpedia_ont:<http://dbpedia.org/ontology/>"
				+ "PREFIX bne_prop:<http://datos.bne.es/def/>"

				+ "SELECT DISTINCT ?person "
				+ "WHERE {"
				+ "?autor owl:sameAs ?person ."
				+ "?autor bne_prop:OP5001 ?obra ."
				+ "?obra bne_prop:P1001 ?titulo "
				+ "FILTER( lang(?nombre) = \"es\" )}"
				+ ""
				;
		Query query = QueryFactory.create(sparqlQuery);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://datos.bne.es/sparql", query);
		ResultSet results = qexec.execSelect();
		System.out.println(results.getRowNumber());

	}

}
