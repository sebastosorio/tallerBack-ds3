package co.edu.unbosque.taller3.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import co.edu.unbosque.taller3.dto.ImagenDTO;

@Service
public class ImagenService {

	@Value("${pixabay.url}")
	private String baseUrl;

	@Value("${pixabay.api.key}")
	private String apiKey;

	public ImagenService() {
	}

	public List<ImagenDTO> buscar(String query, int cantidad) {
		int perPage = Math.max(cantidad, 3);
		String urlCompleta = baseUrl + "?key=" + apiKey + "&q=" + ExternalHTTPRequestHandler.codificar(query)
				+ "&image_type=photo&safesearch=true&per_page=" + perPage;
		return ExternalHTTPRequestHandler.consultarImagenes(urlCompleta, cantidad);
	}

	public byte[] obtenerImagen(String query, int posicion) {
		List<ImagenDTO> lista = buscar(query, posicion + 1);

		if (lista == null) {
			return null;
		}
		if (lista.size() <= posicion) {
			return new byte[0];
		}
		return ExternalHTTPRequestHandler.descargarImagen(lista.get(posicion).getUrlImagen());
	}
}