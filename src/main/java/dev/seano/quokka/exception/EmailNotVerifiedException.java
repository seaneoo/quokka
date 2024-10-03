package dev.seano.quokka.exception;

public class EmailNotVerifiedException extends RuntimeException {

	public EmailNotVerifiedException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public EmailNotVerifiedException(String msg) {
		this(msg, null);
	}

	public EmailNotVerifiedException() {
		this("Email is not verified");
	}
}
