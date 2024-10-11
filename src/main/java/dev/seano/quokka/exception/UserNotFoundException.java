package dev.seano.quokka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {

	public UserNotFoundException(String reason, Throwable cause) {
		super(HttpStatus.NOT_FOUND, reason, cause);
	}

	public UserNotFoundException(String reason) {
		this(reason, null);
	}

	public UserNotFoundException() {
		this("User not found");
	}
}
