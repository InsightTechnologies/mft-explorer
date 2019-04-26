package com.miracle.mft.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.miracle.mft.model.MqmftMonitorStatus;

@Component
public class MqmftMonitorRowMapper implements RowMapper<MqmftMonitors>{

	public MqmftMonitors mapRow(ResultSet rs, int rowNum) throws SQLException {
		MqmftMonitors monitors = new MqmftMonitors();
		monitors.setMonitor_ID(rs.getString("Monitor_ID"));
		monitors.setMonitorName(rs.getString("MonitorName"));
		monitors.setQMGR(rs.getString("QMGR"));
		monitors.setAgent(rs.getString("Agent"));
		monitors.setMxml(rs.getString("mxml"));
		monitors.setStatus(rs.getString("Status"));
		return monitors;
	}

}
