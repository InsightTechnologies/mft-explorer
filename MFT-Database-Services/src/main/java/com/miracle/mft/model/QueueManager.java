package com.miracle.mft.model;

public class QueueManager {

	private int qmid;

	private int hostId;

	private String isActive;

	private String isAgent;

	private String isCmnd;

	private String isCoord;

	private String qmChannel;

	private String qmName;

	private int qmPort;

	private String qmUserid;
	private String host_name;
	private String host_ip;

	public int getQmid() {
		return qmid;
	}

	public void setQmid(int qmid) {
		this.qmid = qmid;
	}

	public int getHostId() {
		return hostId;
	}

	public void setHostId(int hostId) {
		this.hostId = hostId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsAgent() {
		return isAgent;
	}

	public void setIsAgent(String isAgent) {
		this.isAgent = isAgent;
	}

	public String getIsCmnd() {
		return isCmnd;
	}

	public void setIsCmnd(String isCmnd) {
		this.isCmnd = isCmnd;
	}

	public String getIsCoord() {
		return isCoord;
	}

	public void setIsCoord(String isCoord) {
		this.isCoord = isCoord;
	}

	public String getQmChannel() {
		return qmChannel;
	}

	public void setQmChannel(String qmChannel) {
		this.qmChannel = qmChannel;
	}

	public String getQmName() {
		return qmName;
	}

	public void setQmName(String qmName) {
		this.qmName = qmName;
	}

	public int getQmPort() {
		return qmPort;
	}

	public void setQmPort(int qmPort) {
		this.qmPort = qmPort;
	}

	public String getQmUserid() {
		return qmUserid;
	}

	public void setQmUserid(String qmUserid) {
		this.qmUserid = qmUserid;
	}

	public String getHost_name() {
		return host_name;
	}

	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}

	public String getHost_ip() {
		return host_ip;
	}

	public void setHost_ip(String host_ip) {
		this.host_ip = host_ip;
	}

	@Override
	public String toString() {
		return "QueueManager [qmid=" + qmid + ", hostId=" + hostId + ", isActive=" + isActive + ", isAgent=" + isAgent
				+ ", isCmnd=" + isCmnd + ", isCoord=" + isCoord + ", qmChannel=" + qmChannel + ", qmName=" + qmName
				+ ", qmPort=" + qmPort + ", qmUserid=" + qmUserid + ", host_name=" + host_name + ", host_ip=" + host_ip
				+ "]";
	}

}
