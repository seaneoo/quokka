package dev.seano.quokka.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class User {

	private UUID id;

	private String username;

	private Date created;

	@JsonProperty("last_modified")
	private Date lastModified;

	public User(UserEntity userEntity) {
		this(userEntity.getId(), userEntity.getUsername(), userEntity.getCreated(), userEntity.getLastModified());
	}
}
