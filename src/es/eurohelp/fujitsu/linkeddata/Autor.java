package es.eurohelp.fujitsu.linkeddata;

public class Autor extends LinkedDataResource {
	private String name;
	public Autor() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
