package dev.seano.quokka.feature.auth;

import dev.seano.quokka.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {

	private final CustomUserDetailsService userDetailsService;

	private final ApplicationProperties applicationProperties;

	public AuthConfig(CustomUserDetailsService userDetailsService, ApplicationProperties applicationProperties) {
		this.userDetailsService = userDetailsService;
		this.applicationProperties = applicationProperties;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new Argon2PasswordEncoder(applicationProperties.getArgon2().getSaltLength(),
			applicationProperties.getArgon2().getHashLength(), applicationProperties.getArgon2().getParallelism(),
			applicationProperties.getArgon2().getMemory(), applicationProperties.getArgon2().getIterations());
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		var authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
}
