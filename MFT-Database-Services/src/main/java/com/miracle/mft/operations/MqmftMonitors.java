package com.miracle.mft.operations;

public class MqmftMonitors {
	private String Monitor_ID;
	private String MonitorName;
	private String QMGR;
	private String Agent;
	private String mxml;
	private String Status;
	
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getMonitor_ID() {
		return Monitor_ID;
	}
	public void setMonitor_ID(String monitor_ID) {
		Monitor_ID = monitor_ID;
	}
	public String getMonitorName() {
		return MonitorName;
	}
	public void setMonitorName(String monitorName) {
		MonitorName = monitorName;
	}
	public String getQMGR() {
		return QMGR;
	}
	public void setQMGR(String qMGR) {
		QMGR = qMGR;
	}
	public String getAgent() {
		return Agent;
	}
	public void setAgent(String agent) {
		Agent = agent;
	}
	public String getMxml() {
		return mxml;
	}
	public void setMxml(String mxml) {
		this.mxml = mxml;
	}
	@Override
	public String toString() {
		return "MqmftMonitors [Monitor_ID=" + Monitor_ID + ", MonitorName=" + MonitorName + ", QMGR=" + QMGR
				+ ", Agent=" + Agent + ", mxml=" + mxml + ", Status=" + Status + "]";
	}
	
	
}
