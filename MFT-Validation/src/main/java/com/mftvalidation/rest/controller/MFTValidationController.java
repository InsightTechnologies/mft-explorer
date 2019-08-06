package com.mftvalidation.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mftvalidation.rest.api.utility.MFTValidationCommonUtility;
import com.mftvalidation.rest.beans.ValidationDetails;
import com.mftvalidation.rest.email.notification.EmailNotification;
import com.mftvalidation.rest.services.PathValidation;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/validate")
public class MFTValidationController {
	public static final Logger logger = LoggerFactory.getLogger(MFTValidationController.class);

	@Autowired
	MFTValidationCommonUtility mftValidationCommonUtility;

	@Autowired
	EmailNotification emailNotification;

	@Autowired
	PathValidation pathValidation;

	@PostMapping(value = "/path", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> pathValidation(@RequestBody ValidationDetails validationDetails) {
		String jsonResponse;
		try {
			boolean isExist = pathValidation.validatePath(validationDetails);
			jsonResponse = mftValidationCommonUtility.getJsonResponse(isExist, "OK");
		} catch (Exception ioExcep) {
			jsonResponse = mftValidationCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}
		return new ResponseEntity<String>(jsonResponse, MFTValidationCommonUtility.getHttpResponseHeader(),
				HttpStatus.OK);
	}

	@PostMapping(value = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> emailNotification(@RequestBody ValidationDetails validationDetails) {
		String jsonResponse;
		try {
			boolean emailSent = emailNotification.sendEmailNotification(validationDetails);
			jsonResponse = mftValidationCommonUtility.getJsonResponse(emailSent, "OK");
		} catch (Exception ioExcep) {
			jsonResponse = mftValidationCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}
		return new ResponseEntity<String>(jsonResponse, MFTValidationCommonUtility.getHttpResponseHeader(),
				HttpStatus.OK);
	}
}
