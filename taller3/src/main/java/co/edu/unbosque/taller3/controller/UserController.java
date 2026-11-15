package co.edu.unbosque.taller3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.taller3.dto.UserDTO;
import co.edu.unbosque.taller3.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081" })
@Tag(name = "Usuarios", description = "Gestion de usuarios con token del admin menos getall,cantidad users,get by id para ambos")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

	@Autowired
	private UserService userServ;

	public UserController() {
	}

	@GetMapping("/getall")
	ResponseEntity<List<UserDTO>> getAll() {
		return new ResponseEntity<>(userServ.getAll(), HttpStatus.OK);
	}

	@GetMapping("/count")
	ResponseEntity<Long> countAll() {
		return new ResponseEntity<>(userServ.count(), HttpStatus.OK);
	}

	@GetMapping("/getbyid/{id}")
	ResponseEntity<?> getById(@PathVariable Long id) {
		UserDTO found = userServ.getById(id);
		if (found != null) {
			return new ResponseEntity<>(found, HttpStatus.OK);
		}
		return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
	}

	@PostMapping(path = "/createjson", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> createWithJSON(@RequestBody UserDTO newUser) {
		if (!datosValidos(newUser)) {
			return new ResponseEntity<>("Username y password son obligatorios (sin < ni >)", HttpStatus.BAD_REQUEST);
		}
		int status = userServ.createWithRole(newUser);
		if (status == 0) {
			return new ResponseEntity<>("Usuario creado correctamente", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("No se pudo crear, el username ya esta en uso", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>("Rol invalido. Usa USER o ADMIN", HttpStatus.BAD_REQUEST);
	}

	@PutMapping(path = "/updatejson", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> updateWithJSON(@RequestParam Long id, @RequestBody UserDTO newData) {
		if (newData.getUsername() == null || newData.getUsername().isBlank() || newData.getUsername().contains("<")
				|| newData.getUsername().contains(">")) {
			return new ResponseEntity<>("El username es obligatorio (sin < ni >)", HttpStatus.BAD_REQUEST);
		}
		int status = userServ.updateById(id, newData);
		if (status == 0) {
			return new ResponseEntity<>("Usuario actualizado correctamente", HttpStatus.OK);
		} else if (status == 1) {
			return new ResponseEntity<>("Ese username ya lo tiene otro usuario", HttpStatus.CONFLICT);
		} else if (status == 2) {
			return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
		} else if (status == 3) {
			return new ResponseEntity<>("Rol invalido. Usa USER o ADMIN", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Error al actualizar", HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/deletebyid/{id}")
	ResponseEntity<String> deleteById(@PathVariable Long id) {
		if (userServ.deleteById(id) == 0) {
			return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
		}
		return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/deletebyname")
	ResponseEntity<String> deleteByName(@RequestParam String name) {
		if (userServ.deleteByUsername(name) == 0) {
			return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
		}
		return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
	}

	private boolean datosValidos(UserDTO u) {
		return u.getUsername() != null && !u.getUsername().isBlank() && u.getPassword() != null
				&& !u.getPassword().isBlank() && !u.getUsername().contains("<") && !u.getUsername().contains(">");
	}
}