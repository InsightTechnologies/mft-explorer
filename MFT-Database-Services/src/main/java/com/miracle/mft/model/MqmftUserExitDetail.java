package com.miracle.mft.model;

import java.io.Serializable;


/**
 * The persistent class for the MQMFT_USER_EXIT_DETAILS database table.
 * 
 */

public class MqmftUserExitDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	private long exitId;

	private String deleted;

	
	private String exitParams;

	
	private long id;

	
	private String userExitValue;

	public MqmftUserExitDetail() {
	}

	public long getExitId() {
		return this.exitId;
	}

	public void setExitId(long exitId) {
		this.exitId = exitId;
	}

	public String getDeleted() {
		return this.deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getExitParams() {
		return this.exitParams;
	}

	public void setExitParams(String exitParams) {
		this.exitParams = exitParams;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserExitValue() {
		return this.userExitValue;
	}

	public void setUserExitValue(String userExitValue) {
		this.userExitValue = userExitValue;
	}

	@Override
	public String toString() {
		return "MqmftUserExitDetail [exitId=" + exitId + ", deleted=" + deleted + ", exitParams=" + exitParams + ", id="
				+ id + ", userExitValue=" + userExitValue + "]";
	}

}