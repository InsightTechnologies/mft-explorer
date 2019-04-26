package com.mqmft.mq.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@SuppressWarnings("unchecked")
public class MQTopics {
	@Value("${spring.common.protocol}")
	private String http;
	@Value("${spring.mq.host}")
	private String queueHost;
	@Value("${spring.mq.port}")
	private String queuePort;
	@Value("${spring.mq.API}")
	private String queueAPI;
	
	public List<?> getTopicDetails(String connString ,String topic ,String selector) throws IOException {
	    RestTemplate restTemplate = new RestTemplate();
	    List<?> result = restTemplate.getForObject(buildAPIUri()+connString+"/"+MQQueueConstants.topics+"/"+topic+"/"+selector, List.class);
		return result;
	}
	
	public String buildAPIUri() {
		return (http + queueHost + ":" + queuePort + "/"
				+ queueAPI + "/");
	}
}
