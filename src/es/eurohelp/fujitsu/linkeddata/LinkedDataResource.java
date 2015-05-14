package es.eurohelp.fujitsu.linkeddata;

import java.net.URI;

public class LinkedDataResource {
	private URI uri;
	public LinkedDataResource (URI uri){
		this.uri = uri;
	}
	public URI getUri() {
		return uri;
	}
}
