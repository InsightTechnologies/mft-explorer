package com.miracle.mft.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.miracle.mft.constants.MftQueryConstants;
import com.miracle.mft.exception.MftUserException;
import com.miracle.mft.model.Transfer;
import com.miracle.mft.model.TransferValues;
import com.miracle.mft.operation.utils.TransferRowMapper;
import com.miracle.mft.operation.utils.TransferValueRowMapper;

@Component
public class TransferOperation {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	TransferRowMapper transferRowMapper;
	List<Transfer> transfers;
	String query;

	public ResponseEntity<?> getData() {
		try {
			query = String.format(MftQueryConstants.Get_All_JOBS);
			transfers = jdbcTemplate.query(query, transferRowMapper);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to get the records", e), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(transfers, HttpStatus.OK);
	}
}
