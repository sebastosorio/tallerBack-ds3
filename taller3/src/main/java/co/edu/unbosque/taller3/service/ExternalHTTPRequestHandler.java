package co.edu.unbosque.taller3.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import co.edu.unbosque.taller3.dto.PaisDTO;

public class ExternalHTTPRequestHandler {

	private static final String BASE_URL = "https://countries.dev";

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.followRedirects(HttpClient.Redirect.NORMAL).connectTimeout(Duration.ofSeconds(10)).build();

	public static List<PaisDTO> buscarPaisesPorNombre(String nombre) {
		return doGetAndConvertToPaisList(BASE_URL + "/name/" + codificar(nombre));
	}

	public static List<PaisDTO> buscarPaisesPorCodigo(String codigo) {
		return doGetAndConvertToPaisList(BASE_URL + "/alpha/" + codificar(codigo));
	}

	public static List<PaisDTO> buscarPaisesPorRegion(String region) {
		return doGetAndConvertToPaisList(BASE_URL + "/region/" + codificar(region));
	}

	public static List<PaisDTO> doGetAndConvertToPaisList(String url) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).header("Accept", "application/json")
				.timeout(Duration.ofSeconds(15)).build();

		HttpResponse<String> response = null;
		try {
			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}

		if (response == null) {
			return null;
		}


		List<PaisDTO> lista = new ArrayList<>();
		if (response.statusCode() == 404) {
			return lista;
		}
		if (response.statusCode() != 200) {
			return null;
		}

		try {
			JsonElement root = JsonParser.parseString(response.body());
			if (root.isJsonArray()) {
				for (JsonElement el : root.getAsJsonArray()) {
					if (el.isJsonObject()) {
						lista.add(convertirAPais(el.getAsJsonObject()));
					}
				}
			} else if (root.isJsonObject()) {
				lista.add(convertirAPais(root.getAsJsonObject()));
			}
		} catch (Exception e) {
			System.out.println("Respuesta inesperada: " + response.body());
			e.printStackTrace();
			return null;
		}
		return lista;
	}

	private static PaisDTO convertirAPais(JsonObject json) {
		PaisDTO pais = new PaisDTO();

		pais.setNombre(getString(json, "name"));
		pais.setCapital(getString(json, "capital"));
		pais.setRegion(getString(json, "subregion"));
		pais.setContinentes(getString(json, "region"));
		pais.setPoblacion(getLong(json, "population"));
		pais.setExtension(getDouble(json, "area"));
		pais.setFronteras(String.join(", ", getStringList(json, "borders")));

		List<String> idiomas = new ArrayList<>();
		JsonElement langs = json.get("languages");
		if (langs != null && langs.isJsonArray()) {
			for (JsonElement item : langs.getAsJsonArray()) {
				if (item.isJsonObject()) {
					String nombreIdioma = getString(item.getAsJsonObject(), "name");
					if (nombreIdioma != null) {
						idiomas.add(nombreIdioma);
					}
				}
			}
		}
		pais.setIdiomas(String.join(", ", idiomas));

		List<String> monedas = new ArrayList<>();
		JsonElement currs = json.get("currencies");
		if (currs != null && currs.isJsonArray()) {
			for (JsonElement item : currs.getAsJsonArray()) {
				if (item.isJsonObject()) {
					JsonObject moneda = item.getAsJsonObject();
					String codigo = getString(moneda, "code");
					String nombreMoneda = getString(moneda, "name");
					String simbolo = getString(moneda, "symbol");
					String texto = (nombreMoneda != null ? nombreMoneda : codigo) + " (" + codigo
							+ (simbolo != null ? ", " + simbolo : "") + ")";
					monedas.add(texto);
				}
			}
		}
		pais.setMonedas(String.join(", ", monedas));

		JsonElement latlng = json.get("latlng");
		if (latlng != null && latlng.isJsonArray()) {
			JsonArray arr = latlng.getAsJsonArray();
			if (arr.size() >= 2) {
				pais.setLatitud(arr.get(0).getAsDouble());
				pais.setLongitud(arr.get(1).getAsDouble());
			}
		}

		return pais;
	}
	
	public static String enviarGet(String url) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                return "Error en la petición HTTP. Código: " + response.statusCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Excepción: " + e.getMessage();
        }
    }

	private static String codificar(String texto) {
		return URLEncoder.encode(texto.trim(), StandardCharsets.UTF_8).replace("+", "%20");
	}

	private static String getString(JsonObject obj, String key) {
		JsonElement el = obj.get(key);
		return (el != null && el.isJsonPrimitive()) ? el.getAsString() : null;
	}

	private static long getLong(JsonObject obj, String key) {
		JsonElement el = obj.get(key);
		return (el != null && el.isJsonPrimitive()) ? el.getAsLong() : 0;
	}

	private static double getDouble(JsonObject obj, String key) {
		JsonElement el = obj.get(key);
		return (el != null && el.isJsonPrimitive()) ? el.getAsDouble() : 0;
	}

	private static List<String> getStringList(JsonObject obj, String key) {
		List<String> lista = new ArrayList<>();
		JsonElement el = obj.get(key);
		if (el != null && el.isJsonArray()) {
			for (JsonElement item : el.getAsJsonArray()) {
				if (item.isJsonPrimitive()) {
					lista.add(item.getAsString());
				}
			}
		}
		return lista;
	}
}