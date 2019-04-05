package com.miracle.mft.model;

public class MqmftUserExit {

	private long id;

	private String userExitType;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserExitType() {
		return userExitType;
	}

	public void setUserExitType(String userExitType) {
		this.userExitType = userExitType;
	}

	@Override
	public String toString() {
		return "MqmftUserExit [id=" + id + ", userExitType=" + userExitType + "]";
	}
	
}
