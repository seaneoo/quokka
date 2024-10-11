package dev.seano.quokka.config;

import dev.seano.quokka.util.DateTimeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "utcDateTimeProvider")
public class JpaConfig {

	@Bean
	public DateTimeProvider utcDateTimeProvider() {
		return () -> Optional.of(DateTimeUtils.now());
	}
}
