package com.miracle.mft.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.miracle.mft.constants.MftQueryConstants;
import com.miracle.mft.exception.MftUserException;
import com.miracle.mft.model.TransferValues;
import com.miracle.mft.operation.utils.TransferValueRowMapper;

@Component
public class TransferValueOperation {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	TransferValueRowMapper transferValueRowMapper;
	List<TransferValues> transferValues;
	String query;

	public ResponseEntity<?> getData(String transferid) {
		try {
			query = String.format(MftQueryConstants.getTransferValues, transferid);
			transferValues = jdbcTemplate.query(query, transferValueRowMapper);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to get the records", e), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(transferValues, HttpStatus.OK);
	}
}
