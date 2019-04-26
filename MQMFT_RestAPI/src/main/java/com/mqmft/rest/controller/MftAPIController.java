package com.mqmft.rest.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

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

import com.mqmft.mft.util.CreateTransfer;
import com.mqmft.mft.util.MFTRequests;
import com.mqmft.mft.util.Transfer_Details;
import com.mqmft.mq.service.MQQueues;
import com.mqmft.rest.api.utility.MQMFTCommonUtility;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class MftAPIController {
	public static final Logger logger = LoggerFactory.getLogger(MqApiController.class);
	@Autowired
	MQMFTCommonUtility mqMFTCommonUtility;
	@Autowired
	MFTRequests mftRequests;
	@Autowired
	MQQueues mqQueues;
	@PostMapping(value = "/createTransfer", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createTransfer(@RequestBody Transfer_Details createTransfer) {
		String response = null;
		String jsonResponse;
		try {
			response = mftRequests.createMonitorXML(createTransfer);
			//put the generated xml in Queue Code 
			response = mqQueues.putMessage(response, createTransfer.getSrc_HostIP()+":"+createTransfer.getSrc_Channel()+":"+createTransfer.getSrc_Port(), "SYSTEM.FTE.COMMAND."+createTransfer.getSrc_Agent());
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (Exception ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@PutMapping(value = "/createTransfer/{agent}/{xml}/{connString}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> startTransfer(@PathVariable("connString") String connString,@PathVariable("xml") String startTransferxml,@PathVariable("agent") String agent) {
		String response = null;
		String jsonResponse;
		try {
			response = mqQueues.putMessage(startTransferxml, connString, "SYSTEM.FTE.COMMAND."+agent);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (Exception ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deleteScheduler/{id}/{host}/{user}/{agent}/{connString}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteScheduler(@PathVariable("id") String scheduleId,@PathVariable("host") String host,@PathVariable("user") String user,@PathVariable("agent") String agent,@PathVariable("connString") String conn) {
		String response = null;
		String jsonResponse;
		try {
			response = mftRequests.deleteScheduler(scheduleId,host,user);
			//put the generated xml in Queue Code 
			response = mqQueues.putMessage(response, conn, "SYSTEM.FTE.COMMAND."+agent);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (Exception ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deleteMonitor/{monitor}/{host}/{user}/{agent}/{connString}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteMonitor(@PathVariable("monitor") String monitor,@PathVariable("host") String host,@PathVariable("user") String user,@PathVariable("agent") String agent,@PathVariable("connString") String conn) {
		String response = null;
		String jsonResponse;
		try {
			response = mftRequests.deleteMonitor(monitor,host,user);
			//put the generated xml in Queue Code 
			response = mqQueues.putMessage(response, conn, "SYSTEM.FTE.COMMAND."+agent);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (Exception ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@PutMapping(value = "/stopMonitor/{monitor}/{host}/{user}/{agent}/{connString}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> stopMonitor(@PathVariable("monitor") String monitor,@PathVariable("host") String host,@PathVariable("user") String user,@PathVariable("agent") String agent,@PathVariable("connString") String conn) {
		String response = null;
		String jsonResponse;
		try {
			response = mftRequests.deleteMonitor(monitor,host,user);
			//put the generated xml in Queue Code 
			response = mqQueues.putMessage(response, conn, "SYSTEM.FTE.COMMAND."+agent);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (Exception ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
}
