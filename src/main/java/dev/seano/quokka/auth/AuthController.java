package dev.seano.quokka.auth;

import dev.seano.quokka.user.UserDTO;
import dev.seano.quokka.user.UserEntity;
import dev.seano.quokka.user.UserService;
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

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid RegisterRequest request) {
		var user = UserEntity.builder()
			.username(request.getUsername())
			.password(passwordEncoder.encode(request.getPassword()))
			.build();
		var createdUser = userService.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(createdUser));
	}
}
