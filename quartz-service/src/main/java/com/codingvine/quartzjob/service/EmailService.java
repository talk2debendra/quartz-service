package com.codingvine.quartzjob.service;

public interface EmailService {

	public void sendMail( String toEmail, String subject, String body);
}
