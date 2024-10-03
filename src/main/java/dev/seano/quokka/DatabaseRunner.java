package dev.seano.quokka;

import dev.seano.quokka.feature.user.UserEntity;
import dev.seano.quokka.feature.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DatabaseRunner implements CommandLineRunner {

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	public DatabaseRunner(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		try {
			var user = UserEntity.builder()
				.email("user@example.com")
				.username("user")
				.password(passwordEncoder.encode("changeme"))
				.emailVerified(true)
				.build();
			userService.save(user);
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
	}
}
