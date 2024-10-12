package dev.seano.quokka.feature.auth.req;

import lombok.Data;

/**
 * This is near identical to {@link UserRegisterRequest} except this contains no validation.
 */
@Data
public class UserAuthenticationRequest {

	private String username;

	private String password;
}
