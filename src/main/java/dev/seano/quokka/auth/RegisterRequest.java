package dev.seano.quokka.auth;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

	@Size(max = 255)
	@Pattern(regexp = "^.+@.+$")
	private String email;

	@Size(min = 2, max = 20)
	@Pattern(regexp = "^\\w+$")
	private String username;

	@Size(min = 8, max = 255)
	private String password;
}
