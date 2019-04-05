package com.miracle.mft.operation.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.miracle.mft.model.MqmftHost;

@Component
public class MqmftHostRowMapper implements RowMapper<MqmftHost> {

	public MqmftHost mapRow(ResultSet rs, int rowNum) throws SQLException {
		MqmftHost mqmftHost = new MqmftHost();
		mqmftHost.setActive(rs.getString("ACTIVE"));
		mqmftHost.setAgentPort(rs.getInt("AGENT_PORT"));
		mqmftHost.setAgentQmgr(rs.getString("AGENT_QMGR"));
		mqmftHost.setCoordPort(rs.getInt("COORD_PORT"));
		mqmftHost.setCoordQmgr(rs.getString("COORD_QMGR"));
		mqmftHost.setDefaultChannel(rs.getString("DEFAULT_CHANNEL"));
		mqmftHost.setDeleted(rs.getString("DELETED"));
		mqmftHost.setHostId(rs.getInt("HOST_ID"));
		mqmftHost.setHostIp(rs.getString("HOST_IP"));
		mqmftHost.setHostName(rs.getString("HOST_NAME"));
		mqmftHost.setUserId(rs.getString("USER_ID"));
		return mqmftHost;
	}

}
