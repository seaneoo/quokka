package dev.seano.quokka.auth;

import dev.seano.quokka.user.User;
import dev.seano.quokka.user.UserEntity;
import dev.seano.quokka.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody @Valid RegisterRequest request) {
		var user = UserEntity.builder()
			.username(request.getUsername())
			.password(passwordEncoder.encode(request.getPassword()))
			.build();
		var createdUser = userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(new User(createdUser));
	}
}
