package dev.seano.quokka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserDeletionException extends ResponseStatusException {

	public UserDeletionException(String reason, Throwable cause) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
	}

	public UserDeletionException(String reason) {
		this(reason, null);
	}

	public UserDeletionException() {
		this("Could not delete user");
	}
}
