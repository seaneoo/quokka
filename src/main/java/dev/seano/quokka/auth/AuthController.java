package dev.seano.quokka.auth;

import dev.seano.quokka.user.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid RegisterRequest request) throws ExecutionException, InterruptedException {
		var user = authService.register(request).get();
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
}
