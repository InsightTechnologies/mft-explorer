package com.miracle.mft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miracle.mft.model.MqmftHost;
import com.miracle.mft.model.MqmftMonitorStatus;
import com.miracle.mft.operations.MqmftMonitorStatusOperations;

@RestController
@RequestMapping("/mft/dbServices")
public class MqmftMonitorStatusController {

	@Autowired
	MqmftMonitorStatusOperations monitorStatusOperations;

	@GetMapping("/getDeletedMonitorStatus")
	public ResponseEntity<?> getMonitorStatus() {
		return new ResponseEntity<>(monitorStatusOperations.getMonitorStatus(), HttpStatus.OK);
	}
	
	@GetMapping("/getMonitors")
	public ResponseEntity<?> getMonitors() {
		return new ResponseEntity<>(monitorStatusOperations.getMonitors(), HttpStatus.OK);
	}

	@PostMapping("/insertDeletedMonitorStatus")
	public ResponseEntity<?> insertHosts(@RequestBody MqmftMonitorStatus monitorStatus) {
		return new ResponseEntity<>(monitorStatusOperations.insertMoniterStatus(monitorStatus), HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteMonitors")
	public ResponseEntity<?> deleteMonitors(@RequestParam(value = "Host_id") int hostid,@RequestParam(value = "monitor") String monitor) {
		return new ResponseEntity<>(monitorStatusOperations.delete(hostid,monitor), HttpStatus.OK);
	}
}
