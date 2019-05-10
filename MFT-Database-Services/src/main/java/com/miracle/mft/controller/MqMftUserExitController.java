package com.miracle.mft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miracle.mft.operation.utils.UserExitDetailsRowMapper;
import com.miracle.mft.operations.JobXmlOperations;
import com.miracle.mft.operations.MqmftHostOperations;
import com.miracle.mft.operations.MqmftUserExitOperation;
import com.miracle.mft.operations.OriginalXMLRequestOperations;
import com.miracle.mft.operations.QueueManagerOperations;
import com.miracle.mft.operations.UserExitDetailsOperation;

@RestController
@RequestMapping("/mft/dbServices")
public class MqMftUserExitController {
	@Autowired
	MqmftUserExitOperation mqmftUserExitOperation;
	@Autowired
	UserExitDetailsOperation userExitDetailsOperation;
	@Autowired
	QueueManagerOperations queueManagerOperations;

	@Autowired
	OriginalXMLRequestOperations OriginalXMLRequestOperations;
	@Autowired
	JobXmlOperations jobXmlOperations;

	@PutMapping("/updateUserExist")
	public ResponseEntity<?> updateUserExitValue(@RequestParam(value = "id") int id,
			@RequestParam(value = "id") String exitType) {
		return new ResponseEntity<>(mqmftUserExitOperation.update(id, exitType), HttpStatus.OK);

	}

	@PostMapping("/insertUserExist")
	public ResponseEntity<?> insertExitValue(@RequestParam(value = "id") String exitType) {
		return new ResponseEntity<>(mqmftUserExitOperation.insert(exitType), HttpStatus.OK);

	}

	@GetMapping("/getUserExitTypes")
	public ResponseEntity<?> getUserExitTypes() {
		return new ResponseEntity<>(mqmftUserExitOperation.getuserExitYpes(), HttpStatus.OK);

	}

	@GetMapping("/getUserExitDetails")
	public ResponseEntity<?> getExitDetails() {
		return new ResponseEntity<>(userExitDetailsOperation.getValues(), HttpStatus.OK);

	}

	@PostMapping("/getOriginalXml")
	public ResponseEntity<?> getOriginalXml(@RequestParam(value = "id") String id) {
		return new ResponseEntity<>(OriginalXMLRequestOperations.getOriginalXml(id), HttpStatus.OK);
	}

	@PostMapping("/getjobXml")
	public ResponseEntity<?> getJobXml(@RequestParam(value = "JobXMl") String jobXml) {
		return new ResponseEntity<>(jobXmlOperations.getOriginalXml(jobXml), HttpStatus.OK);
	}

}
