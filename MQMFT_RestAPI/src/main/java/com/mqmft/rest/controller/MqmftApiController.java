package com.mqmft.rest.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.ibm.mq.MQException;
import com.ibm.mq.pcf.PCFException;
import com.mqmft.mq.service.MQQueues;
import com.mqmft.mq.service.MQTopics;
import com.mqmft.rest.api.utility.MQMFTCommonUtility;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class MqmftApiController {

	public static final Logger logger = LoggerFactory.getLogger(MqmftApiController.class);

	@RequestMapping(value = "/queues/allLocal/{connectionString}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllLocalQueues(@PathVariable("connectionString") String connString) {
		List response = null;
		String jsonResponse;
		try {
			response = new MQQueues().listLocalQueues(connString);
			jsonResponse = new MQMFTCommonUtility().getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = new MQMFTCommonUtility().getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/queues/browseMessages/{queueName}/{startRange}/{endRange}/{connectionString}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> browseMessages(@PathVariable("connectionString") String connString,
			@PathVariable("queueName") String queue,@PathVariable("startRange") int rangeStart,
			@PathVariable("endRange") int rangeEnd) throws IOException {
		String jsonResponse;
		List response = null;
		try {
			response = new MQQueues().browseMessages(connString, queue, rangeStart, rangeEnd);
			jsonResponse = new MQMFTCommonUtility().getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = new MQMFTCommonUtility().getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}
		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/queues/put/{queueName}/{connectionString}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> putMessage(@RequestBody String msg, @PathVariable("connectionString") String connString,
			@PathVariable("queueName") String queueName) throws IOException {
		String jsonResponse;
		String response ;
		try {
			response = new MQQueues().putMessage(msg, connString, queueName);
			jsonResponse = new MQMFTCommonUtility().getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = new MQMFTCommonUtility().getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}
		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
		
	}

	@RequestMapping(value = "/queues/puth/{queueName}/{connectionString}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> putHeaderMessage(@RequestHeader Map header, @RequestBody String msg,
			@PathVariable("connectionString") String connString, @PathVariable("queueName") String queueName)
			throws DOMException, ParserConfigurationException, SAXException, IOException {
		String jsonResponse;
		String  response ;
		try {
			response = new MQQueues().putHeaderMessage( header, msg, connString, queueName);
			jsonResponse = new MQMFTCommonUtility().getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = new MQMFTCommonUtility().getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}
		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
		
	}

	@RequestMapping(value = "/queues/properties/{queueName}/{connectionString}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getQProperties(@PathVariable("connectionString") String connString,
			@PathVariable("queueName") String queueName) throws PCFException, MQException, IOException {
		String jsonResponse;
		Map<String, Object> response = null;
		try {
			response = new MQQueues().getQProperties(connString, queueName);
			jsonResponse = new MQMFTCommonUtility().getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = new MQMFTCommonUtility().getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}
		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	
	}

	@RequestMapping(value = "/queues/local/{connectionString}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getLocalQueues(@PathVariable("connectionString") String connString)
			throws MQException, IOException {
		String jsonResponse;
		List response = null;
		try {
			response = new MQQueues().getLocalQueues(connString);
			jsonResponse = new MQMFTCommonUtility().getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = new MQMFTCommonUtility().getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}
		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);

	}

	@RequestMapping(value = "/queues/system/{connectionString}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getSystemQueues(@PathVariable("connectionString") String connString)
			throws MQException, IOException {
		String jsonResponse;
		List response = null;
		try {
			response = new MQQueues().getSystemQueues(connString);
			jsonResponse = new MQMFTCommonUtility().getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = new MQMFTCommonUtility().getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}
		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/topic/{topicname}/{selector}/{connectionString}", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getTopicMessages( @PathVariable("connectionString") String connString,@PathVariable("topicname") String topicname,@PathVariable("selector") String selector) throws MQException, IOException, Exception {
		String jsonResponse;
		List response = null;
		try {
			response = new MQTopics().getTopicDetails(connString, topicname,selector );
			jsonResponse = new MQMFTCommonUtility().getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = new MQMFTCommonUtility().getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}
		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	
	}
}
