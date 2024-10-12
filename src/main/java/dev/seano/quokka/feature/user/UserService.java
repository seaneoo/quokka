package dev.seano.quokka.feature.user;

import dev.seano.quokka.exception.UserNotFoundException;
import dev.seano.quokka.feature.user.res.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
}
