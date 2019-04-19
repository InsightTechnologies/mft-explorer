package com.mft.rest.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Configuration

public class Transfer_Details {
	private String action;
	private String checksums;
	private String dest_Type;
	private String e_Pattern;
	private String exists_AgentName;
	private String exists_QueMGR;
	private String fileSizeB;
	private String job_Name;
	private String m_Pattern;
	private String monitor_ID;
	private String monitor_Name;
	private String pattern_Type;
	private String poll_Frequency;
	private int poll_Interval;
	private String polls;
	private String post_Dest;
	private String post_Src;
	private String pre_Dest;
	private String pre_Src;
	private String reply_Queue;
	private String resource_Info;
	private String resource_Type;
	private Schedule_details schedule;

	private String source_Type;
	private String src_Agent;
	private String src_Channel;
	private String src_Disposition;
	private String src_File;
	private String src_HostIP;
	private String src_HostName;
	private int src_Port;;
	private String src_QueMgr;
	private String src_UserID;
	private String trans_Mode;
	private String trans_Priority;
	private String trg_Condition;
	private String trgt_Agent;
	private String trgt_Exists;
	private String trgt_File;
	private String trgt_QueMgr;
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return the checksums
	 */
	public String getChecksums() {
		return checksums;
	}
	/**
	 * @param checksums the checksums to set
	 */
	public void setChecksums(String checksums) {
		this.checksums = checksums;
	}
	/**
	 * @return the dest_Type
	 */
	public String getDest_Type() {
		return dest_Type;
	}
	/**
	 * @param dest_Type the dest_Type to set
	 */
	public void setDest_Type(String dest_Type) {
		this.dest_Type = dest_Type;
	}
	/**
	 * @return the e_Pattern
	 */
	public String getE_Pattern() {
		return e_Pattern;
	}
	/**
	 * @param e_Pattern the e_Pattern to set
	 */
	public void setE_Pattern(String e_Pattern) {
		this.e_Pattern = e_Pattern;
	}
	/**
	 * @return the exists_AgentName
	 */
	public String getExists_AgentName() {
		return exists_AgentName;
	}
	/**
	 * @param exists_AgentName the exists_AgentName to set
	 */
	public void setExists_AgentName(String exists_AgentName) {
		this.exists_AgentName = exists_AgentName;
	}
	/**
	 * @return the exists_QueMGR
	 */
	public String getExists_QueMGR() {
		return exists_QueMGR;
	}
	/**
	 * @param exists_QueMGR the exists_QueMGR to set
	 */
	public void setExists_QueMGR(String exists_QueMGR) {
		this.exists_QueMGR = exists_QueMGR;
	}
	/**
	 * @return the fileSizeB
	 */
	public String getFileSizeB() {
		return fileSizeB;
	}
	/**
	 * @param fileSizeB the fileSizeB to set
	 */
	public void setFileSizeB(String fileSizeB) {
		this.fileSizeB = fileSizeB;
	}
	/**
	 * @return the job_Name
	 */
	public String getJob_Name() {
		return job_Name;
	}
	/**
	 * @param job_Name the job_Name to set
	 */
	public void setJob_Name(String job_Name) {
		this.job_Name = job_Name;
	}
	/**
	 * @return the m_Pattern
	 */
	public String getM_Pattern() {
		return m_Pattern;
	}
	/**
	 * @param m_Pattern the m_Pattern to set
	 */
	public void setM_Pattern(String m_Pattern) {
		this.m_Pattern = m_Pattern;
	}
	/**
	 * @return the monitor_ID
	 */
	public String getMonitor_ID() {
		return monitor_ID;
	}
	/**
	 * @param monitor_ID the monitor_ID to set
	 */
	public void setMonitor_ID(String monitor_ID) {
		this.monitor_ID = monitor_ID;
	}
	/**
	 * @return the monitor_Name
	 */
	public String getMonitor_Name() {
		return monitor_Name;
	}
	/**
	 * @param monitor_Name the monitor_Name to set
	 */
	public void setMonitor_Name(String monitor_Name) {
		this.monitor_Name = monitor_Name;
	}
	/**
	 * @return the pattern_Type
	 */
	public String getPattern_Type() {
		return pattern_Type;
	}
	/**
	 * @param pattern_Type the pattern_Type to set
	 */
	public void setPattern_Type(String pattern_Type) {
		this.pattern_Type = pattern_Type;
	}
	/**
	 * @return the poll_Frequency
	 */
	public String getPoll_Frequency() {
		return poll_Frequency;
	}
	/**
	 * @param poll_Frequency the poll_Frequency to set
	 */
	public void setPoll_Frequency(String poll_Frequency) {
		this.poll_Frequency = poll_Frequency;
	}
	/**
	 * @return the poll_Interval
	 */
	public int getPoll_Interval() {
		return poll_Interval;
	}
	/**
	 * @param poll_Interval the poll_Interval to set
	 */
	public void setPoll_Interval(int poll_Interval) {
		this.poll_Interval = poll_Interval;
	}
	/**
	 * @return the polls
	 */
	public String getPolls() {
		return polls;
	}
	/**
	 * @param polls the polls to set
	 */
	public void setPolls(String polls) {
		this.polls = polls;
	}
	/**
	 * @return the post_Dest
	 */
	public String getPost_Dest() {
		return post_Dest;
	}
	/**
	 * @param post_Dest the post_Dest to set
	 */
	public void setPost_Dest(String post_Dest) {
		this.post_Dest = post_Dest;
	}
	/**
	 * @return the post_Src
	 */
	public String getPost_Src() {
		return post_Src;
	}
	/**
	 * @param post_Src the post_Src to set
	 */
	public void setPost_Src(String post_Src) {
		this.post_Src = post_Src;
	}
	/**
	 * @return the pre_Dest
	 */
	public String getPre_Dest() {
		return pre_Dest;
	}
	/**
	 * @param pre_Dest the pre_Dest to set
	 */
	public void setPre_Dest(String pre_Dest) {
		this.pre_Dest = pre_Dest;
	}
	/**
	 * @return the pre_Src
	 */
	public String getPre_Src() {
		return pre_Src;
	}
	/**
	 * @param pre_Src the pre_Src to set
	 */
	public void setPre_Src(String pre_Src) {
		this.pre_Src = pre_Src;
	}
	/**
	 * @return the reply_Queue
	 */
	public String getReply_Queue() {
		return reply_Queue;
	}
	/**
	 * @param reply_Queue the reply_Queue to set
	 */
	public void setReply_Queue(String reply_Queue) {
		this.reply_Queue = reply_Queue;
	}
	/**
	 * @return the resource_Info
	 */
	public String getResource_Info() {
		return resource_Info;
	}
	/**
	 * @param resource_Info the resource_Info to set
	 */
	public void setResource_Info(String resource_Info) {
		this.resource_Info = resource_Info;
	}
	/**
	 * @return the resource_Type
	 */
	public String getResource_Type() {
		return resource_Type;
	}
	/**
	 * @param resource_Type the resource_Type to set
	 */
	public void setResource_Type(String resource_Type) {
		this.resource_Type = resource_Type;
	}
	
	/**
	 * @return the schedule
	 */
	public Schedule_details getSchedule() {
		return schedule;
	}
	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(Schedule_details schedule) {
		this.schedule = schedule;
	}
	/**
	 * @return the source_Type
	 */
	public String getSource_Type() {
		return source_Type;
	}
	/**
	 * @param source_Type the source_Type to set
	 */
	public void setSource_Type(String source_Type) {
		this.source_Type = source_Type;
	}
	/**
	 * @return the src_Agent
	 */
	public String getSrc_Agent() {
		return src_Agent;
	}
	/**
	 * @param src_Agent the src_Agent to set
	 */
	public void setSrc_Agent(String src_Agent) {
		this.src_Agent = src_Agent;
	}
	/**
	 * @return the src_Channel
	 */
	public String getSrc_Channel() {
		return src_Channel;
	}
	/**
	 * @param src_Channel the src_Channel to set
	 */
	public void setSrc_Channel(String src_Channel) {
		this.src_Channel = src_Channel;
	}
	/**
	 * @return the src_Disposition
	 */
	public String getSrc_Disposition() {
		return src_Disposition;
	}
	/**
	 * @param src_Disposition the src_Disposition to set
	 */
	public void setSrc_Disposition(String src_Disposition) {
		this.src_Disposition = src_Disposition;
	}
	/**
	 * @return the src_File
	 */
	public String getSrc_File() {
		return src_File;
	}
	/**
	 * @param src_File the src_File to set
	 */
	public void setSrc_File(String src_File) {
		this.src_File = src_File;
	}
	/**
	 * @return the src_HostIP
	 */
	public String getSrc_HostIP() {
		return src_HostIP;
	}
	/**
	 * @param src_HostIP the src_HostIP to set
	 */
	public void setSrc_HostIP(String src_HostIP) {
		this.src_HostIP = src_HostIP;
	}
	/**
	 * @return the src_HostName
	 */
	public String getSrc_HostName() {
		return src_HostName;
	}
	/**
	 * @param src_HostName the src_HostName to set
	 */
	public void setSrc_HostName(String src_HostName) {
		this.src_HostName = src_HostName;
	}
	/**
	 * @return the src_Port
	 */
	public int getSrc_Port() {
		return src_Port;
	}
	/**
	 * @param src_Port the src_Port to set
	 */
	public void setSrc_Port(int src_Port) {
		this.src_Port = src_Port;
	}
	/**
	 * @return the src_QueMgr
	 */
	public String getSrc_QueMgr() {
		return src_QueMgr;
	}
	/**
	 * @param src_QueMgr the src_QueMgr to set
	 */
	public void setSrc_QueMgr(String src_QueMgr) {
		this.src_QueMgr = src_QueMgr;
	}
	/**
	 * @return the src_UserID
	 */
	public String getSrc_UserID() {
		return src_UserID;
	}
	/**
	 * @param src_UserID the src_UserID to set
	 */
	public void setSrc_UserID(String src_UserID) {
		this.src_UserID = src_UserID;
	}
	/**
	 * @return the trans_Mode
	 */
	public String getTrans_Mode() {
		return trans_Mode;
	}
	/**
	 * @param trans_Mode the trans_Mode to set
	 */
	public void setTrans_Mode(String trans_Mode) {
		this.trans_Mode = trans_Mode;
	}
	/**
	 * @return the trans_Priority
	 */
	public String getTrans_Priority() {
		return trans_Priority;
	}
	/**
	 * @param trans_Priority the trans_Priority to set
	 */
	public void setTrans_Priority(String trans_Priority) {
		this.trans_Priority = trans_Priority;
	}
	/**
	 * @return the trg_Condition
	 */
	public String getTrg_Condition() {
		return trg_Condition;
	}
	/**
	 * @param trg_Condition the trg_Condition to set
	 */
	public void setTrg_Condition(String trg_Condition) {
		this.trg_Condition = trg_Condition;
	}
	/**
	 * @return the trgt_Agent
	 */
	public String getTrgt_Agent() {
		return trgt_Agent;
	}
	/**
	 * @param trgt_Agent the trgt_Agent to set
	 */
	public void setTrgt_Agent(String trgt_Agent) {
		this.trgt_Agent = trgt_Agent;
	}
	/**
	 * @return the trgt_Exists
	 */
	public String getTrgt_Exists() {
		return trgt_Exists;
	}
	/**
	 * @param trgt_Exists the trgt_Exists to set
	 */
	public void setTrgt_Exists(String trgt_Exists) {
		this.trgt_Exists = trgt_Exists;
	}
	/**
	 * @return the trgt_File
	 */
	public String getTrgt_File() {
		return trgt_File;
	}
	/**
	 * @param trgt_File the trgt_File to set
	 */
	public void setTrgt_File(String trgt_File) {
		this.trgt_File = trgt_File;
	}
	/**
	 * @return the trgt_QueMgr
	 */
	public String getTrgt_QueMgr() {
		return trgt_QueMgr;
	}
	/**
	 * @param trgt_QueMgr the trgt_QueMgr to set
	 */
	public void setTrgt_QueMgr(String trgt_QueMgr) {
		this.trgt_QueMgr = trgt_QueMgr;
	}

	

}
