package com.miracle.mft.operation.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.miracle.mft.model.QueueManager;

@Component
public class QueueManagerRowMapper implements RowMapper<QueueManager> {

	public QueueManager mapRow(ResultSet rs, int rowNum) throws SQLException {
		QueueManager manager = new QueueManager();
		manager.setHost_ip(rs.getString("HOST_IP"));
		manager.setHost_name(rs.getString("HOST_NAME"));
		manager.setHostId(rs.getInt("HOST_ID"));
		manager.setIsActive(rs.getString("IS_ACTIVE"));
		manager.setIsAgent(rs.getString("IS_AGENT"));
		manager.setIsCmnd(rs.getString("IS_CMND"));
		manager.setIsCoord(rs.getString("IS_COORD"));
		manager.setQmChannel(rs.getString("QM_CHANNEL"));
		manager.setQmid(rs.getInt("QMID"));
		manager.setQmName(rs.getString("QM_NAME"));
		manager.setQmPort(rs.getInt("QM_PORT"));
		manager.setQmUserid(rs.getString("QM_USERID"));
		return manager;
	}

}
