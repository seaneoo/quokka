package dev.seano.quokka.feature.auth;

import dev.seano.quokka.feature.auth.req.UserAuthenticationRequest;
import dev.seano.quokka.feature.auth.req.UserRegisterRequest;
import dev.seano.quokka.feature.user.res.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody @Valid UserRegisterRequest request) {
		authService.register(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/authenticate")
	public ResponseEntity<UserResponse> authenticate(@RequestBody UserAuthenticationRequest request) {
		var user = authService.authenticate(request);
		return ResponseEntity.ok(user);
	}
}
