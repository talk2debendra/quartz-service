package com.codingvine.quartzjob.service;

public interface EmailService {

	/**
	 * Sends an email based on the supplied data
	 * 
	 * @param toEmail Email receiver
	 * @param subject Email subject
	 * @param body Email body
	 * */
	void sendMail( String toEmail, String subject, String body);
}
