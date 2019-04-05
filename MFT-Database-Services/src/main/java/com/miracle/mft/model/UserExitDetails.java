package com.miracle.mft.model;

public class UserExitDetails {

	private String exitParams;
	private String userExitValue;
	private String userExitType;

	public String getExitParams() {
		return exitParams;
	}

	public void setExitParams(String exitParams) {
		this.exitParams = exitParams;
	}

	public String getUserExitValue() {
		return userExitValue;
	}

	public void setUserExitValue(String userExitValue) {
		this.userExitValue = userExitValue;
	}

	public String getUserExitType() {
		return userExitType;
	}

	public void setUserExitType(String userExitType) {
		this.userExitType = userExitType;
	}

	@Override
	public String toString() {
		return "UserExitDetails [exitParams=" + exitParams + ", userExitValue=" + userExitValue + ", userExitType="
				+ userExitType + "]";
	}

}
