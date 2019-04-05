package com.miracle.mft.operation.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.miracle.mft.model.OriginalXMLRequest;

@Component
public class OriginalXMLRequestRowMapper implements RowMapper<OriginalXMLRequest> {

	public OriginalXMLRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
		OriginalXMLRequest originalXMLRequest = new OriginalXMLRequest();
		originalXMLRequest.setMo_xml(rs.getString("MO_XML"));
		return originalXMLRequest;
	}

}
