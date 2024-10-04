package dev.seano.quokka.feature.user;

import dev.seano.quokka.feature.auth.verification.EmailVerificationEntity;
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

import java.util.*;

@Table(name = "users")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(unique = true, nullable = false)
	@ColumnTransformer(write = "LOWER(?)")
	private String email;

	@Column(unique = true, nullable = false, length = 20)
	@ColumnTransformer(write = "LOWER(?)")
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, updatable = false)
	@CreatedDate
	private Date created;

	@Column(nullable = false)
	@LastModifiedDate
	private Date lastModified;

	@Column(nullable = false)
	@Builder.Default
	private boolean enabled = true;

	@Column(nullable = false)
	@Builder.Default
	private boolean emailVerified = false;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@Builder.Default
	private Set<EmailVerificationEntity> emailVerifications = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}
}
