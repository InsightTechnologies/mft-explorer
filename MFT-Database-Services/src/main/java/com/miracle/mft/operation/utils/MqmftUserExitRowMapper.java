package com.miracle.mft.operation.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.miracle.mft.model.MqmftUserExit;

@Component
public class MqmftUserExitRowMapper implements RowMapper<MqmftUserExit> {

	public MqmftUserExit mapRow(ResultSet rs, int rowNum) throws SQLException {
		MqmftUserExit mqmftUserExit = new MqmftUserExit();
		mqmftUserExit.setId(rs.getLong("ID"));
		mqmftUserExit.setUserExitType(rs.getString("USER_EXIT_TYPE"));
		return mqmftUserExit;
	}

}
