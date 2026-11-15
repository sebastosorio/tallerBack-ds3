package co.edu.unbosque.taller3.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PokemonService {

    @Value("${pokeapi.url}")
    private String baseUrl;

    public PokemonService() {
    }

   
    public String consultarPokemonesPorLimite(int limit) {
        String urlCompleta = baseUrl + "pokemon?limit=" + limit;
        return ExternalHTTPRequestHandler.enviarGet(urlCompleta);
    }
}