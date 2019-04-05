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
import com.miracle.mft.operation.utils.MqmftHostRowMapper;

@Component
public class MqmftHostOperations {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MqmftHostRowMapper mqmftHostRowMapper;
	int updateCount = 0;
	private List<MqmftHost> mqmftHosts;
	String query;

	public ResponseEntity<?> getHosts() {
		try {
			query = String.format(MftQueryConstants.GetHost);
			mqmftHosts = jdbcTemplate.query(query, mqmftHostRowMapper);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to get the records", e), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(mqmftHosts, HttpStatus.OK);
	}

	public ResponseEntity<?> update(MqmftHost mqmftHost) {
		try {
			query = String.format(MftQueryConstants.Update_Host, mqmftHost.getHostName(), mqmftHost.getHostIp(),
					mqmftHost.getCoordQmgr(), mqmftHost.getCoordPort(), mqmftHost.getAgentQmgr(),
					mqmftHost.getAgentPort(), mqmftHost.getDefaultChannel(), mqmftHost.getUserId(),
					mqmftHost.getActive(), mqmftHost.getHostId());
			updateCount = jdbcTemplate.update(query);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to update the records", e),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(updateCount + " number of records Updated ", HttpStatus.OK);
	}

	public ResponseEntity<?> insert(MqmftHost mqmftHost) {
		try {
			query = String.format(MftQueryConstants.Insert_Host, mqmftHost.getHostName(), mqmftHost.getHostIp(),
					mqmftHost.getCoordQmgr(), mqmftHost.getCoordPort(), mqmftHost.getAgentQmgr(),
					mqmftHost.getAgentPort(), mqmftHost.getDefaultChannel(), mqmftHost.getUserId(),
					mqmftHost.getActive());
			jdbcTemplate.execute(query);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to insert the records", e),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Records Inserted successfully ", HttpStatus.OK);
	}

	public ResponseEntity<?> delete(int id) {
		try {
			query = String.format(MftQueryConstants.Delete_Host, id);
			jdbcTemplate.execute(query);
		} catch (Exception e) {
			return new ResponseEntity<>(new MftUserException("failed to delete the records", e),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Records deleted successfully ", HttpStatus.OK);
	}
}
