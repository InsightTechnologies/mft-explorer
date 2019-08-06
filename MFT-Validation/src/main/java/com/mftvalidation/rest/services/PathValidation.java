package com.mftvalidation.rest.services;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mftvalidation.rest.api.utility.MFTValidationConstants;
import com.mftvalidation.rest.beans.ValidationDetails;

@Component
public class PathValidation {
	public static final Logger logger = LoggerFactory.getLogger(PathValidation.class);

	public boolean validatePath(ValidationDetails validationDetails) {
		File fileDirectory;
		boolean isExist = false;
		try {
			if (!validationDetails.getSrcPath().isEmpty()) {
				fileDirectory = new File(validationDetails.getSrcPath());
			} else {
				String sourcePath = MFTValidationConstants.userPath + "/" + validationDetails.getRegion() + "/"
						+ validationDetails.getSrcSystem() + "/EAI/" + validationDetails.getCountryCode() + "/"
						+ validationDetails.getDomain() + "/" + validationDetails.getRacfId();
				// fileDirectory = new File(sourcePath);
				fileDirectory = new File(MFTValidationConstants.userPath);
			}
			if (fileDirectory.exists()) {
				isExist = true;
			} else {
				isExist = false;
			}
		} catch (Exception e) {
			logger.error("PathValidation : " + e.getMessage().toString().trim());
		}
		return isExist;
	}

}
