package com.mqmft.mft.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MFTRequests {
	@Value("${spring.common.protocol}")
	private String http;
	@Value("${spring.mft.host}")
	private String mftHost;
	@Value("${spring.mft.port}")
	private String mftPort;
	@Value("${spring.mft.API}")
	private String mftAPI;
	
	public String createMonitorXML(Transfer_Details transferDetails) throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(transferDetails);
		return restTemplate.postForObject(new URI( buildAPIUri() + MFTConstants.getTransferXML),httpEntity,String.class);
	}

	private String buildAPIUri() {
		return (http + mftHost + ":" + mftPort + "/"
				+ mftAPI + "/");
	}

	public String deleteScheduler(String scheduleId, String host, String user) throws RestClientException, URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(new URI( buildAPIUri() + MFTConstants.deleteScheduler+""+scheduleId+"/"+host+"/"+user),String.class);
	}
	
	public String deleteMonitor(String monitor, String host, String user) throws RestClientException, URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(new URI( buildAPIUri() + MFTConstants.deleteMonitor+""+monitor+"/"+host+"/"+user),String.class);
	}
}
