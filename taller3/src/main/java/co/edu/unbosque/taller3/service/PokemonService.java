package co.edu.unbosque.taller3.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import co.edu.unbosque.taller3.dto.PokemonDTO;
import co.edu.unbosque.taller3.dto.PokemonEspecieDTO;
import co.edu.unbosque.taller3.dto.PokemonEvolucionDTO;
import co.edu.unbosque.taller3.exception.PokemonApiException;
import co.edu.unbosque.taller3.exception.PokemonNotFoundException;

@Service
public class PokemonService {

	@Value("${pokeapi.url}")
	private String baseUrl;

	public PokemonService() {
	}

	public List<PokemonDTO> consultarPokemonesPorLimite(int limit)
			throws PokemonApiException, PokemonNotFoundException {
		String urlCompleta = baseUrl + "pokemon?limit=" + limit;
		List<PokemonDTO> lista = ExternalHTTPRequestHandler.consultarListaPokemones(urlCompleta);
		if (lista.isEmpty()) {
			throw new PokemonNotFoundException("No se encontraron pokemones");
		}
		return lista;
	}

	public PokemonDTO consultarPokemonPorNombre(String nombre) throws PokemonApiException, PokemonNotFoundException {
		String urlCompleta = baseUrl + "pokemon/" + nombre.trim().toLowerCase();
		return ExternalHTTPRequestHandler.consultarPokemonUnico(urlCompleta);
	}

	public PokemonEspecieDTO consultarEspeciePokemon(String nombreOEspecie)
			throws PokemonApiException, PokemonNotFoundException {
		String urlCompleta = baseUrl + "pokemon-species/" + nombreOEspecie.trim().toLowerCase();
		return ExternalHTTPRequestHandler.consultarEspeciePokemon(urlCompleta);
	}

	public PokemonEvolucionDTO consultarEvolucionPokemon(int id) throws PokemonApiException, PokemonNotFoundException {
		String urlCompleta = baseUrl + "evolution-chain/" + id + "/";
		return ExternalHTTPRequestHandler.consultarEvolucionPokemon(urlCompleta);
	}
}