package es.eurohelp.fujitsu.linkeddata;

import java.net.URI;

public class Obra extends LinkedDataResource {
	private String titulo;
	
	public Obra(URI uri, String titulo) {
		super(uri);
		this.titulo=titulo;
	}
	
	public String getTitulo() {
		return titulo;
	}
}
