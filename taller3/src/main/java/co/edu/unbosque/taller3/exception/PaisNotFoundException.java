package co.edu.unbosque.taller3.exception;

public class PaisNotFoundException extends Exception {

	private static final long serialVersionUID = 4817350263951472001L;

	public PaisNotFoundException() {
		super("No se encontro ningun pais con ese criterio de busqueda.");
	}

	public PaisNotFoundException(String mensaje) {
		super(mensaje);
	}
}