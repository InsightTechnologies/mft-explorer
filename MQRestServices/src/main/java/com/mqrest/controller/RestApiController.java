package com.mqrest.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.mqrest.MQService.LocalQueues;
import com.mqrest.MQService.MQServices;
import com.mqrest.pcf.PCFLocalQueues;
import com.mqrest.topic.MQTopicUtility;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableSwagger2
@RequestMapping("/{connectionString}")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	MQServices mqService; // Service which will do all data retrieval/manipulation work
	
	@Autowired
	PCFLocalQueues pcfLocalQueues;
	
	@Autowired
	MQTopicUtility mqTopicUtils;
	
	@GetMapping(value = "/queue/allLocal")
	public ResponseEntity<List<LocalQueues>> listLocalQueues(@PathVariable("connectionString") String connString)
			throws MQException, IOException {
		List localQueues = mqService.listLocalQueues(connString);
		if (localQueues.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<LocalQueues>>(localQueues, HttpStatus.OK);
	}

	@GetMapping(value = "/queue/browseMessages/{qName}/{startRange}/{endRange}")
	ResponseEntity<List<String>> browseMessages(@PathVariable("qName") String queueName,
			@PathVariable("connectionString") String connString, @PathVariable("startRange") int rangeStart,
			@PathVariable("endRange") int rangeEnd) throws MQException, IOException, JMSException {
		List browsedMsgs = mqService.browseMessages(queueName, connString, rangeStart, rangeEnd);
		return new ResponseEntity<List<String>>(browsedMsgs, HttpStatus.OK);
	}

	@PutMapping(value = "/queue/put/{queueName}")
	public ResponseEntity<?> putMessages(@RequestBody String msg, @PathVariable("connectionString") String connString,
			@PathVariable("queueName") String queueName) {
		String messages = mqService.putMessage(msg, connString, queueName);
		return new ResponseEntity<String>(messages, HttpStatus.OK);
	}

	@PutMapping(value = "/queue/puth/{queueName}")
	public ResponseEntity<?> putHeaderMessage(@RequestHeader Map header, @RequestBody String msg,
			@PathVariable("connectionString") String connString, @PathVariable("queueName") String queueName)
			throws DOMException, ParserConfigurationException, SAXException, IOException {
		String messages = mqService.putHeaderMessage(header, msg, connString, queueName);
		return new ResponseEntity<String>(messages, HttpStatus.OK);
	}

	@GetMapping(value = "/queue/properties/{queueName}")
	public ResponseEntity<Map<String, Object>> getQProperties(@PathVariable("connectionString") String connString,
			@PathVariable("queueName") String queueName) throws PCFException, MQException, IOException {
		Map properties = mqService.getQProperties(connString, queueName);
		return new ResponseEntity<Map<String, Object>>(properties, HttpStatus.OK);
	}

	@GetMapping(value = "/queue/local")
	public ResponseEntity<List<LocalQueues>> listLocalQueues2(@PathVariable("connectionString") String connString)
			throws MQException, IOException {
		List localQueues = pcfLocalQueues.getLocalQueues(connString, "normal");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");

		if (localQueues.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<LocalQueues>>(localQueues, responseHeaders, HttpStatus.OK);

	}

	@GetMapping(value = "/queue/system")
	public ResponseEntity<List<LocalQueues>> systemQueues(@PathVariable("connectionString") String connString)
			throws MQException, IOException {
		List sysQueues = pcfLocalQueues.getLocalQueues(connString, "system");
		if (sysQueues.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<LocalQueues>>(sysQueues, HttpStatus.OK);
	}

	@GetMapping(value = "/topic/{topicname}/{selector}")
	public ResponseEntity<List> getTopicMessages( @PathVariable("connectionString") String connString,@PathVariable("topicname") String topicname,@PathVariable("selector") String selector) throws MQException, IOException, Exception {
	return new ResponseEntity<List>(mqTopicUtils.topicConsumer(topicname,selector, connString), HttpStatus.OK);
	}
}
