package dev.seano.quokka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "quokka")
@Data
public class ApplicationProperties {

	private Argon2 argon2;

	private Mail mail;

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

	@Data
	public static class Mail {

		/**
		 * The email address to send mail from.
		 */
		private String from;
	}
}
