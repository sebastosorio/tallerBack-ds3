package co.edu.unbosque.taller3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unbosque.taller3.exception.PokemonApiException;
import co.edu.unbosque.taller3.exception.PokemonNotFoundException;
import co.edu.unbosque.taller3.service.PokemonService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pokemon")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081" })
@Tag(name = "Pokemones", description = "Espacio para obtener informacion de pokemones (requiere token)")
@SecurityRequirement(name = "bearerAuth")
public class PokemonController {

	@Autowired
	private PokemonService pokemonServ;

	public PokemonController() {
	}

	@GetMapping("/listar")
	public ResponseEntity<?> listarPorLimite(@RequestParam(defaultValue = "20") int limit) {
		if (limit <= 0) {
			return new ResponseEntity<>("El limite debe ser mayor a 0", HttpStatus.BAD_REQUEST);
		}
		try {
			return new ResponseEntity<>(pokemonServ.consultarPokemonesPorLimite(limit), HttpStatus.OK);
		} catch (PokemonNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (PokemonApiException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@GetMapping("/buscar")
	public ResponseEntity<?> buscarPorNombre(@RequestParam String nombre) {
		if (nombre == null || nombre.isBlank()) {
			return new ResponseEntity<>("Debes escribir el nombre de un pokemon", HttpStatus.BAD_REQUEST);
		}
		try {
			return new ResponseEntity<>(pokemonServ.consultarPokemonPorNombre(nombre), HttpStatus.OK);
		} catch (PokemonNotFoundException e) {
			return new ResponseEntity<>("No se encontro ningun pokemon con ese nombre (ej: pikachu)",
					HttpStatus.NOT_FOUND);
		} catch (PokemonApiException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@GetMapping("/especie/{nombre}")
	public ResponseEntity<?> buscarEspecie(@PathVariable String nombre) {
		if (nombre == null || nombre.isBlank()) {
			return new ResponseEntity<>("Debes escribir el nombre o especie del pokemon", HttpStatus.BAD_REQUEST);
		}
		try {
			return new ResponseEntity<>(pokemonServ.consultarEspeciePokemon(nombre), HttpStatus.OK);
		} catch (PokemonNotFoundException e) {
			return new ResponseEntity<>("No se encontro ninguna especie con ese nombre", HttpStatus.NOT_FOUND);
		} catch (PokemonApiException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@GetMapping("/evolucion/{id}")
	public ResponseEntity<?> buscarEvolucion(@PathVariable int id) {
		if (id <= 0) {
			return new ResponseEntity<>("El ID de evolucion debe ser mayor a 0", HttpStatus.BAD_REQUEST);
		}
		try {
			return new ResponseEntity<>(pokemonServ.consultarEvolucionPokemon(id), HttpStatus.OK);
		} catch (PokemonNotFoundException e) {
			return new ResponseEntity<>("No se encontro ninguna cadena de evolucion con ese ID", HttpStatus.NOT_FOUND);
		} catch (PokemonApiException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}
}