package com.miracle.mft.operation.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.miracle.mft.model.TransferLog;

@Component
public class TransferLogRowMapper implements RowMapper<TransferLog> {

	@Override
	public TransferLog mapRow(ResultSet rs, int rowNum) throws SQLException {
		TransferLog transferLog = new TransferLog();
		transferLog.setJobName(rs.getString("JOBNAME"));
		transferLog.setTransferId(rs.getString("TRANSFERID"));
		transferLog.setResultcode(rs.getInt("OVERALLRESULT"));
		transferLog.setSourceSystem(rs.getString("SOURCESYSTEM"));
		transferLog.setTargetSystem(rs.getString("TARGETSYSTEM"));
		transferLog.setOverAllResultText(rs.getString("OVERALLRESULTTEXT"));
		transferLog.setStartTime(rs.getString("STARTTIME"));
		transferLog.setEndTime(rs.getString("ENDTIME"));
		transferLog.setSourceFileSize(rs.getInt("SOURCEFILESIZE"));
		transferLog.setSourceFilename(rs.getString("SOURCEFILE"));
		transferLog.setDestinationFilename(rs.getString("DESTINATIONFILE"));
		transferLog.setDestinationFileSize(rs.getInt("DESTINATIONFILESIZE"));
		transferLog.setNumberOfFiles(rs.getInt("NUMBEROFFILES"));
		transferLog.setFileResult(rs.getInt("FILERESULT"));
		transferLog.setFileResultMessage(rs.getString("FILERESULTMESSAGE"));
		transferLog.setStatus(rs.getString("STATUS"));
		return transferLog;
	}

}
