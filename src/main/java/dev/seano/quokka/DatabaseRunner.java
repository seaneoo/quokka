package dev.seano.quokka;

import dev.seano.quokka.user.UserEntity;
import dev.seano.quokka.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
			var user = UserEntity.builder().username("user").password(passwordEncoder.encode("changeme")).build();
			userRepository.save(user);
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
	}
}
