package com.mqmft.mft.util;

import org.springframework.context.annotation.Configuration;

public class Transfer_Details {
	private String Action;
	private String Monitor_ID;
	private String Monitor_Name;
	private String Src_QueMgr;
	private String Src_HostName;
	private String Src_HostIP;
	private String Src_Channel;
	private int Src_Port;
	private String Src_UserID;
	private String Trgt_QueMgr;
	private String Src_Agent;
	private String Trgt_Agent;
	private String Resource_Type;
	private String Resource_Info;
	private int Poll_Interval;
	private String Poll_Frequency;
	private String M_Pattern;
	private String E_Pattern;
	private String Pattern_Type;
	private String Trg_Condition;
	private String FileSizeB;
	private String Polls;
	private String Transfer_Mode;
	private String Src_File;
	private String Trgt_File;
	private String Src_Disposition;
	private String Trgt_Exists;
	private String Pre_Src;
	private String Post_Src;
	private String Pre_Dest;
	private String Post_Dest;
	private String Job_Name;
	private String Reply_Queue;
	private String Transfer_Priority;
	private String Checksums;
	private String Source_Type;
	private String Destination_Type;
	
	private Schedule_details Schedule;
	/**
	 * @return the monitor_ID
	 */
	public String getMonitor_ID() {
		return Monitor_ID;
	}
	/**
	 * @param monitor_ID the monitor_ID to set
	 */
	public void setMonitor_ID(String monitor_ID) {
		Monitor_ID = monitor_ID;
	}
	/**
	 * @return the monitor_Name
	 */
	public String getMonitor_Name() {
		return Monitor_Name;
	}
	/**
	 * @param monitor_Name the monitor_Name to set
	 */
	public void setMonitor_Name(String monitor_Name) {
		Monitor_Name = monitor_Name;
	}
	/**
	 * @return the src_QueMgr
	 */
	public String getSrc_QueMgr() {
		return Src_QueMgr;
	}
	/**
	 * @param src_QueMgr the src_QueMgr to set
	 */
	public void setSrc_QueMgr(String src_QueMgr) {
		Src_QueMgr = src_QueMgr;
	}
	/**
	 * @return the src_HostName
	 */
	public String getSrc_HostName() {
		return Src_HostName;
	}
	/**
	 * @param src_HostName the src_HostName to set
	 */
	public void setSrc_HostName(String src_HostName) {
		Src_HostName = src_HostName;
	}
	/**
	 * @return the src_HostIP
	 */
	public String getSrc_HostIP() {
		return Src_HostIP;
	}
	/**
	 * @param src_HostIP the src_HostIP to set
	 */
	public void setSrc_HostIP(String src_HostIP) {
		Src_HostIP = src_HostIP;
	}
	/**
	 * @return the src_Channel
	 */
	public String getSrc_Channel() {
		return Src_Channel;
	}
	/**
	 * @param src_Channel the src_Channel to set
	 */
	public void setSrc_Channel(String src_Channel) {
		Src_Channel = src_Channel;
	}
	/**
	 * @return the src_Port
	 */
	public int getSrc_Port() {
		return Src_Port;
	}
	/**
	 * @param src_Port the src_Port to set
	 */
	public void setSrc_Port(int src_Port) {
		Src_Port = src_Port;
	}
	/**
	 * @return the src_UserID
	 */
	public String getSrc_UserID() {
		return Src_UserID;
	}
	/**
	 * @param src_UserID the src_UserID to set
	 */
	public void setSrc_UserID(String src_UserID) {
		Src_UserID = src_UserID;
	}
	/**
	 * @return the trgt_QueMgr
	 */
	public String getTrgt_QueMgr() {
		return Trgt_QueMgr;
	}
	/**
	 * @param trgt_QueMgr the trgt_QueMgr to set
	 */
	public void setTrgt_QueMgr(String trgt_QueMgr) {
		Trgt_QueMgr = trgt_QueMgr;
	}
	/**
	 * @return the src_Agent
	 */
	public String getSrc_Agent() {
		return Src_Agent;
	}
	/**
	 * @param src_Agent the src_Agent to set
	 */
	public void setSrc_Agent(String src_Agent) {
		Src_Agent = src_Agent;
	}
	/**
	 * @return the trgt_Agent
	 */
	public String getTrgt_Agent() {
		return Trgt_Agent;
	}
	/**
	 * @param trgt_Agent the trgt_Agent to set
	 */
	public void setTrgt_Agent(String trgt_Agent) {
		Trgt_Agent = trgt_Agent;
	}
	/**
	 * @return the resource_Type
	 */
	public String getResource_Type() {
		return Resource_Type;
	}
	/**
	 * @param resource_Type the resource_Type to set
	 */
	public void setResource_Type(String resource_Type) {
		Resource_Type = resource_Type;
	}
	/**
	 * @return the resource_Info
	 */
	public String getResource_Info() {
		return Resource_Info;
	}
	/**
	 * @param resource_Info the resource_Info to set
	 */
	public void setResource_Info(String resource_Info) {
		Resource_Info = resource_Info;
	}
	/**
	 * @return the poll_Interval
	 */
	public int getPoll_Interval() {
		return Poll_Interval;
	}
	/**
	 * @param poll_Interval the poll_Interval to set
	 */
	public void setPoll_Interval(int poll_Interval) {
		Poll_Interval = poll_Interval;
	}
	/**
	 * @return the poll_Frequency
	 */
	public String getPoll_Frequency() {
		return Poll_Frequency;
	}
	/**
	 * @param poll_Frequency the poll_Frequency to set
	 */
	public void setPoll_Frequency(String poll_Frequency) {
		Poll_Frequency = poll_Frequency;
	}
	/**
	 * @return the m_Pattern
	 */
	public String getM_Pattern() {
		return M_Pattern;
	}
	/**
	 * @param m_Pattern the m_Pattern to set
	 */
	public void setM_Pattern(String m_Pattern) {
		M_Pattern = m_Pattern;
	}
	/**
	 * @return the e_Pattern
	 */
	public String getE_Pattern() {
		return E_Pattern;
	}
	/**
	 * @param e_Pattern the e_Pattern to set
	 */
	public void setE_Pattern(String e_Pattern) {
		E_Pattern = e_Pattern;
	}
	/**
	 * @return the pattern_Type
	 */
	public String getPattern_Type() {
		return Pattern_Type;
	}
	/**
	 * @param pattern_Type the pattern_Type to set
	 */
	public void setPattern_Type(String pattern_Type) {
		Pattern_Type = pattern_Type;
	}
	/**
	 * @return the trg_Condition
	 */
	public String getTrg_Condition() {
		return Trg_Condition;
	}
	/**
	 * @param trg_Condition the trg_Condition to set
	 */
	public void setTrg_Condition(String trg_Condition) {
		Trg_Condition = trg_Condition;
	}
	/**
	 * @return the fileSizeB
	 */
	public String getFileSizeB() {
		return FileSizeB;
	}
	/**
	 * @param fileSizeB the fileSizeB to set
	 */
	public void setFileSizeB(String fileSizeB) {
		FileSizeB = fileSizeB;
	}
	/**
	 * @return the polls
	 */
	public String getPolls() {
		return Polls;
	}
	/**
	 * @param polls the polls to set
	 */
	public void setPolls(String polls) {
		Polls = polls;
	}
	/**
	 * @return the transfer_Mode
	 */
	public String getTransfer_Mode() {
		return Transfer_Mode;
	}
	/**
	 * @param transfer_Mode the transfer_Mode to set
	 */
	public void setTransfer_Mode(String transfer_Mode) {
		Transfer_Mode = transfer_Mode;
	}
	/**
	 * @return the src_File
	 */
	public String getSrc_File() {
		return Src_File;
	}
	/**
	 * @param src_File the src_File to set
	 */
	public void setSrc_File(String src_File) {
		Src_File = src_File;
	}
	/**
	 * @return the trgt_File
	 */
	public String getTrgt_File() {
		return Trgt_File;
	}
	/**
	 * @param trgt_File the trgt_File to set
	 */
	public void setTrgt_File(String trgt_File) {
		Trgt_File = trgt_File;
	}
	/**
	 * @return the src_Disposition
	 */
	public String getSrc_Disposition() {
		return Src_Disposition;
	}
	/**
	 * @param src_Disposition the src_Disposition to set
	 */
	public void setSrc_Disposition(String src_Disposition) {
		Src_Disposition = src_Disposition;
	}
	/**
	 * @return the trgt_Exists
	 */
	public String getTrgt_Exists() {
		return Trgt_Exists;
	}
	/**
	 * @param trgt_Exists the trgt_Exists to set
	 */
	public void setTrgt_Exists(String trgt_Exists) {
		Trgt_Exists = trgt_Exists;
	}
	/**
	 * @return the pre_Src
	 */
	public String getPre_Src() {
		return Pre_Src;
	}
	/**
	 * @param pre_Src the pre_Src to set
	 */
	public void setPre_Src(String pre_Src) {
		Pre_Src = pre_Src;
	}
	/**
	 * @return the post_Src
	 */
	public String getPost_Src() {
		return Post_Src;
	}
	/**
	 * @param post_Src the post_Src to set
	 */
	public void setPost_Src(String post_Src) {
		Post_Src = post_Src;
	}
	/**
	 * @return the pre_Dest
	 */
	public String getPre_Dest() {
		return Pre_Dest;
	}
	/**
	 * @param pre_Dest the pre_Dest to set
	 */
	public void setPre_Dest(String pre_Dest) {
		Pre_Dest = pre_Dest;
	}
	/**
	 * @return the post_Dest
	 */
	public String getPost_Dest() {
		return Post_Dest;
	}
	/**
	 * @param post_Dest the post_Dest to set
	 */
	public void setPost_Dest(String post_Dest) {
		Post_Dest = post_Dest;
	}
	/**
	 * @return the job_Name
	 */
	public String getJob_Name() {
		return Job_Name;
	}
	/**
	 * @param job_Name the job_Name to set
	 */
	public void setJob_Name(String job_Name) {
		Job_Name = job_Name;
	}
	/**
	 * @return the reply_Queue
	 */
	public String getReply_Queue() {
		return Reply_Queue;
	}
	/**
	 * @param reply_Queue the reply_Queue to set
	 */
	public void setReply_Queue(String reply_Queue) {
		Reply_Queue = reply_Queue;
	}
	/**
	 * @return the transfer_Priority
	 */
	public String getTransfer_Priority() {
		return Transfer_Priority;
	}
	/**
	 * @param transfer_Priority the transfer_Priority to set
	 */
	public void setTransfer_Priority(String transfer_Priority) {
		Transfer_Priority = transfer_Priority;
	}
	/**
	 * @return the checksums
	 */
	public String getChecksums() {
		return Checksums;
	}
	/**
	 * @param checksums the checksums to set
	 */
	public void setChecksums(String checksums) {
		Checksums = checksums;
	}
	/**
	 * @return the source_Type
	 */
	public String getSource_Type() {
		return Source_Type;
	}
	/**
	 * @param source_Type the source_Type to set
	 */
	public void setSource_Type(String source_Type) {
		Source_Type = source_Type;
	}
	/**
	 * @return the destination_Type
	 */
	public String getDestination_Type() {
		return Destination_Type;
	}
	/**
	 * @param destination_Type the destination_Type to set
	 */
	public void setDestination_Type(String destination_Type) {
		Destination_Type = destination_Type;
	}
	/**
	 * @return the schedule
	 */
	public Schedule_details getSchedule() {
		return Schedule;
	}
	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(Schedule_details schedule) {
		Schedule = schedule;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return Action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		Action = action;
	}
	
	
}
