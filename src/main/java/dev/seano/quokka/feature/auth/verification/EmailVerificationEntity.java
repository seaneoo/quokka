package dev.seano.quokka.feature.auth.verification;

import dev.seano.quokka.feature.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;
import java.util.UUID;

@Table(name = "email_verifications")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String code;

	@Column(nullable = false, updatable = false)
	@CreatedDate
	private ZonedDateTime created;

	@Column(nullable = false, updatable = false)
	private ZonedDateTime expires;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@PrePersist
	private void prePersist() {
		code = UUID.randomUUID().toString();
		expires = created.plusSeconds(30);
	}
}
