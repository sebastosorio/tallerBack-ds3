package co.edu.unbosque.taller3.dto;

public class PokemonTipoDTO {

	
	private String name;
	
	public PokemonTipoDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonTipoDTO(String name) {
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
		return "PokemonTipoDTO [name=" + name +"]";
	}
	
}
