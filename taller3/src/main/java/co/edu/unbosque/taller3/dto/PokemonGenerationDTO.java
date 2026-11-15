package co.edu.unbosque.taller3.dto;

public class PokemonGenerationDTO {
	
	private String name;
	private String url;
	
	public PokemonGenerationDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonGenerationDTO(String name, String url) {
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
		return "PokemonGenerationDTO [name=" + name + ", url=" + url + "]";
	}
	
	

}
