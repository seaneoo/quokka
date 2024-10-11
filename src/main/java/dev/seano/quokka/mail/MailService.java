package dev.seano.quokka.mail;

import dev.seano.quokka.ApplicationProperties;
import dev.seano.quokka.feature.user.UserEntity;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {

	private final JavaMailSender mailSender;

	private final ApplicationProperties applicationProperties;

	public MailService(JavaMailSender mailSender, ApplicationProperties applicationProperties) {
		this.mailSender = mailSender;
		this.applicationProperties = applicationProperties;
	}

	@Async
	public void send(String to, String subject, String message) throws MessagingException {
		log.debug("Attempting to send an email to '{}'", to);
		var mailMessage = mailSender.createMimeMessage();
		mailMessage.setSubject(subject);
		var helper = new MimeMessageHelper(mailMessage, true);
		helper.setFrom(applicationProperties.getMail().getFrom());
		helper.setTo(to);
		helper.setText(message, true);
		mailSender.send(mailMessage);
		log.debug("Successfully sent email to '{}'", to);
	}

	@Async
	public void sendWithRetry(String to, String subject, String message) {
		var retries = 3;
		var attempts = 0;
		while (attempts < retries) {
			try {
				send(to, subject, message);
				return;
			} catch (MailSendException e) {
				attempts++;
				if (attempts >= retries) {
					log.warn("Could not send email to '{}' after {} attempts", to, attempts, e);
				}
			} catch (Exception e) {
				log.warn("Messaging error when sending email to '{}' - will not attempt again", to, e);
				break;
			}
		}
	}

	@Async
	public void send(UserEntity user, String subject, String message) {
		sendWithRetry(user.getEmail(), subject, message);
	}
}
