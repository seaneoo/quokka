package dev.seano.quokka.feature.auth;

import dev.seano.quokka.feature.auth.req.UserRegisterRequest;
import dev.seano.quokka.feature.user.UserEntity;
import dev.seano.quokka.feature.user.UserService;
import dev.seano.quokka.feature.user.res.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuthService {

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public UserResponse register(UserRegisterRequest request) {
		log.debug("Creating new user with username '{}'", request.getUsername());
		var user = UserEntity.builder()
			.username(request.getUsername())
			.password(passwordEncoder.encode(request.getPassword()))
			.build();

		var createdUser = userService.save(user);
		log.debug("Created new user '{}'", createdUser.getId());
		return new UserResponse(createdUser);
	}
}
