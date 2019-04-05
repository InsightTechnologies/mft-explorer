package com.miracle.mft.model;

public class TransferLog {

	private String transferId;

	private long completeId;

	private String jobName;

	private String relatedTransferId;

	private int resultcode;

	private String overAllResultText;

	private long scheduleId;

	private long startId;

	private String status;
	private String sourceSystem;
	private String targetSystem;
	private String startTime;
	private String endTime;

	private long sourceFileSize;

	private String sourceFilename;

	private long destinationFileSize;

	private String destinationFilename;

	private String resultText;
	private int numberOfFiles;
	private int fileResult;
	private String fileResultMessage;

	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}

	public long getCompleteId() {
		return completeId;
	}

	public void setCompleteId(long completeId) {
		this.completeId = completeId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getRelatedTransferId() {
		return relatedTransferId;
	}

	public void setRelatedTransferId(String relatedTransferId) {
		this.relatedTransferId = relatedTransferId;
	}

	public int getResultcode() {
		return resultcode;
	}

	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}

	public long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public long getStartId() {
		return startId;
	}

	public void setStartId(long startId) {
		this.startId = startId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public long getSourceFileSize() {
		return sourceFileSize;
	}

	public void setSourceFileSize(long sourceFileSize) {
		this.sourceFileSize = sourceFileSize;
	}

	public String getSourceFilename() {
		return sourceFilename;
	}

	public void setSourceFilename(String sourceFilename) {
		this.sourceFilename = sourceFilename;
	}

	public long getDestinationFileSize() {
		return destinationFileSize;
	}

	public void setDestinationFileSize(long destinationFileSize) {
		this.destinationFileSize = destinationFileSize;
	}

	public String getDestinationFilename() {
		return destinationFilename;
	}

	public void setDestinationFilename(String destinationFilename) {
		this.destinationFilename = destinationFilename;
	}

	public String getResultText() {
		return resultText;
	}

	public void setResultText(String resultText) {
		this.resultText = resultText;
	}

	public int getNumberOfFiles() {
		return numberOfFiles;
	}

	public void setNumberOfFiles(int numberOfFiles) {
		this.numberOfFiles = numberOfFiles;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getTargetSystem() {
		return targetSystem;
	}

	public void setTargetSystem(String targetSystem) {
		this.targetSystem = targetSystem;
	}

	public String getOverAllResultText() {
		return overAllResultText;
	}

	public void setOverAllResultText(String overAllResultText) {
		this.overAllResultText = overAllResultText;
	}

	public int getFileResult() {
		return fileResult;
	}

	public void setFileResult(int fileResult) {
		this.fileResult = fileResult;
	}

	public String getFileResultMessage() {
		return fileResultMessage;
	}

	public void setFileResultMessage(String fileResultMessage) {
		this.fileResultMessage = fileResultMessage;
	}

}
