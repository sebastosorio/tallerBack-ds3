package co.edu.unbosque.taller3.dto;

public class PokemonGenerationDTO {
	
	private String name;
	
	public PokemonGenerationDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonGenerationDTO(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	@Override
	public String toString() {
		return "PokemonGenerationDTO [name=" + name + "]";
	}
	
	

}
