package co.edu.unbosque.taller3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.taller3.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByUsername(String username);

	public void deleteByUsername(String username);
}
