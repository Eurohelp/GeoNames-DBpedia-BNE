package es.eurohelp.fujitsu.linkeddata;

import java.net.URI;

public class Autor extends LinkedDataResource {
	private String name;
	
	public Autor(URI uri, String name) {
		super(uri);
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
}
