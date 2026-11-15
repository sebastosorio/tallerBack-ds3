package co.edu.unbosque.taller3.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.edu.unbosque.taller3.dto.PokemonDTO;
import co.edu.unbosque.taller3.dto.PokemonEspecieDTO;
import co.edu.unbosque.taller3.dto.PokemonEvolucionDTO;

@Service
public class PokemonService {

    @Value("${pokeapi.url}")
    private String baseUrl;

    public PokemonService() {
    }

    public List<PokemonDTO> consultarPokemonesPorLimite(int limit) {
        String urlCompleta = baseUrl + "pokemon?limit=" + limit;
        return ExternalHTTPRequestHandler.consultarListaPokemones(urlCompleta);
    }

    public PokemonDTO consultarPokemonPorNombre(String nombre) {
        String urlCompleta = baseUrl + "pokemon/" + nombre.toLowerCase();
        return ExternalHTTPRequestHandler.consultarPokemonUnico(urlCompleta);
    }

    public PokemonEspecieDTO consultarEspeciePokemon(String nombreOEspecie) {
        String urlCompleta = baseUrl + "pokemon-species/" + nombreOEspecie.toLowerCase();
        return ExternalHTTPRequestHandler.consultarEspeciePokemon(urlCompleta);
    }

    public PokemonEvolucionDTO consultarEvolucionPokemon(int id) {
        String urlCompleta = baseUrl + "evolution-chain/" + id + "/";
        return ExternalHTTPRequestHandler.consultarEvolucionPokemon(urlCompleta);
    }
}