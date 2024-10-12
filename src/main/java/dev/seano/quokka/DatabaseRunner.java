package dev.seano.quokka;

import dev.seano.quokka.feature.user.Role;
import dev.seano.quokka.feature.user.User;
import dev.seano.quokka.feature.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DatabaseRunner implements CommandLineRunner {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public DatabaseRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		try {
			var user = User.builder().username("ex_user").password(passwordEncoder.encode("changeme")).build();
			var admin = User.builder()
				.username("ex_admin")
				.password(passwordEncoder.encode("changeme"))
				.role(Role.ADMIN)
				.build();
			userRepository.saveAll(List.of(user, admin));
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
	}
}
