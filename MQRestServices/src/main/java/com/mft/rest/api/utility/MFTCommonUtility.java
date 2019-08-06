package com.mft.rest.api.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class MFTCommonUtility {
	@Bean
	public static HttpHeaders getHttpResponseHeader() {
		HttpHeaders responseHeaders = new HttpHeaders();
		return responseHeaders;
	}
}
