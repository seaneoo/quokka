package dev.seano.quokka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadCredentialsException extends ResponseStatusException {

	public BadCredentialsException(String reason, Throwable cause) {
		super(HttpStatus.UNAUTHORIZED, reason, cause);
	}

	public BadCredentialsException(String reason) {
		this(reason, null);
	}

	public BadCredentialsException() {
		this("Bad credentials");
	}
}
