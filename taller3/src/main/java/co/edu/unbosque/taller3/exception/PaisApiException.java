package co.edu.unbosque.taller3.exception;

public class PaisApiException extends Exception {

	private static final long serialVersionUID = 4817350263951472002L;

	public PaisApiException() {
		super("No se pudo consultar la API externa de paises.");
	}

	public PaisApiException(String mensaje) {
		super(mensaje);
	}
}