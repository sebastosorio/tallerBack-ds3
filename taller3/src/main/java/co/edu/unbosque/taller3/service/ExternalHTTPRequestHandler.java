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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import co.edu.unbosque.taller3.dto.ImagenDTO;
import co.edu.unbosque.taller3.dto.PaisDTO;
import co.edu.unbosque.taller3.dto.PixabayHitDTO;
import co.edu.unbosque.taller3.dto.PixabayRespuestaDTO;
import co.edu.unbosque.taller3.dto.PokemonChainLinkDTO;
import co.edu.unbosque.taller3.dto.PokemonColorDTO;
import co.edu.unbosque.taller3.dto.PokemonDTO;
import co.edu.unbosque.taller3.dto.PokemonEspecieDTO;
import co.edu.unbosque.taller3.dto.PokemonEvolucionDTO;
import co.edu.unbosque.taller3.dto.PokemonGenerationDTO;
import co.edu.unbosque.taller3.dto.SpeciesRefDTO;
import co.edu.unbosque.taller3.exception.ImagenApiException;
import co.edu.unbosque.taller3.exception.PaisApiException;
import co.edu.unbosque.taller3.exception.PokemonApiException;
import co.edu.unbosque.taller3.exception.PokemonNotFoundException;

public class ExternalHTTPRequestHandler {

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.followRedirects(HttpClient.Redirect.NORMAL).connectTimeout(Duration.ofSeconds(10)).build();

	public static List<PaisDTO> doGetAndConvertToPaisList(String url) throws PaisApiException {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).header("Accept", "application/json")
				.timeout(Duration.ofSeconds(15)).build();

		HttpResponse<String> response = null;
		try {
			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (response == null) {
			throw new PaisApiException();
		}

		List<PaisDTO> lista = new ArrayList<>();
		if (response.statusCode() == 404) {
			return lista;
		}
		if (response.statusCode() != 200) {
			throw new PaisApiException();
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
			throw new PaisApiException("La API de paises devolvio una respuesta inesperada.");
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

	private static String obtenerJsonPokemon(String url) throws PokemonApiException, PokemonNotFoundException {
		HttpResponse<String> response = ejecutarGetPokemon(url);
		if (response == null) {
			throw new PokemonApiException();
		}
		if (response.statusCode() == 404) {
			throw new PokemonNotFoundException();
		}
		if (response.statusCode() != 200) {
			throw new PokemonApiException();
		}
		return response.body();
	}

	public static List<PokemonDTO> consultarListaPokemones(String url)
			throws PokemonApiException, PokemonNotFoundException {
		String body = obtenerJsonPokemon(url);

		List<PokemonDTO> lista = new ArrayList<>();
		try {
			JsonObject root = JsonParser.parseString(body).getAsJsonObject();
			if (root.has("results") && root.get("results").isJsonArray()) {
				for (JsonElement el : root.getAsJsonArray("results")) {
					if (el.isJsonObject()) {
						JsonObject obj = el.getAsJsonObject();
						PokemonDTO poke = new PokemonDTO();
						poke.setName(getString(obj, "name"));
						lista.add(poke);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PokemonApiException("La API de pokemones devolvio una respuesta inesperada.");
		}
		return lista;
	}

	public static PokemonDTO consultarPokemonUnico(String url) throws PokemonApiException, PokemonNotFoundException {
		String body = obtenerJsonPokemon(url);

		try {
			JsonObject json = JsonParser.parseString(body).getAsJsonObject();
			PokemonDTO pokemon = new PokemonDTO();
			pokemon.setName(getString(json, "name"));
			pokemon.setHeight(getInt(json, "height"));
			pokemon.setWeight(getInt(json, "weight"));
			return pokemon;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PokemonApiException("La API de pokemones devolvio una respuesta inesperada.");
		}
	}

	public static PokemonEspecieDTO consultarEspeciePokemon(String url)
			throws PokemonApiException, PokemonNotFoundException {
		String body = obtenerJsonPokemon(url);

		try {
			JsonObject json = JsonParser.parseString(body).getAsJsonObject();
			PokemonEspecieDTO especie = new PokemonEspecieDTO();
			especie.setName(getString(json, "name"));

			if (json.has("color") && json.get("color").isJsonObject()) {
				JsonObject colorObj = json.getAsJsonObject("color");
				PokemonColorDTO color = new PokemonColorDTO();
				color.setName(getString(colorObj, "name"));
				color.setUrl(getString(colorObj, "url"));
				especie.setColor(color);
			}

			if (json.has("generation") && json.get("generation").isJsonObject()) {
				JsonObject genObj = json.getAsJsonObject("generation");
				PokemonGenerationDTO generacion = new PokemonGenerationDTO();
				generacion.setName(getString(genObj, "name"));
				generacion.setUrl(getString(genObj, "url"));
				especie.setGeneracion(generacion);
			}

			return especie;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PokemonApiException("La API de pokemones devolvio una respuesta inesperada.");
		}
	}

	public static PokemonEvolucionDTO consultarEvolucionPokemon(String url)
			throws PokemonApiException, PokemonNotFoundException {
		String body = obtenerJsonPokemon(url);

		try {
			JsonObject json = JsonParser.parseString(body).getAsJsonObject();
			PokemonEvolucionDTO evolucion = new PokemonEvolucionDTO();

			if (json.has("chain") && json.get("chain").isJsonObject()) {
				evolucion.setChain(convertirAChainLink(json.getAsJsonObject("chain")));
			}

			return evolucion;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PokemonApiException("La API de pokemones devolvio una respuesta inesperada.");
		}
	}

	private static PokemonChainLinkDTO convertirAChainLink(JsonObject json) {
		PokemonChainLinkDTO link = new PokemonChainLinkDTO();

		JsonElement isBabyEl = json.get("is_baby");
		if (isBabyEl != null && isBabyEl.isJsonPrimitive()) {
			link.setIs_baby(isBabyEl.getAsBoolean());
		}

		if (json.has("species") && json.get("species").isJsonObject()) {
			JsonObject specObj = json.getAsJsonObject("species");
			SpeciesRefDTO species = new SpeciesRefDTO();
			species.setName(getString(specObj, "name"));
			species.setUrl(getString(specObj, "url"));
			link.setSpecies(species);
		}

		List<PokemonChainLinkDTO> evoluciones = new ArrayList<>();
		if (json.has("evolves_to") && json.get("evolves_to").isJsonArray()) {
			for (JsonElement item : json.getAsJsonArray("evolves_to")) {
				if (item.isJsonObject()) {
					evoluciones.add(convertirAChainLink(item.getAsJsonObject()));
				}
			}
		}
		link.setEvolves_to(evoluciones);

		return link;
	}

	private static HttpResponse<String> ejecutarGetPokemon(String url) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).header("Accept", "application/json")
				.timeout(Duration.ofSeconds(15)).build();

		try {
			return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<ImagenDTO> consultarImagenes(String url, int cantidad) throws ImagenApiException {
		HttpResponse<String> response = ejecutarGetImagen(url);
		if (response == null || response.statusCode() != 200) {
			throw new ImagenApiException();
		}

		PixabayRespuestaDTO respuesta = null;
		try {
			respuesta = new Gson().fromJson(response.body(), PixabayRespuestaDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (respuesta == null || respuesta.getHits() == null) {
			throw new ImagenApiException("La API de imagenes devolvio una respuesta inesperada.");
		}

		List<ImagenDTO> lista = new ArrayList<>();
		for (PixabayHitDTO hit : respuesta.getHits()) {
			if (lista.size() >= cantidad) {
				break;
			}
			ImagenDTO dto = new ImagenDTO();
			dto.setId(hit.getId());
			dto.setDescripcion(hit.getTags());
			dto.setUrlImagen(hit.getWebformatURL());
			dto.setUrlPagina(hit.getPageURL());
			lista.add(dto);
		}
		return lista;
	}

	public static byte[] descargarImagen(String url) throws ImagenApiException {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).header("User-Agent", "Mozilla/5.0")
				.timeout(Duration.ofSeconds(20)).build();

		try {
			HttpResponse<byte[]> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofByteArray());
			if (response.statusCode() == 200) {
				return response.body();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		throw new ImagenApiException("No se pudo descargar la imagen.");
	}

	private static HttpResponse<String> ejecutarGetImagen(String url) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).header("Accept", "application/json")
				.timeout(Duration.ofSeconds(15)).build();

		try {
			return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String codificar(String texto) {
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

	private static int getInt(JsonObject obj, String key) {
		JsonElement el = obj.get(key);
		return (el != null && el.isJsonPrimitive()) ? el.getAsInt() : 0;
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