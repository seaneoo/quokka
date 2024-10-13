package dev.seano.quokka.feature.user;

import dev.seano.quokka.exception.UserDeletionException;
import dev.seano.quokka.exception.UserNotFoundException;
import dev.seano.quokka.feature.user.res.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Page<UserResponse> findAllPageable(Pageable pageable) {
		var rawResults = userRepository.findAll(pageable);
		return rawResults.map(UserResponse::new);
	}

	public Optional<User> findByUsernameOptional(String username) {
		return userRepository.findByUsernameIgnoreCase(username);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsernameIgnoreCase(username).orElseThrow(UserNotFoundException::new);
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public void delete(User user) {
		log.debug("[UserService::delete] Attempting to delete user '{}'", user.getUsername());
		try {
			userRepository.delete(user);
		} catch (Exception e) {
			log.debug("[UserService::delete] Failed to delete user '{}': {}", user.getUsername(), e.getMessage());
			throw new UserDeletionException();
		}
	}
}
