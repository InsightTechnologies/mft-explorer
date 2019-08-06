package com.mftvalidation.rest.email.notification;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.mftvalidation.rest.beans.ValidationDetails;

@Configuration
public class EmailNotification {

	public static final Logger logger = LoggerFactory.getLogger(EmailNotification.class);

	@Autowired
	private EmailService emailService;

	public boolean sendEmailNotification(ValidationDetails validationDetails) {
		boolean emailSent = false;

		try {
			Mail mail = new Mail();
			mail.setFrom(validationDetails.getFrom());
			mail.setTo(validationDetails.getTo());
			mail.setSubject(validationDetails.getSubject());

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("name", "Customer");			
			mail.setModel(model);

			emailService.sendNotification(mail);
			emailSent = true;
		} catch (Exception e) {
			logger.error("SendEmailNotification : " + e.getMessage().toString().trim());
			emailSent = false;
		}

		return emailSent;
	}

}
