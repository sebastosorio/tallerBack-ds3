package co.edu.unbosque.taller3.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import co.edu.unbosque.taller3.dto.ImagenDTO;

@Service
public class ImagenService {

	private static final String BASE_URL = "https://pixabay.com/api/";

	@Value("${pixabay.api.key}")
	private String apiKey;

	public ImagenService() {
	}

	public List<ImagenDTO> buscar(String query, int cantidad) {
		int perPage = Math.max(cantidad, 3);

		String url = BASE_URL + "?key=" + apiKey + "&q=" + URLEncoder.encode(query, StandardCharsets.UTF_8)
				+ "&image_type=photo&safesearch=true&per_page=" + perPage;

		try {
			HttpResponse<String> response = ExternalHTTPRequestHandler.doGet(url);

			if (response.statusCode() != 200) {
				System.out.println("Pixabay status code -> " + response.statusCode());
				return null;
			}

			JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();
			JsonArray hits = root.getAsJsonArray("hits");
			List<ImagenDTO> lista = new ArrayList<>();

			for (JsonElement element : hits) {
				if (lista.size() >= cantidad) {
					break;
				}
				JsonObject hit = element.getAsJsonObject();

				ImagenDTO dto = new ImagenDTO();
				dto.setId(hit.get("id").getAsLong());
				dto.setDescripcion(getString(hit, "tags"));
				dto.setUrlImagen(getString(hit, "webformatURL"));
				dto.setUrlPagina(getString(hit, "pageURL"));
				lista.add(dto);
			}
			return lista;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public byte[] obtenerImagen(String query, int posicion) {
		List<ImagenDTO> lista = buscar(query, posicion + 1);

		if (lista == null) {
			return null;
		}
		if (lista.size() <= posicion) {
			return new byte[0];
		}

		try {
			HttpResponse<byte[]> response = ExternalHTTPRequestHandler.doGetBytes(lista.get(posicion).getUrlImagen());

			if (response.statusCode() != 200) {
				System.out.println("Descarga imagen status code -> " + response.statusCode());
				return null;
			}
			return response.body();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getString(JsonObject obj, String key) {
		if (obj != null && obj.has(key) && !obj.get(key).isJsonNull()) {
			return obj.get(key).getAsString();
		}
		return "";
	}
}