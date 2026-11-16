package co.edu.unbosque.taller3.dto;

public class PokemonColorDTO {

	
	private String name;
	
	public PokemonColorDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonColorDTO(String name) {
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
		return "PokemonColorDTO [name=" + name + "]";
	}
	
}
