package com.mftvalidation.rest.beans;

public class ValidationDetails {
	
	private String srcPath;

	//  /usr/eai_ts/<region>_eai/<sourceSystem>/EAI/<CountryCode>/<Domain>/<RicefId>
	// /usr/eai_ts/namr_eai/TUMBLEWEED/EAI/UN/AP/AP1021
	//src_eai_in
	//src_eai_in_success
	//monitor_eai_in
	
	private String host;      //
	private String countryCode; //UN,CN
	private String racfId;  
	private String domain; //Functional Team name Ex: AP,GL
	private String region;
	private String srcSystem;
	private String channel;
	private String port;
	private String from;
	private String to;
	private String subject;
	
	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String sourcePath) {
		this.srcPath = sourcePath;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getRacfId() {
		return racfId;
	}
	public void setRacfId(String racfId) {
		this.racfId = racfId;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getSrcSystem() {
		return srcSystem;
	}
	public void setSrcSystem(String sourceSystem) {
		this.srcSystem = sourceSystem;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
}
