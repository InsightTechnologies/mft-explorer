package com.mftvalidation.rest.api.utility;

import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class MFTValidationCommonUtility {
	
	public String getJsonResponse(Object result, String responseType) {
		JSONObject jsonResponse = new JSONObject();
		if (responseType.equalsIgnoreCase("Ok")) {
		jsonResponse.put("Success", true);
		jsonResponse.put("Message", "Ok");
		jsonResponse.put("Result", result);
		
		}
		else {
			jsonResponse.put("Success", false);
			jsonResponse.put("Message", responseType);
			jsonResponse.put("Result", "");
		}
		return jsonResponse.toString();
	}
	
	public static HttpHeaders getHttpResponseHeader() {
		HttpHeaders responseHeaders = new HttpHeaders();
//		responseHeaders.set("Access-Control-Allow-Origin", "*");
		return responseHeaders;
	}
}
