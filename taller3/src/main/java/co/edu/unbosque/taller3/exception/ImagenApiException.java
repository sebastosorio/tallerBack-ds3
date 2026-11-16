package co.edu.unbosque.taller3.exception;

public class ImagenApiException extends Exception {

	private static final long serialVersionUID = 4817350263951472006L;

	public ImagenApiException() {
		super("No se pudo consultar la API externa de imagenes.");
	}

	public ImagenApiException(String mensaje) {
		super(mensaje);
	}
}