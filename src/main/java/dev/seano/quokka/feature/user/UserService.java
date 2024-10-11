package dev.seano.quokka.feature.user;

import dev.seano.quokka.exception.UserNotFoundException;
import dev.seano.quokka.feature.user.res.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<UserResponse> findAll() {
		return userRepository.findAll().stream().map(UserResponse::new).toList();
	}

	public User findByUsername(String username) {
		return userRepository.findByUsernameIgnoreCase(username).orElseThrow(UserNotFoundException::new);
	}

	public User save(User user) {
		return userRepository.save(user);
	}
}
