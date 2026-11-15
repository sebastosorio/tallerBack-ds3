package co.edu.unbosque.taller3.dto;

public class PokemonEspecieDTO {

	
	private String name;
	private PokemonColorDTO color;
	private PokemonGenerationDTO generacion;
	
	public PokemonEspecieDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonEspecieDTO(String name, PokemonColorDTO color, PokemonGenerationDTO generacion) {
		super();
		this.name = name;
		this.color = color;
		this.generacion = generacion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PokemonColorDTO getColor() {
		return color;
	}

	public void setColor(PokemonColorDTO color) {
		this.color = color;
	}

	public PokemonGenerationDTO getGeneracion() {
		return generacion;
	}

	public void setGeneracion(PokemonGenerationDTO generacion) {
		this.generacion = generacion;
	}

	@Override
	public String toString() {
		return "PokemonEspecieDTO [name=" + name + ", color=" + color + ", generacion=" + generacion + "]";
	}
	
	
	
}
