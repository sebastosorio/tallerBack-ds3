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

import co.edu.unbosque.taller3.dto.PaisDTO;
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
		return armarRespuesta(paisServ.buscarPorNombre(nombre),
				"No se encontro ningun pais con ese nombre (escribelo en ingles, ej: germany)");
	}

	@GetMapping("/codigo/{codigo}")
	ResponseEntity<?> buscarPorCodigo(@PathVariable String codigo) {
		if (codigo == null || codigo.isBlank()) {
			return new ResponseEntity<>("Debes escribir un codigo de pais", HttpStatus.BAD_REQUEST);
		}
		return armarRespuesta(paisServ.buscarPorCodigo(codigo), "No se encontro ningun pais con ese codigo");
	}

	@GetMapping("/region/{region}")
	ResponseEntity<?> buscarPorRegion(@PathVariable String region) {
		return armarRespuesta(paisServ.buscarPorRegion(region),
				"Region no valida. Usa: Africa, Americas, Asia, Europe, Oceania o Polar");
	}

	private ResponseEntity<?> armarRespuesta(List<PaisDTO> paises, String mensajeNoEncontrado) {
		if (paises == null) {
			return new ResponseEntity<>("No se pudo consultar la API externa de paises", HttpStatus.BAD_GATEWAY);
		}
		if (paises.isEmpty()) {
			return new ResponseEntity<>(mensajeNoEncontrado, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(paises, HttpStatus.OK);
	}
}