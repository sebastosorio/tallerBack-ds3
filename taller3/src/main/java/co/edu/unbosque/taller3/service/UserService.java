package co.edu.unbosque.taller3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.unbosque.taller3.dto.UserDTO;
import co.edu.unbosque.taller3.model.User;
import co.edu.unbosque.taller3.repository.UserRepository;

@Service
public class UserService implements CRUDOperation<UserDTO> {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserService() {
	}

	@Override
	public long count() {
		return userRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return userRepo.existsById(id);
	}

	@Override
	public int create(UserDTO data) {
		if (findUsernameAlreadyTaken(data.getUsername())) {
			return 1;
		}

		User entity = new User(data.getUsername(), passwordEncoder.encode(data.getPassword()));
		userRepo.save(entity);
		return 0;
	}

	public int createWithRole(UserDTO data) {
		if (findUsernameAlreadyTaken(data.getUsername())) {
			return 1;
		}
		User.Role role = parseRole(data.getRole());
		if (role == null) {
			return 2;
		}
		User entity = new User(data.getUsername(), passwordEncoder.encode(data.getPassword()));
		entity.setRole(role);
		userRepo.save(entity);
		return 0;
	}

	private User.Role parseRole(String role) {
		if (role == null || role.isBlank()) {
			return User.Role.USER;
		}
		try {
			return User.Role.valueOf(role.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	@Override
	public List<UserDTO> getAll() {
		List<User> entityList = userRepo.findAll();
		List<UserDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<User> found = userRepo.findById(id);
		if (found.isPresent()) {
			userRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	public int deleteByUsername(String username) {
		Optional<User> found = userRepo.findByUsername(username);
		if (found.isPresent()) {
			userRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, UserDTO newData) {
		Optional<User> found = userRepo.findById(id);
		if (!found.isPresent()) {
			return 2; // no existe
		}

		User.Role newRole = null;
		if (newData.getRole() != null && !newData.getRole().isBlank()) {
			newRole = parseRole(newData.getRole());
			if (newRole == null) {
				return 3; // rol invalido
			}
		}

		Optional<User> sameName = userRepo.findByUsername(newData.getUsername());
		if (sameName.isPresent() && !sameName.get().getId().equals(id)) {
			return 1; // username ya lo tiene otro usuario
		}

		User temp = found.get();
		temp.setUsername(newData.getUsername());
		if (newData.getPassword() != null && !newData.getPassword().isBlank()) {
			temp.setPassword(passwordEncoder.encode(newData.getPassword()));
		}
		if (newRole != null) {
			temp.setRole(newRole);
		}
		userRepo.save(temp);
		return 0;
	}

	public UserDTO getById(Long id) {
		Optional<User> found = userRepo.findById(id);
		if (found.isPresent()) {
			return toDTO(found.get());
		} else {
			return null;
		}
	}

	public boolean findUsernameAlreadyTaken(String username) {
		return userRepo.findByUsername(username).isPresent();
	}

	public int validateCredentials(String username, String password) {
		Optional<User> userOpt = userRepo.findByUsername(username);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			if (passwordEncoder.matches(password, user.getPassword())) {
				return 0; // correcto
			}
		}
		return 1; // credenciales invalidas
	}

	private UserDTO toDTO(User entity) {
		UserDTO dto = new UserDTO();
		dto.setId(entity.getId());
		dto.setUsername(entity.getUsername());
		dto.setRole(entity.getRole().name());
		return dto;
	}
}