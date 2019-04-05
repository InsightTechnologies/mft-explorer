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
import com.miracle.mft.model.MqmftMonitorStatus;
import com.miracle.mft.operation.utils.JobXmlRowMapper;
import com.miracle.mft.operation.utils.MqmftMonitorStatusRowMapper;

@Component
public class MqmftMonitorStatusOperations {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MqmftMonitorStatusRowMapper mqmftMonitorStatusRowMapper;
	private List<MqmftMonitorStatus> mqmftMonitorStatusList;
	String query;

	public ResponseEntity<?> getMoniterStatus() {
		try {
			query = String.format(MftQueryConstants.DeletedMonitorsList);
			mqmftMonitorStatusList = jdbcTemplate.query(query, mqmftMonitorStatusRowMapper);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to get the records", e), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(mqmftMonitorStatusList, HttpStatus.OK);
	}

	public ResponseEntity<?> insertMoniterStatus(MqmftMonitorStatus monitorStatus) {
		try {
			query = String.format(MftQueryConstants.InsertDeletedMonitor, monitorStatus.getHostId(),
					monitorStatus.getMonitorNames());
			jdbcTemplate.execute(query);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to insert the records", e),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Records inserted successfully", HttpStatus.OK);
	}

}
