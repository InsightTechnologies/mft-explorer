package com.miracle.mft.operation.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.miracle.mft.model.Transfer;

@Component
public class TransferRowMapper implements RowMapper<Transfer> {

	public Transfer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Transfer transfer = new Transfer();
		transfer.setTransferId(rs.getString("JOB_NAME"));
		return transfer;
	}

}
