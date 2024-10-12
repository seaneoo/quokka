package dev.seano.quokka.feature.auth;

import dev.seano.quokka.exception.UserRegistrationException;
import dev.seano.quokka.exception.UsernameUnavailableException;
import dev.seano.quokka.feature.auth.req.UserRegisterRequest;
import dev.seano.quokka.feature.user.User;
import dev.seano.quokka.feature.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	public void register(UserRegisterRequest request) {
		log.debug("[AuthService::register] Creating new user with username '{}'", request.getUsername());

		// 409 Conflict: Username is unavailable
		if (userService.findByUsernameOptional(request.getUsername()).isPresent()) {
			log.debug("[AuthService::register] Failed to create new user '{}': Username is unavailable",
				request.getUsername());
			throw new UsernameUnavailableException();
		}

		// Build new user
		var user = User.builder()
			.username(request.getUsername())
			.password(passwordEncoder.encode(request.getPassword()))
			.build();

		try {
			// Save new user to database
			var createdUser = userService.save(user);
			log.debug("[AuthService::register] Created new user '{}'", createdUser.getId());
		} catch (Exception e) {
			log.debug("[AuthService::register] Failed to create new user '{}': {}", request.getUsername(),
				e.getMessage());
			// 500 Internal Server Error: Error while creating user
			throw new UserRegistrationException(e.getMessage());
		}
	}
}
