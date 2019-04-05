package com.miracle.mft.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.miracle.mft.constants.MftQueryConstants;
import com.miracle.mft.exception.MftUserException;
import com.miracle.mft.model.MqmftUserExitDetail;
import com.miracle.mft.model.Transfer;
import com.miracle.mft.operation.utils.MqmftUserExitDetailRowMapper;
import com.miracle.mft.operation.utils.TransferRowMapper;

@Component
public class MQMftUserExistDetailsOperation {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MqmftUserExitDetailRowMapper mqmftUserExitDetailRowMapper;
	int updateCount = 0;
	private List<MqmftUserExitDetail> userDetails;

	String query;

	public ResponseEntity<?> update(int id) {
		try {
			query = String.format(MftQueryConstants.DeleteUserExitValues, id);
			updateCount = jdbcTemplate.update(query);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to update the records", e),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(updateCount + " number of records Updated ", HttpStatus.OK);
	}

	public ResponseEntity<?> insert(int id, String userExitValue, String params) {
		try {
			query = String.format(MftQueryConstants.InsertUserExitvalues, id, userExitValue, params);
			jdbcTemplate.execute(query);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to Insert the records", e),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Record Has been inserted", HttpStatus.OK);
	}

	public ResponseEntity<?> UpdateValues(int exit_id, String userExitValue, String params) {
		try {
			query = String.format(MftQueryConstants.UpdateUserExitValues, userExitValue, params, exit_id);
			updateCount = jdbcTemplate.update(query);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to update the records", e),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(updateCount + " number of records Updated ", HttpStatus.OK);
	}

	public ResponseEntity<?> getValues(int id) {
		try {
			query = String.format(MftQueryConstants.GetUserExitValues, id);
			userDetails = jdbcTemplate.query(query, mqmftUserExitDetailRowMapper);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to get the records", e), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}
}
