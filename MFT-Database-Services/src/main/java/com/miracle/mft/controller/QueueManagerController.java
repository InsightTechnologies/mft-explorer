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

import com.miracle.mft.model.QueueManager;
import com.miracle.mft.operations.QueueManagerOperations;

@RestController
@RequestMapping("/mft/dbServices")
public class QueueManagerController {

	@Autowired
	QueueManagerOperations queueManagerOperations;

	@GetMapping("/getQueueManagers")
	public ResponseEntity<?> getQueueManagers() {
		return new ResponseEntity<>(queueManagerOperations.getQueueManagers(), HttpStatus.OK);
	}

	@PostMapping("/insertQueueManager")
	public ResponseEntity<?> insertQueueManagers(@RequestBody QueueManager queueManager) {
		return new ResponseEntity<>(queueManagerOperations.insertQueueManagers(queueManager), HttpStatus.OK);
	}

	@PutMapping("/updateQueueManager")
	public ResponseEntity<?> updateQueueManagers(@RequestBody QueueManager queueManager) {
		return new ResponseEntity<>(queueManagerOperations.updateQueueManagers(queueManager), HttpStatus.OK);
	}

	@DeleteMapping("/deleteQueueManager")
	public ResponseEntity<?> deleteQueueManagers(@RequestParam(value = "QM_ID") int qmId) {
		return new ResponseEntity<>(queueManagerOperations.deleteQueueManager(qmId), HttpStatus.OK);
	}
}
