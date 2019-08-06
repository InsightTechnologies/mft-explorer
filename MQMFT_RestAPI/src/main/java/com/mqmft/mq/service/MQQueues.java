package com.mqmft.mq.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
@SuppressWarnings("unchecked")

public class MQQueues {
	@Value("${spring.common.protocol}")
	private String http;
	@Value("${spring.mq.host}")
	private String queueHost;
	@Value("${spring.mq.port}")
	private String queuePort;
	@Value("${spring.mq.API}")
	private String queueAPI;
	
	
	public List<String> listLocalQueues(String connString) throws IOException {
	    RestTemplate restTemplate = new RestTemplate();
	    List<String> result = restTemplate.getForObject(buildAPIUri()+connString+"/queue/allLocal", List.class);
		return result;
	}

	public String putMessage(String msg, String connString, String queueName, String queueManager) throws IOException {
		RestTemplate restTemplate = new RestTemplate();
//		List<String> result = restTemplate.getForObject(apiUri+connString+MQQueueConstants.queuePut+queueName, List.class);
		restTemplate.put(buildAPIUri()+connString+MQQueueConstants.queuePutMsg+queueName+"/"+queueManager, msg);
		return "Message Put successfully";
		}
	
	public String put(String msg, String connString, String queueName) throws IOException {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(buildAPIUri()+connString+MQQueueConstants.queuePut+queueName, msg);
		return "Message Put successfully";
		
	}

	
	public String putHeaderMessage(Map<?,?> header, String msg, String connString, String queueName) throws IOException {
		// /queue/connString/put/queueName/rfh
		List keyArray = Arrays.asList("host","connection","content-length","user-agent","cache-control","origin","postman-token","content-type","accept","accept-encoding","accept-language","expect","accept-charset");
		HttpHeaders headers= new HttpHeaders();
		for (Entry<?, ?> entry : header.entrySet()) {
			if (!keyArray.contains((entry.getKey().toString())))
			headers.set((String)entry.getKey(), (String)entry.getValue());
		}
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> httpEntity = new HttpEntity<Object>(msg,headers);
		System.out.println(httpEntity);
		restTemplate.put(buildAPIUri()+connString+MQQueueConstants.queuePutHeader+queueName, httpEntity);
		return "Message With Header was put successfully";		
	}
	
	public Map<String, Object> getQProperties(String connString, String queueName) throws IOException {
		// /queue/connString/properties/queueName
		RestTemplate restTemplate = new RestTemplate();
	    Map<String,Object> result = restTemplate.getForObject(buildAPIUri()+connString+MQQueueConstants.queueProperties+queueName, Map.class);
		return result;
	}

	public List<String> getLocalQueues(String connString) throws IOException {
		// /queue/connString/local
		RestTemplate restTemplate = new RestTemplate();
	    List<String> result = restTemplate.getForObject(buildAPIUri()+connString+MQQueueConstants.queueLocal, List.class);
		return result;

	}
	
	public List<String> getSystemQueues(String connString) throws IOException {
		// /queue/connString/local
		RestTemplate restTemplate = new RestTemplate();
	    List<String> result = restTemplate.getForObject(buildAPIUri()+connString+MQQueueConstants.queueSystem, List.class);
		return result;

	}

	public List<String> browseMessages(String connString, String queue, int rangeStart, int rangeEnd) throws IOException {
		// /queue/connString/browseMessages/queue
		RestTemplate restTemplate = new RestTemplate();
	    List<String> result = restTemplate.getForObject(buildAPIUri()+connString+MQQueueConstants.queueBrowseMessages+queue+"/"+rangeStart+"/"+rangeEnd, List.class);
		return result;
				
	}
	
	public String buildAPIUri() {
		return (http + queueHost + ":" + queuePort + "/"
				+ queueAPI + "/");
	}
	
}
