package com.miracle.mft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miracle.mft.model.MqmftHost;
import com.miracle.mft.operations.MqmftHostOperations;

@RestController
@RequestMapping("/mft/dbServices")
public class MqMftHostController {
	@Autowired
	MqmftHostOperations mqmftHostOperations;

	@GetMapping("/getHosts")
	public ResponseEntity<?> getHosts() {
		return new ResponseEntity<>(mqmftHostOperations.getHosts(), HttpStatus.OK);
	}

	@PostMapping("/insertHosts")
	public ResponseEntity<?> insertHosts(@RequestBody MqmftHost mqmftHost) {
		return new ResponseEntity<>(mqmftHostOperations.insert(mqmftHost), HttpStatus.OK);
	}

	@PutMapping("/updateHosts")
	public ResponseEntity<?> updateHosts(@RequestBody MqmftHost mqmftHost) {
		return new ResponseEntity<>(mqmftHostOperations.update(mqmftHost), HttpStatus.OK);
	}

	@DeleteMapping("/deleteHosts")
	public ResponseEntity<?> deleteHosts(@RequestParam(value = "Host_id") int hostid) {
		return new ResponseEntity<>(mqmftHostOperations.delete(hostid), HttpStatus.OK);
	}
}
