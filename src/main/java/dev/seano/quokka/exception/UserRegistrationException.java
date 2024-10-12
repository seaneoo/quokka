package dev.seano.quokka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserRegistrationException extends ResponseStatusException {

	public UserRegistrationException(String reason, Throwable cause) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
	}

	public UserRegistrationException(String reason) {
		this(reason, null);
	}
}
