package com.miracle.mft.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.miracle.mft.constants.MftQueryConstants;
import com.miracle.mft.exception.MftUserException;
import com.miracle.mft.model.MqmftUserExit;
import com.miracle.mft.operation.utils.MqmftUserExitRowMapper;
@Component
public class MqmftUserExitOperation {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MqmftUserExitRowMapper mqmftUserExitRowMapper;
	int updateCount = 0;
	private List<MqmftUserExit> userExits;

	String query;

	public ResponseEntity<?> update(int id, String exit_type) {
		try {
			query = String.format(MftQueryConstants.UpdateUserExitTypes, exit_type, id);
			updateCount = jdbcTemplate.update(query);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to update the records", e),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(updateCount + " number of records Updated ", HttpStatus.OK);
	}

	public ResponseEntity<?> getuserExitYpes() {
		try {
			query = String.format(MftQueryConstants.GetUserExitTypes);
			userExits = jdbcTemplate.query(query, mqmftUserExitRowMapper);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to get the records", e), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(userExits, HttpStatus.OK);
	}

	public ResponseEntity<?> insert(String exitType) {
		try {
			query = String.format(MftQueryConstants.InsertUserExitTypes, exitType);
			jdbcTemplate.execute(query);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to Insert the records", e),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Record Has been inserted", HttpStatus.OK);
	}
}
