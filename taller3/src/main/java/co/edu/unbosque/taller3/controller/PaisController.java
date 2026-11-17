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
import co.edu.unbosque.taller3.exception.PaisApiException;
import co.edu.unbosque.taller3.exception.PaisNotFoundException;
import co.edu.unbosque.taller3.service.PaisService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pais")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081" })
@Tag(name = "Paises", description = "Espacio para obtener informacion de paises (requiere token)")
@SecurityRequirement(name = "bearerAuth")
public class PaisController {

	@Autowired
	private PaisService paisServ;

	public PaisController() {
	}

	@GetMapping("/buscar")
	ResponseEntity<?> buscarPorNombre(@RequestParam String nombre) {
		if (nombre == null || nombre.isBlank()) {
			return new ResponseEntity<>("Debes escribir el nombre de un pais", HttpStatus.BAD_REQUEST);
		}
		try {
			return new ResponseEntity<>(paisServ.buscarPorNombre(nombre), HttpStatus.OK);
		} catch (PaisNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (PaisApiException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@GetMapping("/codigo/{codigo}")
	ResponseEntity<?> buscarPorCodigo(@PathVariable String codigo) {
		if (codigo == null || codigo.isBlank()) {
			return new ResponseEntity<>("Debes escribir un codigo de pais", HttpStatus.BAD_REQUEST);
		}
		try {
			return new ResponseEntity<>(paisServ.buscarPorCodigo(codigo), HttpStatus.OK);
		} catch (PaisNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (PaisApiException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@GetMapping("/region/{region}")
	ResponseEntity<?> buscarPorRegion(@PathVariable String region) {
		try {
			return new ResponseEntity<>(paisServ.buscarPorRegion(region), HttpStatus.OK);
		} catch (PaisNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (PaisApiException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}
}