package com.mqrest.MQService;

import java.util.StringTokenizer;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSException;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

public class MQUtilities {
public static JmsConnectionFactory getConnectionFactory(String connString) throws JMSException{
	
	String [] connDetails = connString.split(":");
	 String HOST = connDetails[0];
	 String CHANNEL = connDetails[1];
	 int PORT =	 Integer.parseInt(connDetails[2]);

	JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
	JmsConnectionFactory cf = ff.createConnectionFactory();

	// Set the properties
	cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
	cf.setIntProperty(WMQConstants.WMQ_PORT, PORT);
	cf.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
	cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
	cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
	return cf;
}

}
