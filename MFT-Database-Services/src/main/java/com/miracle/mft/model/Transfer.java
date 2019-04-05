package com.miracle.mft.model;

public class Transfer {

	private String JOB_NAME;

	public String getTransferId() {
		return JOB_NAME;
	}

	public void setTransferId(String JOB_NAME) {
		this.JOB_NAME = JOB_NAME;
	}

	@Override
	public String toString() {
		return "Transfer [JOB_NAME=" + JOB_NAME + "]";
	}

}
