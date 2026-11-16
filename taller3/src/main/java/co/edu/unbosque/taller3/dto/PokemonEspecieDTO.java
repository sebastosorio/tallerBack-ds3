package co.edu.unbosque.taller3.dto;

public class PokemonEspecieDTO {

	
	private String name;
	private PokemonColorDTO color;
	private PokemonGenerationDTO generacion;
	private int idCadenaEvolutiva;
	private String region;
	
	public PokemonEspecieDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonEspecieDTO(String name, PokemonColorDTO color, PokemonGenerationDTO generacion, int idCadenaEvolutiva,
			String region) {
		super();
		this.name = name;
		this.color = color;
		this.generacion = generacion;
		this.idCadenaEvolutiva = idCadenaEvolutiva;
		this.region = region;
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

	public int getIdCadenaEvolutiva() {
		return idCadenaEvolutiva;
	}

	public void setIdCadenaEvolutiva(int idCadenaEvolutiva) {
		this.idCadenaEvolutiva = idCadenaEvolutiva;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "PokemonEspecieDTO [name=" + name + ", color=" + color + ", generacion=" + generacion
				+ ", idCadenaEvolutiva=" + idCadenaEvolutiva + ", region=" + region + "]";
	}

	
	
	
	
}
