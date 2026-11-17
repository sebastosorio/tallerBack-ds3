package co.edu.unbosque.taller3.exception;

public class PokemonNotFoundException extends Exception {

	private static final long serialVersionUID = 4817350263951472003L;

	public PokemonNotFoundException() {
		super("No se encontro el pokemon, la especie o la evolucion solicitada.");
	}

	public PokemonNotFoundException(String mensaje) {
		super(mensaje);
	}
}