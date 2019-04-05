package com.miracle.mft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miracle.mft.model.TransferValues;
import com.miracle.mft.operations.MQMftUserExistDetailsOperation;
import com.miracle.mft.operations.TransferFileLogOperations;
import com.miracle.mft.operations.TransferOperation;
import com.miracle.mft.operations.TransferValueOperation;

@RestController
@RequestMapping("/mft/dbServices")
public class MftDBController {
	@Autowired
	TransferValueOperation transferValueOperation;
	@Autowired
	TransferOperation transferOperation;
	@Autowired
	MQMftUserExistDetailsOperation mQMftUserExistDetailsOperation;
	@Autowired
	TransferFileLogOperations transferFileLogOperations;

	@GetMapping("/getTransferValues/{id}")
	public ResponseEntity<?> getTransferValues(@PathVariable(value = "id") String id) {
		return new ResponseEntity<>(transferValueOperation.getData(id), HttpStatus.OK);

	}

	@GetMapping("/getAllJobNames")
	public ResponseEntity<?> getJobNames() {
		return new ResponseEntity<>(transferOperation.getData(), HttpStatus.OK);

	}

	@PutMapping("/UpdateDeleteUserExistValue")
	public ResponseEntity<?> updateDeleteUserExitValue(@RequestParam(value = "id") int id) {
		return new ResponseEntity<>(mQMftUserExistDetailsOperation.update(id), HttpStatus.OK);

	}

	@PostMapping("/InsertUserExistValue")
	public ResponseEntity<?> InsertUserExitValue(@RequestParam(value = "id") int id,
			@RequestParam(value = "UserExitValue") String userExitValue,
			@RequestParam(value = "params") String params) {
		return new ResponseEntity<>(mQMftUserExistDetailsOperation.insert(id, userExitValue, params), HttpStatus.OK);

	}

	@PutMapping("/UpdateUserExistValues")
	public ResponseEntity<?> updateUserExitValue(@RequestParam(value = "exit_Id") int exit_id,
			@RequestParam(value = "UserExitValue") String userExitValue,
			@RequestParam(value = "params") String params) {
		return new ResponseEntity<>(mQMftUserExistDetailsOperation.UpdateValues(exit_id, userExitValue, params),
				HttpStatus.OK);

	}

	@GetMapping("/getUserExitValues/{id}")
	public ResponseEntity<?> getUserExitValues(@PathVariable(value = "id") int id) {
		return new ResponseEntity<>(mQMftUserExistDetailsOperation.getValues(id), HttpStatus.OK);

	}

	@GetMapping("/getTransferLogs/{startTime}/{endTime}/{jobName}")
	public ResponseEntity<?> getTransferLogs(@PathVariable(value = "startTime") String startTime,
			@PathVariable(value = "endTime") String endTime, @PathVariable(value = "jobName") String jobName) {
		return new ResponseEntity<>(transferFileLogOperations.getTransferLog(startTime, endTime, jobName), HttpStatus.OK);

	}

}
