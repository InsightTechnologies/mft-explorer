package com.mqmft.mq.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class MQQueues {

	public static final String apiUri = MQQueueConstants.http+MQQueueConstants.queueHost+":"+MQQueueConstants.queuePort+"/"+MQQueueConstants.queueAPI+"/";
	
	public static List<String> listLocalQueues(String connString) throws IOException {
	    RestTemplate restTemplate = new RestTemplate();
	    List<String> result = restTemplate.getForObject(apiUri+connString+"/queue/allLocal", List.class);
		return result;
	}

	public static String putMessage(String msg, String connString, String queueName) throws IOException {
		RestTemplate restTemplate = new RestTemplate();
//		List<String> result = restTemplate.getForObject(apiUri+connString+MQQueueConstants.queuePut+queueName, List.class);
		restTemplate.put(apiUri+connString+MQQueueConstants.queuePut+queueName, msg);
		return "Message Put successfully";
		
	}

	public static String putHeaderMessage(Map<?,?> header, String msg, String connString, String queueName) throws IOException {
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
		restTemplate.put(apiUri+connString+MQQueueConstants.queuePutHeader+queueName, httpEntity);
		
		return "Message With Header was put successfully";
		
	}

	public static Map<String, Object> getQProperties(String connString, String queueName) throws IOException {
		// /queue/connString/properties/queueName
		RestTemplate restTemplate = new RestTemplate();
	    Map<String,Object> result = restTemplate.getForObject(apiUri+connString+MQQueueConstants.queueProperties+queueName, Map.class);
		return result;
	}

	public static List<String> getLocalQueues(String connString) throws IOException {
		// /queue/connString/local
		RestTemplate restTemplate = new RestTemplate();
	    List<String> result = restTemplate.getForObject(apiUri+connString+MQQueueConstants.queueLocal, List.class);
		return result;

	}
	
	public static List<String> getSystemQueues(String connString) throws IOException {
		// /queue/connString/local
		RestTemplate restTemplate = new RestTemplate();
	    List<String> result = restTemplate.getForObject(apiUri+connString+MQQueueConstants.queueSystem, List.class);
		return result;

	}

	public static List<String> browseMessages(String connString, String queue, int rangeStart, int rangeEnd) throws IOException {
		// /queue/connString/browseMessages/queue
		RestTemplate restTemplate = new RestTemplate();
	    List<String> result = restTemplate.getForObject(apiUri+connString+MQQueueConstants.queueBrowseMessages+queue+"/"+rangeStart+"/"+rangeEnd, List.class);
		return result;
				
	}
	
}
