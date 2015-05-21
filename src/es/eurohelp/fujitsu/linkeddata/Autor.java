package es.eurohelp.fujitsu.linkeddata;

import java.net.URI;

public class Autor extends LinkedDataResource {
	
	private String name;
	private String description;
	private String depiction;
	private String death;
	private String born;
	private int id;
	
	public Autor(int id, URI uri, String name, String description, String depiction,
			String death, String born) {
		super(uri);
		this.id = id;
		this.name = name;
		this.description = description;
		this.depiction = depiction;
		this.death = death;
		this.born = born;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDepiction() {
		return depiction;
	}

	public void setDepiction(String depiction) {
		this.depiction = depiction;
	}

	public String getDeath() {
		return death;
	}

	public void setDeath(String death) {
		this.death = death;
	}

	public String getBorn() {
		return born;
	}

	public void setBorn(String born) {
		this.born = born;
	}
}
