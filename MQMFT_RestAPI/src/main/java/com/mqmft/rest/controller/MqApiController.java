package com.mqmft.rest.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.ibm.mq.MQException;
import com.ibm.mq.pcf.PCFException;
import com.mqmft.db.util.DBServiceConnector;
import com.mqmft.mq.service.MQQueues;
import com.mqmft.mq.service.MQTopics;
import com.mqmft.rest.api.utility.MQMFTCommonUtility;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class MqApiController {
	@Autowired
	MQQueues mqQueues;
	
	@Autowired
	MQMFTCommonUtility mqMFTCommonUtility;
	
	@Autowired
	MQTopics mqTopics;
	
	public static final Logger logger = LoggerFactory.getLogger(MqApiController.class);

	@GetMapping(value = "/queues/allLocal/{connectionString}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllLocalQueues(@PathVariable("connectionString") String connString) {
		List response = null;
		String jsonResponse;
		try {
			response = mqQueues.listLocalQueues(connString);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}

	@GetMapping(value = "/queues/browseMessages/{queueName}/{startRange}/{endRange}/{connectionString}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> browseMessages(@PathVariable("connectionString") String connString,
			@PathVariable("queueName") String queue,@PathVariable("startRange") int rangeStart,
			@PathVariable("endRange") int rangeEnd) throws IOException {
		String jsonResponse;
		List response = null;
		try {
			response = mqQueues.browseMessages(connString, queue, rangeStart, rangeEnd);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}
		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}

	@PutMapping(value = "/queues/put/{queueName}/{connectionString}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> putMessage(@RequestBody String msg, @PathVariable("connectionString") String connString,
			@PathVariable("queueName") String queueName) throws IOException {
		String jsonResponse;
		String response ;
		try {
			response = mqQueues.put(msg, connString, queueName);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}
		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
		
	}

	@PutMapping(value = "/queues/puth/{queueName}/{connectionString}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> putHeaderMessage(@RequestHeader Map header, @RequestBody String msg,
			@PathVariable("connectionString") String connString, @PathVariable("queueName") String queueName)
			throws DOMException, ParserConfigurationException, SAXException, IOException {
		String jsonResponse;
		String  response ;
		try {
			response = mqQueues.putHeaderMessage( header, msg, connString, queueName);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}
		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
		
	}

	@GetMapping(value = "/queues/properties/{queueName}/{connectionString}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getQProperties(@PathVariable("connectionString") String connString,
			@PathVariable("queueName") String queueName) throws PCFException, MQException, IOException {
		String jsonResponse;
		Map<String, Object> response = null;
		try {
			response = mqQueues.getQProperties(connString, queueName);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}
		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	
	}

	@GetMapping(value = "/queues/local/{connectionString}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getLocalQueues(@PathVariable("connectionString") String connString)
			throws MQException, IOException {
		String jsonResponse;
		List response = null;
		try {
			response = mqQueues.getLocalQueues(connString);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}
		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);

	}

	@GetMapping(value = "/queues/system/{connectionString}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getSystemQueues(@PathVariable("connectionString") String connString)
			throws MQException, IOException {
		String jsonResponse;
		List response = null;
		try {
			response = mqQueues.getSystemQueues(connString);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}
		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/topic/{topicname}/{selector}/{connectionString}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getTopicMessages( @PathVariable("connectionString") String connString,@PathVariable("topicname") String topicname,@PathVariable("selector") String selector) throws MQException, IOException, Exception {
		String jsonResponse;
		List response = null;
		try {
			response = mqTopics.getTopicDetails(connString, topicname,selector );
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}
		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	
	}
	
}
