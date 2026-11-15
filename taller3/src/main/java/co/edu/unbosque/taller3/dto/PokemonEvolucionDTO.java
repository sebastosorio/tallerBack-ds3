package co.edu.unbosque.taller3.dto;

public class PokemonEvolucionDTO {

	
	private PokemonChainLinkDTO chain;
	
	public PokemonEvolucionDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonEvolucionDTO(PokemonChainLinkDTO chain) {
		super();
		this.chain = chain;
	}

	public PokemonChainLinkDTO getChain() {
		return chain;
	}

	public void setChain(PokemonChainLinkDTO chain) {
		this.chain = chain;
	}

	@Override
	public String toString() {
		return "PokemonEvolucionDTO [chain=" + chain + "]";
	}
	
	
}
