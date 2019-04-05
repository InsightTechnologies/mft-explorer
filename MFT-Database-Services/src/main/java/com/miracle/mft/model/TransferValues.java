package com.miracle.mft.model;

public class TransferValues {
	private String key;
	private String value;
	private String sourceFilename;
	private String destinationFilename;
	private String destinationMessageQueueName;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSourceFilename() {
		return sourceFilename;
	}

	public void setSourceFilename(String sourceFilename) {
		this.sourceFilename = sourceFilename;
	}

	public String getDestinationFilename() {
		return destinationFilename;
	}

	public void setDestinationFilename(String destinationFilename) {
		this.destinationFilename = destinationFilename;
	}

	public String getDestinationMessageQueueName() {
		return destinationMessageQueueName;
	}

	public void setDestinationMessageQueueName(String destinationMessageQueueName) {
		this.destinationMessageQueueName = destinationMessageQueueName;
	}

	@Override
	public String toString() {
		return "TransferValues [key=" + key + ", value=" + value + ", sourceFilename=" + sourceFilename
				+ ", destinationFilename=" + destinationFilename + ", destinationMessageQueueName="
				+ destinationMessageQueueName + "]";
	}

}
