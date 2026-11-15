package co.edu.unbosque.taller3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.taller3.dto.PokemonDTO;
import co.edu.unbosque.taller3.dto.PokemonEspecieDTO;
import co.edu.unbosque.taller3.dto.PokemonEvolucionDTO;
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
		List<PokemonDTO> lista = pokemonServ.consultarPokemonesPorLimite(limit);
		if (lista == null) {
			return new ResponseEntity<>("No se pudo consultar la API externa de pokemones", HttpStatus.BAD_GATEWAY);
		}
		if (lista.isEmpty()) {
			return new ResponseEntity<>("No se encontraron pokemones", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping("/buscar")
	public ResponseEntity<?> buscarPorNombre(@RequestParam String nombre) {
		if (nombre == null || nombre.isBlank()) {
			return new ResponseEntity<>("Debes escribir el nombre de un pokemon", HttpStatus.BAD_REQUEST);
		}
		PokemonDTO pokemon = pokemonServ.consultarPokemonPorNombre(nombre);
		return armarRespuestaObjeto(pokemon, "No se encontro ningun pokemon con ese nombre (ej: pikachu)");
	}

	@GetMapping("/especie/{nombre}")
	public ResponseEntity<?> buscarEspecie(@PathVariable String nombre) {
		if (nombre == null || nombre.isBlank()) {
			return new ResponseEntity<>("Debes escribir el nombre o especie del pokemon", HttpStatus.BAD_REQUEST);
		}
		PokemonEspecieDTO especie = pokemonServ.consultarEspeciePokemon(nombre);
		return armarRespuestaObjeto(especie, "No se encontro ninguna especie con ese nombre");
	}

	@GetMapping("/evolucion/{id}")
	public ResponseEntity<?> buscarEvolucion(@PathVariable int id) {
		if (id <= 0) {
			return new ResponseEntity<>("El ID de evolucion debe ser mayor a 0", HttpStatus.BAD_REQUEST);
		}
		PokemonEvolucionDTO evolucion = pokemonServ.consultarEvolucionPokemon(id);
		return armarRespuestaObjeto(evolucion, "No se encontro ninguna cadena de evolucion con ese ID");
	}

	private ResponseEntity<?> armarRespuestaObjeto(Object resultado, String mensajeNoEncontrado) {
		if (resultado == null) {
			return new ResponseEntity<>(mensajeNoEncontrado + " (o fallo la conexion con la API externa)",
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}
}