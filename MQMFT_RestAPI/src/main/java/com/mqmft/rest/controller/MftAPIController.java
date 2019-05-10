package com.mqmft.rest.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mqmft.db.util.DBServiceConnector;
import com.mqmft.db.util.MqmftMonitorStatus;
import com.mqmft.mft.util.MFTRequests;
import com.mqmft.mft.util.Transfer_Details;
import com.mqmft.mq.StartTransferxml;
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
	@Autowired
	DBServiceConnector dBServiceConnector;
	

	@PostMapping(value = "/createTransfer", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createTransfer(@RequestBody Transfer_Details createTransfer) {
		String response = null;
		String jsonResponse;
		try {
			response = mftRequests.createMonitorXML(createTransfer);
			// put the generated xml in Queue Code
			response = mqQueues.putMessage(response,
					createTransfer.getSrc_HostIP() + ":" + createTransfer.getSrc_Channel() + ":"
							+ createTransfer.getSrc_Port(),
					"SYSTEM.FTE.COMMAND." + createTransfer.getSrc_Agent(), createTransfer.getSrc_QueMgr());
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (Exception ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}

	@PutMapping(value = "/startMonitor/{monitorId}/{hostId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> startTransfer(@PathVariable("monitorId")String monitorId,@PathVariable("hostId") int hostId,@RequestBody StartTransferxml startTransferxml) {
		String response = null;
		String jsonResponse;
		try {
			MqmftMonitorStatus insertMonitors=new MqmftMonitorStatus();
			insertMonitors.setHostId(hostId);
			insertMonitors.setMonitorNames(monitorId);
			dBServiceConnector.insertDeletedMonitorStatus(insertMonitors);
			response = mqQueues.putMessage(startTransferxml.getXml(), startTransferxml.getConnString(), "SYSTEM.FTE.COMMAND." + startTransferxml.getAgent(), startTransferxml.getQueueManagerName());
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (Exception ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}

	@PutMapping(value = "/deleteScheduler", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteScheduler(@RequestBody Transfer_Details createTransfer) {
		String response = null;
		String jsonResponse;
		try {
			response = mftRequests.deleteScheduler(createTransfer.getMonitor_ID(), createTransfer.getSrc_HostName(), createTransfer.getSrc_UserID());
			// put the generated xml in Queue Code {id}/{host}/{user}/{agent}/{queueManager}/{connString}
			response = mqQueues.putMessage(response,
					createTransfer.getSrc_HostIP() + ":" + createTransfer.getSrc_Channel() + ":"
							+ createTransfer.getSrc_Port(),
					"SYSTEM.FTE.COMMAND." + createTransfer.getSrc_Agent(), createTransfer.getSrc_QueMgr());
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (Exception ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}

	@PutMapping(value = "/deleteMonitor", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteMonitor(@RequestBody MqmftMonitorStatus insertMonitors) {
		JSONObject response = null;
		String jsonResponse;
		try {
			response = dBServiceConnector.insertDeletedMonitorStatus(insertMonitors);
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (Exception ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}

	@PutMapping(value = "/stopMonitor", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> stopMonitor(@RequestBody Transfer_Details createTransfer) {
		String response = null;
		String jsonResponse;
		try {
			response = mftRequests.deleteMonitor(createTransfer.getMonitor_Name(), createTransfer.getSrc_HostName(), createTransfer.getSrc_UserID());
			// put the generated xml in Queue Code
			response = mqQueues.putMessage(response,
					createTransfer.getSrc_HostIP() + ":" + createTransfer.getSrc_Channel() + ":"
							+ createTransfer.getSrc_Port(),
					"SYSTEM.FTE.COMMAND." + createTransfer.getSrc_Agent(), createTransfer.getSrc_QueMgr());
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (Exception ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/editMonitor/{monitorId}/{hostId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> editMonitor(@RequestBody Transfer_Details createTransfer,@PathVariable("monitorId") String monitorId,
			@PathVariable("hostId") int hostId) {
		String response = null;
		String jsonResponse;
		try {
			MqmftMonitorStatus insertMonitors=new MqmftMonitorStatus();
			insertMonitors.setHostId(hostId);
			insertMonitors.setMonitorNames(monitorId);
			response = mftRequests.deleteMonitor(createTransfer.getMonitor_Name(), createTransfer.getSrc_HostName(), createTransfer.getSrc_UserID());
			// put the generated xml in Queue Code
			response = mqQueues.putMessage(response,
					createTransfer.getSrc_HostIP() + ":" + createTransfer.getSrc_Channel() + ":"
							+ createTransfer.getSrc_Port(),
					"SYSTEM.FTE.COMMAND." + createTransfer.getSrc_Agent(), createTransfer.getSrc_QueMgr());
			dBServiceConnector.insertDeletedMonitorStatus(insertMonitors);
			response = mftRequests.createMonitorXML(createTransfer);
			// put the generated xml in Queue Code
			response = mqQueues.putMessage(response,
					createTransfer.getSrc_HostIP() + ":" + createTransfer.getSrc_Channel() + ":"
							+ createTransfer.getSrc_Port(),
					"SYSTEM.FTE.COMMAND." + createTransfer.getSrc_Agent(), createTransfer.getSrc_QueMgr());
			jsonResponse = mqMFTCommonUtility.getJsonResponse(response, "OK");
		} catch (Exception ioExcep) {
			jsonResponse = mqMFTCommonUtility.getJsonResponse(null, ioExcep.getMessage().toString().trim());
			logger.error("IOException:" + ioExcep.getMessage().toString().trim());
		}

		return new ResponseEntity<String>(jsonResponse, MQMFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
}
