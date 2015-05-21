package es.eurohelp.fujitsu.linkeddata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LoadJSON {

	public static void main(String[] args) throws FileNotFoundException,
			IOException, ParseException {

		String json = null;
		BufferedReader br = new BufferedReader(
				new FileReader(
						"/Users/megana/Euro-help/Eclipse_Workspace/GeoNames-DBpedia-BNE/provincias.json"));

		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
			sb.append(line);
			sb.append("\n");
			line = br.readLine();
		}
		json = sb.toString();

		br.close();
		
//		System.out.println(json.substring(2710, 2725));

		JSONParser parser = new JSONParser();
		Object obj = parser
				.parse(new FileReader(
						"/Users/megana/Euro-help/Eclipse_Workspace/GeoNames-DBpedia-BNE/provincias.json"));
		JSONArray array = (JSONArray) obj;

	}

}
