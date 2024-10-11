package dev.seano.quokka.feature.auth.req;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequest {

	@Size(min = 2, max = 20)
	@Pattern(regexp = "^\\w+$")
	private String username;

	@Size(min = 8, max = 255)
	private String password;
}
