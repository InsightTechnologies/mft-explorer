package com.mqmft.rest.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.mqmft.db.util.DBServiceConnector;
import com.mqmft.db.util.InsertHost;
import com.mqmft.db.util.InsertQMs;
import com.mqmft.db.util.MonitorStatus;
import com.mqmft.rest.api.utility.MQMFTCommonUtility;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")

public class DBAPIController {
	@Autowired
	DBServiceConnector dBServiceConnector;
	@Autowired
	MQMFTCommonUtility mqMFTCommonUtility;
	
	public static final Logger logger = LoggerFactory.getLogger(MqApiController.class);
	
	@GetMapping(value = "/queueManagers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getQueueManagers() {
		String response = null;
		String jsonResponse;
		try {
			response = dBServiceConnector.getQueueManagers();
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@PutMapping(value = "/queueManagers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateQueueManagers(@RequestBody InsertQMs updateString) {
		String response = null;
		String jsonResponse;
		try {
			response = dBServiceConnector.updateQueueManagers(updateString);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/queueManagers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteQueueManagers(@PathVariable("id") String qmgrId) {
		String response = null;
		String jsonResponse;
		try {
			response = dBServiceConnector.deleteQueueManagers(qmgrId);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/queueManagers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> insertQueueManagers(@RequestBody InsertQMs insertQM) throws RestClientException, URISyntaxException {
		JSONObject response = null;
		String jsonResponse;
		try {
			System.out.println(insertQM);
			response = dBServiceConnector.insertQueueManagers(insertQM);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/hosts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getHosts() {
		String response = null;
		String jsonResponse;
		try {
			response = dBServiceConnector.getHosts();
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/hosts",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> insertHosts(@RequestBody InsertHost insertHost) throws RestClientException, URISyntaxException {
		JSONObject response = null;
		String jsonResponse;
		try {
			System.out.println(insertHost);
			response = dBServiceConnector.insertHost(insertHost);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@PutMapping(value = "/hosts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateHosts(@RequestBody InsertHost updateString) {
		String response = null;
		String jsonResponse;
		try {
			response = dBServiceConnector.updateHosts(updateString);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	

	@DeleteMapping(value = "/hosts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteHosts(@PathVariable("id") String hostId) {
		String response = null;
		String jsonResponse;
		try {
			response = dBServiceConnector.deleteHosts(hostId);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/transfers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllJobNames() {
		String response = null;
		String jsonResponse;
		try {
			response = dBServiceConnector.getAllJobNames();
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/transfers/{startTime}/{endTime}/{jobName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getTransferLogs(@PathVariable("startTime") String startTime,@PathVariable("endTime") String endTime,
			@PathVariable("jobName") String jobName) {
		String response = null;
		String jsonResponse;
		try {
			response = dBServiceConnector.getTransferLogs(startTime,endTime,jobName);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/transfers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getTransferValues(@PathVariable("id") String id) {
		String response = null;
		String jsonResponse;
		try {
			response = dBServiceConnector.getTransferValues(id);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}


	@GetMapping(value = "/monitorStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getDeletedMonitorStatus() {
		String response = null;
		String jsonResponse;
		try {
			response = dBServiceConnector.getDeletedMonitorStatus();
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/monitors", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getMonitors() {
		String response = null;
		String jsonResponse;
		try {
			response = dBServiceConnector.getMonitors();
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/monitorStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> insertDeletedMonitorStatus(@RequestBody MonitorStatus monitorStatus) throws RestClientException, URISyntaxException {
		JSONObject response = null;
		String jsonResponse;
		try {
			System.out.println(monitorStatus);
			response = dBServiceConnector.insertDeletedMonitorStatus(monitorStatus);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (IOException ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
}
