package com.miracle.mft.operation.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.miracle.mft.model.UserExitDetails;

@Component
public class UserExitDetailsRowMapper implements RowMapper<UserExitDetails> {

	public UserExitDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserExitDetails userExitDetails = new UserExitDetails();
		userExitDetails.setExitParams(rs.getString("EXIT_PARAMS"));
		userExitDetails.setUserExitType(rs.getString("userexittype"));
		userExitDetails.setUserExitValue(rs.getString("userexitdetail"));
		return null;
	}

}
