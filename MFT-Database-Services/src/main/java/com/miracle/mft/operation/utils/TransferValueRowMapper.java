package com.miracle.mft.operation.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.miracle.mft.model.TransferValues;

@Component
public class TransferValueRowMapper implements RowMapper<TransferValues> {

	public TransferValues mapRow(ResultSet rs, int rowNum) throws SQLException {
		TransferValues transferValues = new TransferValues();
		transferValues.setKey(rs.getString("KEYNAME"));
		transferValues.setDestinationFilename(rs.getString("DFileName"));
		transferValues.setDestinationMessageQueueName(rs.getString("DMQName"));
		transferValues.setSourceFilename(rs.getString("SFileName"));
		transferValues.setValue(rs.getString("KEYVALUES"));
		return transferValues;
	}

}
