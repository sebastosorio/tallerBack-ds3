package co.edu.unbosque.taller3.exception;

public class ImagenNotFoundException extends Exception {

	private static final long serialVersionUID = 4817350263951472005L;

	public ImagenNotFoundException() {
		super("No se encontraron imagenes para esa busqueda.");
	}

	public ImagenNotFoundException(String mensaje) {
		super(mensaje);
	}
}