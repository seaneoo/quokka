package dev.seano.quokka.feature.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Table(name = "users")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(unique = true, nullable = false, length = 20)
	@ColumnTransformer(write = "LOWER(?)")
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, updatable = false)
	@CreatedDate
	private ZonedDateTime created;

	@Column(nullable = false)
	@LastModifiedDate
	private ZonedDateTime lastModified;

	@Column(nullable = false)
	@Builder.Default
	private boolean enabled = true;

	@Enumerated
	@Column(nullable = false)
	@Builder.Default
	private Role role = Role.USER;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(role.getAuthority());
	}
}
