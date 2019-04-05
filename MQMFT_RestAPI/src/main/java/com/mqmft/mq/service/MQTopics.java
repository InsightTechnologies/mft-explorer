package com.mqmft.mq.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.client.RestTemplate;

public class MQTopics {
public static final String apiUri = MQQueueConstants.http+MQQueueConstants.queueHost+":"+MQQueueConstants.queuePort+"/"+MQQueueConstants.queueAPI+"/";
	
	public static List<?> getTopicDetails(String connString ,String topic ,String selector) throws IOException {
	    RestTemplate restTemplate = new RestTemplate();
	    List<?> result = restTemplate.getForObject(apiUri+connString+"/"+MQQueueConstants.topics+"/"+topic+"/"+selector, List.class);
		return result;
	}
}
