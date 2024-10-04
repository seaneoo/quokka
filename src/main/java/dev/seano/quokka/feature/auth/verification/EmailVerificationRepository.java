package dev.seano.quokka.feature.auth.verification;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationRepository extends CrudRepository<EmailVerificationEntity, Long> {

	Optional<EmailVerificationEntity> findByCode(String code);
}
