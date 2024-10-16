package dev.seano.quokka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "quokka")
@Data
public class ApplicationProperties {

	/**
	 * The host name of the application. Should be something like "http://localhost:8080" or "https://quokka.com"
	 */
	@SuppressWarnings("JavadocLinkAsPlainText")
	private String host;

	/**
	 * The base URL of the application. The "host" and the "context-path" put together.
	 */
	private String baseUrl;

	private Argon2 argon2;

	@Data
	public static class Argon2 {

		/**
		 * The salt length (in bytes).
		 */
		private Integer saltLength;

		/**
		 * The hash length (in bytes).
		 */
		private Integer hashLength;

		/**
		 * Degree of parallelism (i.e. number of threads).
		 */
		private Integer parallelism;

		/**
		 * Amount of memory (in kibibytes) to use.
		 */
		private Integer memory;

		/**
		 * Number of iterations to perform.
		 */
		private Integer iterations;
	}
}
