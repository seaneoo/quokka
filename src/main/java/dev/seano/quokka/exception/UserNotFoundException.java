package dev.seano.quokka.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public UserNotFoundException(String msg) {
		this(msg, null);
	}

	public UserNotFoundException() {
		this("User not found");
	}
}
