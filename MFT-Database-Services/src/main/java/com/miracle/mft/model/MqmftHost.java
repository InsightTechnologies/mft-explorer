package com.miracle.mft.model;

public class MqmftHost {

	private int hostId;
	private String active;
	private int agentPort;
	private String agentQmgr;
	private int coordPort;
	private String coordQmgr;
	private String defaultChannel;

	private String deleted;
	private String hostIp;
	private String hostName;
	private String userId;

	public int getHostId() {
		return hostId;
	}

	public void setHostId(int hostId) {
		this.hostId = hostId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public int getAgentPort() {
		return agentPort;
	}

	public void setAgentPort(int agentPort) {
		this.agentPort = agentPort;
	}

	public String getAgentQmgr() {
		return agentQmgr;
	}

	public void setAgentQmgr(String agentQmgr) {
		this.agentQmgr = agentQmgr;
	}

	public int getCoordPort() {
		return coordPort;
	}

	public void setCoordPort(int coordPort) {
		this.coordPort = coordPort;
	}

	public String getCoordQmgr() {
		return coordQmgr;
	}

	public void setCoordQmgr(String coordQmgr) {
		this.coordQmgr = coordQmgr;
	}

	public String getDefaultChannel() {
		return defaultChannel;
	}

	public void setDefaultChannel(String defaultChannel) {
		this.defaultChannel = defaultChannel;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "MqmftHost [hostId=" + hostId + ", active=" + active + ", agentPort=" + agentPort + ", agentQmgr="
				+ agentQmgr + ", coordPort=" + coordPort + ", coordQmgr=" + coordQmgr + ", defaultChannel="
				+ defaultChannel + ", deleted=" + deleted + ", hostIp=" + hostIp + ", hostName=" + hostName
				+ ", userId=" + userId + "]";
	}

}
