package es.eurohelp.fujitsu.linkeddata;

import java.net.URI;
import java.util.ArrayList;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sparql.core.ResultBinding;

public class autor_lugar {

	public static void main(String[] args) {
		String sparqlQuery = "PREFIX owl:<http://www.w3.org/2002/07/owl#>"
				+ "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX dbpedia_prop:<http://dbpedia.org/property/>"
				+ "PREFIX dbpedia_ont:<http://dbpedia.org/ontology/>"
				+ "PREFIX bne_prop:<http://datos.bne.es/def/>"

				+ "SELECT DISTINCT ?person " + "WHERE {"
				+ "?autor owl:sameAs ?person ."
				+ "?autor bne_prop:OP5001 ?obra ."
				+ "?obra bne_prop:P1001 ?titulo " + "}" + "";
		Query query = QueryFactory.create(sparqlQuery);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://datos.bne.es/sparql", query);
		ResultSet results = qexec.execSelect();

		while (results.hasNext()) {
			ResultBinding binding = (ResultBinding) results.next();
			String person = (binding.get("?person")).asResource().getURI();
			if (person.startsWith("http://dbpedia.org/resource/")) {
				System.out.println("[PERSON] " + person);
				String sparqlQuery2 = "PREFIX owl:<http://www.w3.org/2002/07/owl#>"
						+ "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
						+ "PREFIX dbpedia_prop:<http://dbpedia.org/property/>"
						+ "PREFIX dbpedia_ont:<http://dbpedia.org/ontology/>"
						+ "SELECT DISTINCT ?geo "
						+ "WHERE {"
						+ "<"
						+ person
						+ ">"
						+ " rdfs:label ?nombre . "
						+ "?town owl:sameAs ?geo ."
						+ "{ "
						+ "<"
						+ person
						+ ">"
						+ " dbpedia_prop:birthPlace ?town} "
						+ "UNION "
						+ "{ "
						+ "<"
						+ person
						+ ">"
						+ "  dbpedia_ont:birthPlace ?town}"
						+ "FILTER( lang(?nombre) = \"es\" )" + "}";
				Query query2 = QueryFactory.create(sparqlQuery2);
				QueryExecution qexec2 = QueryExecutionFactory.sparqlService(
						"http://datos.bne.es/sparql", query2);
				ResultSet results2 = qexec.execSelect();
				while (results2.hasNext()) {
					ResultBinding binding2 = (ResultBinding) results2.next();
					if (binding2.get("?geo") != null) {
						System.out.println("[GEO] "
								+ binding2.get("?geo").asResource().getURI());
					}
				}
				qexec2.close();
			}
		}
		qexec.close();
	}
}
