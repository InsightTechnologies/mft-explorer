package com.miracle.mft.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.miracle.mft.constants.MftQueryConstants;
import com.miracle.mft.exception.MftUserException;
import com.miracle.mft.model.OriginalXMLRequest;
import com.miracle.mft.operation.utils.OriginalXMLRequestRowMapper;

@Component
public class OriginalXMLRequestOperations {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private OriginalXMLRequestRowMapper originalXMLRequestRowMapper;
	private List<OriginalXMLRequest> originalXMLRequests;
	String query;

	public ResponseEntity<?> getOriginalXml(String id) {
		try {
			query = String.format(MftQueryConstants.Get_MonitorXML, id);
			originalXMLRequests = jdbcTemplate.query(query, originalXMLRequestRowMapper);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to get the records", e), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(originalXMLRequests, HttpStatus.OK);
	}
}
