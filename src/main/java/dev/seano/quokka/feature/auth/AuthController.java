package dev.seano.quokka.feature.auth;

import dev.seano.quokka.feature.user.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/verify")
	public ResponseEntity<UserDTO> verifyEmail(@RequestParam("code") String verificationCode) {
		var user = authService.verifyEmail(verificationCode);
		return ResponseEntity.ok(user);
	}
}
