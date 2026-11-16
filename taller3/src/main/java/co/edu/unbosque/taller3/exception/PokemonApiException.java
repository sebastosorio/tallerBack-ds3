package co.edu.unbosque.taller3.exception;

public class PokemonApiException extends Exception {

	private static final long serialVersionUID = 4817350263951472004L;

	public PokemonApiException() {
		super("No se pudo consultar la API externa de pokemones.");
	}

	public PokemonApiException(String mensaje) {
		super(mensaje);
	}
}