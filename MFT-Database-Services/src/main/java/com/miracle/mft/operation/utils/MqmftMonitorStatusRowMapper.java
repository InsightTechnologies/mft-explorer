package com.miracle.mft.operation.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.miracle.mft.model.MqmftMonitorStatus;

@Component
public class MqmftMonitorStatusRowMapper implements RowMapper<MqmftMonitorStatus> {

	public MqmftMonitorStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
		MqmftMonitorStatus monitorStatus = new MqmftMonitorStatus();
		monitorStatus.setMonitorNames(rs.getString("MonitorNames"));
		return monitorStatus;
	}

}
