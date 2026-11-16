package co.edu.unbosque.taller3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.taller3.dto.ImagenDTO;
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
		return armarRespuesta(imagenServ.buscar(query.trim(), cantidad), "No se encontraron imagenes para: " + query);
	}

	@GetMapping(path = "/ver", produces = MediaType.IMAGE_JPEG_VALUE)
	ResponseEntity<byte[]> ver(@RequestParam String query, @RequestParam(defaultValue = "0") int posicion) {
		if (query == null || query.isBlank() || posicion < 0 || posicion > 19) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		byte[] imagen = imagenServ.obtenerImagen(query.trim(), posicion);

		if (imagen == null) {
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
		if (imagen.length == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(imagen, HttpStatus.OK);
	}

	private ResponseEntity<?> armarRespuesta(List<ImagenDTO> imagenes, String mensajeNoEncontrado) {
		if (imagenes == null) {
			return new ResponseEntity<>("No se pudo consultar la API externa de imagenes", HttpStatus.BAD_GATEWAY);
		}
		if (imagenes.isEmpty()) {
			return new ResponseEntity<>(mensajeNoEncontrado, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(imagenes, HttpStatus.OK);
	}
}