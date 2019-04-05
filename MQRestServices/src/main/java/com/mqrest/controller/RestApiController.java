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
import com.mqrest.MQService.LocalQueues;
import com.mqrest.MQService.MQServices;
import com.mqrest.pcf.PCFLocalQueues;
import com.mqrest.topic.MQTopicUtility;

@RestController
@RequestMapping("/{connectionString}")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	MQServices mqService; // Service which will do all data retrieval/manipulation work
	
	@RequestMapping(value = "/queue/allLocal", method = RequestMethod.GET)
	public ResponseEntity<List<String>> listLocalQueues(@PathVariable("connectionString") String connString)
			throws MQException, IOException {
		List localQueues = new MQServices().listLocalQueues(connString);
		if (localQueues.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<String>>(localQueues, HttpStatus.OK);
	}

	@RequestMapping(value = "/queue/browseMessages/{qName}/{startRange}/{endRange}", method = RequestMethod.GET)
	ResponseEntity<List<String>> browseMessages(@PathVariable("qName") String queueName,
			@PathVariable("connectionString") String connString, @PathVariable("startRange") int rangeStart,
			@PathVariable("endRange") int rangeEnd) throws MQException, IOException, JMSException {
		List browsedMsgs = new MQServices().browseMessages(queueName, connString, rangeStart, rangeEnd);
		return new ResponseEntity<List<String>>(browsedMsgs, HttpStatus.OK);
	}

	@RequestMapping(value = "/queue/put/{queueName}", method = RequestMethod.PUT)
	public ResponseEntity<?> putMessages(@RequestBody String msg, @PathVariable("connectionString") String connString,
			@PathVariable("queueName") String queueName) {
		String messages = new MQServices().putMessage(msg, connString, queueName);
		return new ResponseEntity<String>(messages, HttpStatus.OK);
	}

	@RequestMapping(value = "/queue/puth/{queueName}", method = RequestMethod.PUT)
	public ResponseEntity<?> putHeaderMessage(@RequestHeader Map header, @RequestBody String msg,
			@PathVariable("connectionString") String connString, @PathVariable("queueName") String queueName)
			throws DOMException, ParserConfigurationException, SAXException, IOException {
		String messages = new MQServices().putHeaderMessage(header, msg, connString, queueName);
		return new ResponseEntity<String>(messages, HttpStatus.OK);
	}

	@RequestMapping(value = "/queue/properties/{queueName}", method = RequestMethod.GET)
	public ResponseEntity<?> getQProperties(@PathVariable("connectionString") String connString,
			@PathVariable("queueName") String queueName) throws PCFException, MQException, IOException {
		Map properties = new MQServices().getQProperties(connString, queueName);
		return new ResponseEntity<Map<String, Object>>(properties, HttpStatus.OK);
	}

	@RequestMapping(value = "/queue/local", method = RequestMethod.GET)
	public ResponseEntity<List<LocalQueues>> listLocalQueues2(@PathVariable("connectionString") String connString)
			throws MQException, IOException {
		List localQueues = new PCFLocalQueues().getLocalQueues(connString, "normal");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");

		if (localQueues.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<LocalQueues>>(localQueues, responseHeaders, HttpStatus.OK);

	}

	@RequestMapping(value = "/queue/system", method = RequestMethod.GET)
	public ResponseEntity<List<LocalQueues>> systemQueues(@PathVariable("connectionString") String connString)
			throws MQException, IOException {
		List sysQueues = new PCFLocalQueues().getLocalQueues(connString, "system");
		if (sysQueues.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<LocalQueues>>(sysQueues, HttpStatus.OK);
	}

	@RequestMapping(value = "/topic/{topicname}/{selector}", method = RequestMethod.GET)
	public ResponseEntity<List> getTopicMessages( @PathVariable("connectionString") String connString,@PathVariable("topicname") String topicname,@PathVariable("selector") String selector) throws MQException, IOException, Exception {

	List<String> jsonList=new MQTopicUtility().topicConsumer(topicname,selector, connString);
	return new ResponseEntity<List>(jsonList, HttpStatus.OK);
	}
}
