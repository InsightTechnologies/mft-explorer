package com.miracle.mft.operation.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.miracle.mft.model.JobXml;

@Component
public class JobXmlRowMapper implements RowMapper<JobXml> {

	public JobXml mapRow(ResultSet rs, int rowNum) throws SQLException {
		JobXml jobXml = new JobXml();
		jobXml.setJobXML(rs.getString("JBXML"));
		return jobXml;
	}

}
