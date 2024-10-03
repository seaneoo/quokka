package dev.seano.quokka.user;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<UserDTO> findAll() {
		return userRepository.findAll().stream().map(UserDTO::new).toList();
	}

	public UserEntity findByEmail(String email) {
		return userRepository.findByEmailIgnoreCase(email)
			.orElseThrow(() -> new UsernameNotFoundException("Email not found"));
	}

	public UserEntity save(UserEntity userEntity) {
		return userRepository.save(userEntity);
	}
}
