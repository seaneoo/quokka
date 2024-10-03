package dev.seano.quokka.mail;

import dev.seano.quokka.ApplicationProperties;
import dev.seano.quokka.feature.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
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
	public void send(String to, String subject, String message) {
		try {
			var mailMessage = mailSender.createMimeMessage();
			mailMessage.setSubject(subject);
			var helper = new MimeMessageHelper(mailMessage, true);
			helper.setFrom(applicationProperties.getMail().getFrom());
			helper.setTo(to);
			helper.setText(message, true);
			mailSender.send(mailMessage);
		} catch (Exception e) {
			log.warn("Could not send email", e);
		}
	}

	@Async
	public void send(UserEntity user, String subject, String message) {
		send(user.getEmail(), subject, message);
	}
}
