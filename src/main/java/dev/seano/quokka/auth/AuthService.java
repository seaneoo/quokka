package dev.seano.quokka.auth;

import dev.seano.quokka.mail.MailService;
import dev.seano.quokka.user.UserDTO;
import dev.seano.quokka.user.UserEntity;
import dev.seano.quokka.user.UserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AuthService {

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	private final MailService mailService;

	public AuthService(UserService userService, PasswordEncoder passwordEncoder, MailService mailService) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.mailService = mailService;
	}

	@Async
	public void sendVerificationEmail(UserEntity user) {
		mailService.send(user, "Welcome to Quokka!", """
			<p>Hello, <strong>%s</strong>!</p>""".formatted(user.getUsername()));
	}

	@Async
	public CompletableFuture<UserDTO> register(RegisterRequest request) {
		var user = UserEntity.builder()
			.email(request.getEmail())
			.username(request.getUsername())
			.password(passwordEncoder.encode(request.getPassword()))
			.build();
		var createdUser = userService.save(user);
		sendVerificationEmail(createdUser);
		return CompletableFuture.completedFuture(new UserDTO(createdUser));
	}
}
