package com.mft.rest.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Schedule_details {
	  private String endtime;
	  private String frequency;
	  private String interval;
	  private String starttime;
	  private String timebase;
	/**
	 * @return the endtime
	 */
	public String getEndtime() {
		return endtime;
	}
	/**
	 * @param endtime the endtime to set
	 */
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	/**
	 * @return the interval
	 */
	public String getInterval() {
		return interval;
	}
	/**
	 * @param interval the interval to set
	 */
	public void setInterval(String interval) {
		this.interval = interval;
	}
	/**
	 * @return the starttime
	 */
	public String getStarttime() {
		return starttime;
	}
	/**
	 * @param starttime the starttime to set
	 */
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	/**
	 * @return the timebase
	 */
	public String getTimebase() {
		return timebase;
	}
	/**
	 * @param timebase the timebase to set
	 */
	public void setTimebase(String timebase) {
		this.timebase = timebase;
	}
	
	
}
