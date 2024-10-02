package dev.seano.quokka.auth;

import dev.seano.quokka.QuokkaProperties;
import dev.seano.quokka.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {

	private final UserRepository userRepository;

	private final QuokkaProperties quokkaProperties;

	public AuthConfig(UserRepository userRepository, QuokkaProperties quokkaProperties) {
		this.userRepository = userRepository;
		this.quokkaProperties = quokkaProperties;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return username -> userRepository.findByUsernameIgnoreCase(username)
			.orElseThrow(() -> new UsernameNotFoundException(username));
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new Argon2PasswordEncoder(quokkaProperties.getArgon2().getSaltLength(),
			quokkaProperties.getArgon2().getHashLength(), quokkaProperties.getArgon2().getParallelism(),
			quokkaProperties.getArgon2().getMemory(), quokkaProperties.getArgon2().getIterations());
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		var authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
}
