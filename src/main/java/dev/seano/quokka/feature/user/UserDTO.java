package dev.seano.quokka.feature.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private UUID id;

	private String email;

	private String username;

	private Date created;

	@JsonProperty("last_modified")
	private Date lastModified;

	public UserDTO(UserEntity userEntity) {
		this(userEntity.getId(), userEntity.getEmail(), userEntity.getUsername(), userEntity.getCreated(),
			userEntity.getLastModified());
	}
}
