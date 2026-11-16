package co.edu.unbosque.taller3.dto;

public class PokemonDTO {

	
	private String name;
	private int height;
	private int weight;
	private String tipo;
	
	public PokemonDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonDTO(String name, int height, int weight, String tipo) {
		super();
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.tipo = tipo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "PokemonDTO [name=" + name + ", height=" + height + ", weight=" + weight + ", tipo=" + tipo + "]";
	}

	

	
	
	
}
