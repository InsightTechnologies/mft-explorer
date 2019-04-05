package com.miracle.mft.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.miracle.mft.constants.MftQueryConstants;
import com.miracle.mft.exception.MftUserException;
import com.miracle.mft.model.TransferLog;
import com.miracle.mft.operation.utils.TransferLogRowMapper;

@Component
public class TransferFileLogOperations {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private TransferLogRowMapper transferLogRowMapper;

	String query;
	private List<TransferLog> transferLogs;

	public ResponseEntity<?> getTransferLog(String startTime, String endTime, String jobName) {

		try {
			query = String.format(MftQueryConstants.Transfer_Log, startTime, endTime, jobName);
			transferLogs = jdbcTemplate.query(query, transferLogRowMapper);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			return new ResponseEntity<>(new MftUserException("failed to get the records", e), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(transferLogs, HttpStatus.OK);
	}

}
