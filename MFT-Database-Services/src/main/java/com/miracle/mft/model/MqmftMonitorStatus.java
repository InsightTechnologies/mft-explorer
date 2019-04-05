package com.miracle.mft.model;

public class MqmftMonitorStatus {
	private String monitorNames;
	private int hostId;

	public String getMonitorNames() {
		return monitorNames;
	}

	public void setMonitorNames(String monitorNames) {
		this.monitorNames = monitorNames;
	}

	public int getHostId() {
		return hostId;
	}

	public void setHostId(int hostId) {
		this.hostId = hostId;
	}

	@Override
	public String toString() {
		return "MqmftMonitorStatus [monitorNames=" + monitorNames + ", hostId=" + hostId + "]";
	}

}
