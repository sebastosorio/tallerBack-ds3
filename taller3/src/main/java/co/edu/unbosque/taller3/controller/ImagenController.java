package co.edu.unbosque.taller3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unbosque.taller3.exception.ImagenApiException;
import co.edu.unbosque.taller3.exception.ImagenNotFoundException;
import co.edu.unbosque.taller3.service.ImagenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/imagen")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081" })
@Tag(name = "Imagenes", description = "Espacio para buscar imagenes (requiere token)")
@SecurityRequirement(name = "bearerAuth")
public class ImagenController {

	@Autowired
	private ImagenService imagenServ;

	public ImagenController() {
	}

	@GetMapping("/buscar")
	ResponseEntity<?> buscar(@RequestParam String query, @RequestParam(defaultValue = "5") int cantidad) {
		if (query == null || query.isBlank()) {
			return new ResponseEntity<>("Debes escribir que imagen quieres buscar", HttpStatus.BAD_REQUEST);
		}
		if (cantidad < 1 || cantidad > 20) {
			return new ResponseEntity<>("La cantidad debe estar entre 1 y 20", HttpStatus.BAD_REQUEST);
		}
		try {
			return new ResponseEntity<>(imagenServ.buscar(query.trim(), cantidad), HttpStatus.OK);
		} catch (ImagenNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ImagenApiException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@GetMapping(path = "/ver", produces = MediaType.IMAGE_JPEG_VALUE)
	ResponseEntity<byte[]> ver(@RequestParam String query, @RequestParam(defaultValue = "0") int posicion) {
		if (query == null || query.isBlank() || posicion < 0 || posicion > 19) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			return new ResponseEntity<>(imagenServ.obtenerImagen(query.trim(), posicion), HttpStatus.OK);
		} catch (ImagenNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (ImagenApiException e) {
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
	}
}