package com.mft.rest.api.utility;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TransfersDeletionUtil {

	/**
	 * Method to delete monitor with specific monitor name 
	 * @param monitorName
	 * @param hostName
	 * @param user
	 * @return delete Monitor XML
	 */
	public String deleteMonitor(String monitorName, String hostName, String user) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<monitor:deleteMonitor xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"6.00\" xmlns:monitor=\"http://www.ibm.com/xmlns/wmqfte/7.0.1/MonitorDefinition\" xsi:schemaLocation=\"http://www.ibm.com/xmlns/wmqfte/7.0.1/MonitorDefinition ./Monitor.xsd\" >"
				+ "<name>" + monitorName + "</name><originator><hostName>" + hostName + "</hostName><userID>" + user
				+ "</userID></originator></monitor:deleteMonitor>";

	}
	
	/**
	 * Method to delete scheduler with specific id 
	 * @param scheduleID
	 * @param hostName
	 * @param user
	 * @return delete Schedule XML
	 */
	public String deleteScheduler(String scheduleID, String hostName, String user) {

		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<request xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"6.00\" xsi:noNamespaceSchemaLocation=\"FileTransfer.xsd\">"
				+ "<deleteScheduledTransfer><originator><delete><hostName>" + hostName + "</hostName><userID>" + user
				+ "</userID></delete>" + "</originator><ID>" + scheduleID + "</ID></deleteScheduledTransfer></request>";
	}

}
