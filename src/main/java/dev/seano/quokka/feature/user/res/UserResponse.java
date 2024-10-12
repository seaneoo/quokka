package dev.seano.quokka.feature.user.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.seano.quokka.feature.user.Role;
import dev.seano.quokka.feature.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

	private UUID id;

	private String username;

	private ZonedDateTime created;

	@JsonProperty("last_modified")
	private ZonedDateTime lastModified;

	private Role role;

	public UserResponse(User user) {
		this(user.getId(), user.getUsername(), user.getCreated(), user.getLastModified(), user.getRole());
	}
}
