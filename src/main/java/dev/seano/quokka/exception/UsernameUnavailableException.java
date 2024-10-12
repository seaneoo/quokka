package dev.seano.quokka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameUnavailableException extends ResponseStatusException {

	public UsernameUnavailableException(String reason, Throwable cause) {
		super(HttpStatus.CONFLICT, reason, cause);
	}

	public UsernameUnavailableException(String reason) {
		this(reason, null);
	}

	public UsernameUnavailableException() {
		this("Username is unavailable");
	}
}
