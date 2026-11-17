package co.edu.unbosque.taller3.dto;

import java.util.List;

public class PokemonChainLinkDTO {

	
	private boolean is_baby;
	private SpeciesRefDTO species;
	private List<PokemonChainLinkDTO>evolves_to;
	
	public PokemonChainLinkDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonChainLinkDTO(boolean is_baby, SpeciesRefDTO species, List<PokemonChainLinkDTO> evolves_to) {
		super();
		this.is_baby = is_baby;
		this.species = species;
		this.evolves_to = evolves_to;
	}

	public boolean isIs_baby() {
		return is_baby;
	}

	public void setIs_baby(boolean is_baby) {
		this.is_baby = is_baby;
	}

	public SpeciesRefDTO getSpecies() {
		return species;
	}

	public void setSpecies(SpeciesRefDTO species) {
		this.species = species;
	}

	public List<PokemonChainLinkDTO> getEvolves_to() {
		return evolves_to;
	}

	public void setEvolves_to(List<PokemonChainLinkDTO> evolves_to) {
		this.evolves_to = evolves_to;
	}

	@Override
	public String toString() {
		return "PokemonChainLinkDTO [is_baby=" + is_baby + ", species=" + species + ", evolves_to=" + evolves_to + "]";
	}
	
}
