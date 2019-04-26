package com.mqmft.mft.util;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Schedule_details {
	private String Timebase;
	private String Frequency;
	private String Interval;
	private String Starttime;
	private String Endtime;
	/**
	 * @return the timebase
	 */
	public String getTimebase() {
		return Timebase;
	}
	/**
	 * @param timebase the timebase to set
	 */
	public void setTimebase(String timebase) {
		Timebase = timebase;
	}
	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return Frequency;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		Frequency = frequency;
	}
	/**
	 * @return the interval
	 */
	public String getInterval() {
		return Interval;
	}
	/**
	 * @param interval the interval to set
	 */
	public void setInterval(String interval) {
		Interval = interval;
	}
	/**
	 * @return the starttime
	 */
	public String getStarttime() {
		return Starttime;
	}
	/**
	 * @param starttime the starttime to set
	 */
	public void setStarttime(String starttime) {
		Starttime = starttime;
	}
	/**
	 * @return the endtime
	 */
	public String getEndtime() {
		return Endtime;
	}
	/**
	 * @param endtime the endtime to set
	 */
	public void setEndtime(String endtime) {
		Endtime = endtime;
	}
	
}
