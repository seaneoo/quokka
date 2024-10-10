package dev.seano.quokka.config;

import dev.seano.quokka.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final ApplicationProperties applicationProperties;

	public SecurityConfig(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// TODO 2024-10-01, 19:16 Configure CORS and CSRF before production release
		// TODO 2024-10-01, 19:56 Disable httpBasic and implement custom filter
		http.cors(CorsConfigurer::disable)
			.csrf(CsrfConfigurer::disable)
			.httpBasic(Customizer.withDefaults())
			.formLogin(FormLoginConfigurer::disable)
			.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/user/**")
				.authenticated()
				.anyRequest()
				.permitAll())
			.sessionManagement(
				sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new Argon2PasswordEncoder(applicationProperties.getArgon2().getSaltLength(),
			applicationProperties.getArgon2().getHashLength(), applicationProperties.getArgon2().getParallelism(),
			applicationProperties.getArgon2().getMemory(), applicationProperties.getArgon2().getIterations());
	}
}
