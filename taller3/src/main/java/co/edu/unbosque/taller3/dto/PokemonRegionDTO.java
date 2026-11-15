package co.edu.unbosque.taller3.dto;

public class PokemonRegionDTO {

	
	private String name;
	private String url;
	
	public PokemonRegionDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonRegionDTO(String name, String url) {
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
		return "PokemonRegionDTO [name=" + name + ", url=" + url + "]";
	}
	
	
}
