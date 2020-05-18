package com.codingvine.quartzjob.service.impl;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.codingvine.quartzjob.service.EmailService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class EmailServiceImpl implements EmailService{

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailProperties mailProperties;

	
	
	
	@Override
	public void sendMail(String toEmail, String subject, String body) {
		try {
			log.info("Sending Email to {}", toEmail);
			MimeMessage message = mailSender.createMimeMessage();

			MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
			messageHelper.setSubject(subject);
			messageHelper.setText(body, true);
			messageHelper.setFrom(mailProperties.getUsername());
			messageHelper.setTo(toEmail);

			mailSender.send(message);
		} catch (MessagingException ex) {
			log.error("Failed to send email to {}", toEmail);
		}

	}

}
