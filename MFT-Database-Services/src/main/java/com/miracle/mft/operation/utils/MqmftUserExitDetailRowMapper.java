package com.miracle.mft.operation.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.miracle.mft.model.MqmftUserExitDetail;

@Component
public class MqmftUserExitDetailRowMapper implements RowMapper<MqmftUserExitDetail> {

	public MqmftUserExitDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		MqmftUserExitDetail mqmftUserExitDetail = new MqmftUserExitDetail();
		mqmftUserExitDetail.setExitId(rs.getLong("EXIT_ID"));
		mqmftUserExitDetail.setExitParams(rs.getString("EXIT_PARAMS"));
		mqmftUserExitDetail.setId(rs.getLong("ID"));
		mqmftUserExitDetail.setUserExitValue(rs.getString("USER_EXIT_VALUE"));
		return mqmftUserExitDetail;
	}

}
