package co.edu.unbosque.taller3.dto;

public class SpeciesRefDTO {

	
	private String name;
	private int numeroPokedex;
	
	public SpeciesRefDTO() {
		// TODO Auto-generated constructor stub
	}

	public SpeciesRefDTO(String name, int numeroPokedex) {
		super();
		this.name = name;
		this.numeroPokedex = numeroPokedex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumeroPokedex() {
		return numeroPokedex;
	}

	public void setNumeroPokedex(int numeroPokedex) {
		this.numeroPokedex = numeroPokedex;
	}

	@Override
	public String toString() {
		return "SpeciesRefDTO [name=" + name + ", numeroPokedex=" + numeroPokedex + "]";
	}

	
	
	
}
