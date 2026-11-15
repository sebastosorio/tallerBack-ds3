package co.edu.unbosque.taller3.configuration;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.edu.unbosque.taller3.model.User;
import co.edu.unbosque.taller3.repository.UserRepository;

@Configuration
public class LoadDataBase {

	private static final Logger log = LoggerFactory.getLogger(LoadDataBase.class);

	
	private static final String ADMIN_USER = "brandon";
	private static final String ADMIN_PASS = "cainegro";

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		return args -> {
			Optional<User> found = userRepo.findByUsername(ADMIN_USER);

			if (found.isPresent()) {
				User admin = found.get();
				boolean cambio = false;

				if (admin.getRole() != User.Role.ADMIN) {
					admin.setRole(User.Role.ADMIN);
					cambio = true;
				}
				
				if (admin.getPassword() == null || !admin.getPassword().startsWith("$2")) {
					admin.setPassword(passwordEncoder.encode(admin.getPassword()));
					cambio = true;
				}

				if (cambio) {
					userRepo.save(admin);
					log.info("Admin existente corregido (rol ADMIN y clave encriptada)");
				} else {
					log.info("Admin already exists, skipping admin creating ...");
				}
			} else {
				User admin = new User(ADMIN_USER, passwordEncoder.encode(ADMIN_PASS));
				admin.setRole(User.Role.ADMIN);
				userRepo.save(admin);
				log.info("Preloading admin user");
			}
		};
	}
}