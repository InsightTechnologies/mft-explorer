package com.miracle.mft.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.miracle.mft.constants.MftQueryConstants;
import com.miracle.mft.exception.MftUserException;
import com.miracle.mft.model.MqmftHost;
import com.miracle.mft.model.QueueManager;
import com.miracle.mft.operation.utils.QueueManagerRowMapper;

@Component
public class QueueManagerOperations {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private QueueManagerRowMapper queueManagerRowMapper;
	private List<QueueManager> queueManagers;
	String query;
	int updateCount = 0;

	public ResponseEntity<?> getQueueManagers() {
		try {
			query = String.format(MftQueryConstants.GetQueueManagers);
			queueManagers = jdbcTemplate.query(query, queueManagerRowMapper);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to get the records", e), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(queueManagers, HttpStatus.OK);
	}

	public ResponseEntity<?> insertQueueManagers(QueueManager queueManager) {
		try {
			query = String.format(MftQueryConstants.Insert_QM, queueManager.getQmName(), queueManager.getQmChannel(),
					queueManager.getQmPort(), queueManager.getQmUserid(), queueManager.getIsAgent(),
					queueManager.getIsCmnd(), queueManager.getIsCoord(), queueManager.getIsActive(),
					queueManager.getHostId());
			jdbcTemplate.execute(query);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to insert the records", e),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Records inserted successfully", HttpStatus.OK);
	}

	public ResponseEntity<?> updateQueueManagers(QueueManager queueManager) {
		try {
			query = String.format(MftQueryConstants.Update_QM, queueManager.getQmName(), queueManager.getQmChannel(),
					queueManager.getQmPort(), queueManager.getQmUserid(), queueManager.getIsAgent(),
					queueManager.getIsCmnd(), queueManager.getIsCoord(), queueManager.getIsActive(),
					queueManager.getHostId(), queueManager.getQmid());
			updateCount = jdbcTemplate.update(query);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to update the records", e),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(updateCount + " number of records Updated ", HttpStatus.OK);
	}

	public ResponseEntity<?> deleteQueueManager(int id) {
		try {
			query = String.format(MftQueryConstants.Delete_QM, id);
			jdbcTemplate.execute(query);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to delete the records", e),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Records deleted successfully ", HttpStatus.OK);
	}

}
