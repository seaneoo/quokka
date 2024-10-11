package dev.seano.quokka.feature.auth.verification;

import dev.seano.quokka.ApplicationProperties;
import dev.seano.quokka.feature.user.UserEntity;
import dev.seano.quokka.mail.MailService;
import dev.seano.quokka.util.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class EmailVerificationService {

	private final EmailVerificationRepository emailVerificationRepository;

	private final MailService mailService;

	private final ApplicationProperties applicationProperties;

	public EmailVerificationService(EmailVerificationRepository emailVerificationRepository,
									MailService mailService,
									ApplicationProperties applicationProperties) {
		this.emailVerificationRepository = emailVerificationRepository;
		this.mailService = mailService;
		this.applicationProperties = applicationProperties;
	}

	public Optional<EmailVerificationEntity> findByCode(String code) {
		return emailVerificationRepository.findByCode(code);
	}

	public EmailVerificationEntity save(EmailVerificationEntity emailVerificationEntity) {
		return emailVerificationRepository.save(emailVerificationEntity);
	}

	public EmailVerificationEntity createVerificationCode(UserEntity user) {
		var verificationEntity = EmailVerificationEntity.builder().user(user).build();
		return save(verificationEntity);
	}

	@Async
	@Transactional
	public void sendVerificationEmail(UserEntity user) {
		log.debug("Creating email verification code for user '{}'", user.getId());
		var verificationEntity = createVerificationCode(user);

		log.debug("Sending verification email to user '{}'", user.getId());

		var verificationUrl = applicationProperties.getBaseUrl() + "/auth/verify?code=%s".formatted(
			verificationEntity.getCode());
		mailService.send(user, "Please verify your Quokka account", """
			<p>Hello, <strong>%s</strong>!</p><p><a href="%s">Click here to verify your email.</a></p><p>This link expires in 5 minutes.</p>""".formatted(
			user.getUsername(), verificationUrl));
	}

	@Transactional
	public UserEntity verify(String verificationCode, Function<UserEntity, Boolean> callback) {
		log.debug("Checking email verification with code '{}'", verificationCode);

		var verificationEntityOptional = findByCode(verificationCode);
		if (verificationEntityOptional.isEmpty()) throw new RuntimeException("Invalid verification code");

		var expires = verificationEntityOptional.get().getExpires();
		if (expires.isBefore(DateTimeUtils.now())) throw new RuntimeException("Expired verification code");

		var user = verificationEntityOptional.get().getUser();
		if (user.isEmailVerified()) throw new RuntimeException("Email already verified");
		log.debug("Found user '{}' for email verification code '{}'", user.getId(), verificationCode);
		user.setEmailVerified(true);

		if (callback.apply(user)) {
			log.debug("Email verification callback succeeded for user '{}'", user.getId());
			emailVerificationRepository.deleteById(verificationEntityOptional.get().getId());
		} else {
			throw new RuntimeException("Email verification failed");
		}

		return user;
	}
}
