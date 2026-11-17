package co.edu.unbosque.taller3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.edu.unbosque.taller3.dto.ImagenDTO;
import co.edu.unbosque.taller3.exception.ImagenApiException;
import co.edu.unbosque.taller3.exception.ImagenNotFoundException;

@Service
public class ImagenService {

	@Value("${pixabay.url}")
	private String baseUrl;

	@Value("${pixabay.api.key}")
	private String apiKey;

	public ImagenService() {
	}

	public List<ImagenDTO> buscar(String query, int cantidad) throws ImagenApiException, ImagenNotFoundException {
		int perPage = Math.max(cantidad, 3);
		String urlCompleta = baseUrl + "?key=" + apiKey + "&q=" + ExternalHTTPRequestHandler.codificar(query)
				+ "&image_type=photo&safesearch=true&per_page=" + perPage;

		List<ImagenDTO> lista = ExternalHTTPRequestHandler.consultarImagenes(urlCompleta, cantidad);
		if (lista.isEmpty()) {
			throw new ImagenNotFoundException("No se encontraron imagenes para: " + query);
		}
		return lista;
	}

	public byte[] obtenerImagen(String query, int posicion) throws ImagenApiException, ImagenNotFoundException {
		List<ImagenDTO> lista = buscar(query, posicion + 1);

		if (lista.size() <= posicion) {
			throw new ImagenNotFoundException("No hay una imagen en la posicion " + posicion + " para: " + query);
		}
		return ExternalHTTPRequestHandler.descargarImagen(lista.get(posicion).getUrlImagen());
	}
}