package com.miracle.mft.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.miracle.mft.constants.MftQueryConstants;
import com.miracle.mft.exception.MftUserException;
import com.miracle.mft.model.JobXml;
import com.miracle.mft.operation.utils.JobXmlRowMapper;

@Component
public class JobXmlOperations {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private JobXmlRowMapper jobXmlRowMapper;
	private List<JobXml> jobXmls;
	String query;

	public ResponseEntity<?> getOriginalXml(String jobXml) {
		try {
			query = String.format(MftQueryConstants.Get_JobXML, jobXml);
			jobXmls = jdbcTemplate.query(query, jobXmlRowMapper);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to get the records", e), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(jobXmls, HttpStatus.OK);
	}
}
