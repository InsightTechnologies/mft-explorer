package com.mft.rest.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mft.rest.api.utility.MFTCommonUtility;
import com.mft.rest.api.utility.MonitorCreationUtil;
import com.mft.rest.api.utility.TransfersDeletionUtil;
import com.mft.rest.beans.Transfer_Details;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class MftAPIController {
	public static final Logger logger = LoggerFactory.getLogger(MftAPIController.class);
	@Autowired
	MonitorCreationUtil monitorCreationUtil;
	@Autowired
	TransfersDeletionUtil transfersDeletionUtil;
	
	@PostMapping(value = "/getTransferXML")
	public ResponseEntity<String> getTransferXML(@RequestBody Transfer_Details transferDetails) throws ParserConfigurationException, IOException, TransformerException {
		return new ResponseEntity<String>(monitorCreationUtil.getTransferXML(transferDetails), MFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/deleteMonitor/{MonitorName}/{HostName}/{User}")
	public ResponseEntity<String> deleteMonitor(@PathVariable("MonitorName") String monitorName ,@PathVariable("HostName") String hostName, @PathVariable("User") String user){
		return new ResponseEntity<String>(transfersDeletionUtil.deleteMonitor(monitorName,hostName,user),  MFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/deleteScheduler/{ScheduleId}/{HostName}/{User}")
	public ResponseEntity<String> deleteScheduler(@PathVariable("ScheduleId") String ScheduleID ,@PathVariable("HostName") String hostName, @PathVariable("User") String user){
		return new ResponseEntity<String>(transfersDeletionUtil.deleteScheduler(ScheduleID,hostName,user),  MFTCommonUtility.getHttpResponseHeader(), HttpStatus.OK);
	}
}
