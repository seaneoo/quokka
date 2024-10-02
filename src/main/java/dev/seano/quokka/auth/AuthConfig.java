package dev.seano.quokka.auth;

import dev.seano.quokka.ApplicationProperties;
import dev.seano.quokka.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {

	private final UserService userService;

	private final ApplicationProperties applicationProperties;

	public AuthConfig(UserService userService, ApplicationProperties applicationProperties) {
		this.userService = userService;
		this.applicationProperties = applicationProperties;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return userService::findByUsername;
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
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
}
