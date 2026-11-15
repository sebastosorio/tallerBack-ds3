package co.edu.unbosque.taller3.dto;

public class PokemonColorDTO {

	
	private String name;
	private String url;
	
	public PokemonColorDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonColorDTO(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "PokemonColorDTO [name=" + name + ", url=" + url + "]";
	}
	
}
