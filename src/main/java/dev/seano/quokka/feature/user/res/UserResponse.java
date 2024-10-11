package dev.seano.quokka.feature.user.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.seano.quokka.feature.user.UserEntity;
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

	public UserResponse(UserEntity userEntity) {
		this(userEntity.getId(), userEntity.getUsername(), userEntity.getCreated(), userEntity.getLastModified());
	}
}
