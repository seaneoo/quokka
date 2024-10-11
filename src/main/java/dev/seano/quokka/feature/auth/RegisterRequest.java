package dev.seano.quokka.feature.auth;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

	@Size(min = 2, max = 20)
	@Pattern(regexp = "^\\w+$")
	private String username;

	@Size(min = 8, max = 255)
	private String password;
}
