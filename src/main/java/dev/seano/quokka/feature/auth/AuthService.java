package dev.seano.quokka.feature.auth;

import dev.seano.quokka.feature.auth.verification.EmailVerificationService;
import dev.seano.quokka.feature.user.UserDTO;
import dev.seano.quokka.feature.user.UserEntity;
import dev.seano.quokka.feature.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AuthService {

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	private final EmailVerificationService emailVerificationService;

	public AuthService(UserService userService,
					   PasswordEncoder passwordEncoder,
					   EmailVerificationService emailVerificationService) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.emailVerificationService = emailVerificationService;
	}

	@Async
	@Transactional
	public CompletableFuture<UserDTO> register(RegisterRequest request) {
		log.debug("Creating new user with username '{}'", request.getUsername());
		var user = UserEntity.builder()
			.email(request.getEmail())
			.username(request.getUsername())
			.password(passwordEncoder.encode(request.getPassword()))
			.build();

		var userEntityCompletableFuture = CompletableFuture.supplyAsync(() -> userService.save(user));
		return userEntityCompletableFuture.thenApply(userEntity -> {
			log.debug("Created new user '{}'", userEntity.getId());
			emailVerificationService.sendVerificationEmail(userEntity);
			return new UserDTO(userEntity);
		});
	}

	@Transactional
	public UserDTO verifyEmail(String verificationCode) {
		var user = emailVerificationService.verify(verificationCode, userEntity -> {
			log.debug("Processing email verification with code '{}' for user '{}'", verificationCode,
				userEntity.getId());
			try {
				userService.save(userEntity);
			} catch (Exception e) {
				return false;
			}
			return true;
		});
		return new UserDTO(user);
	}
}
