/*
* (c) Copyright IBM Corporation 2018
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.mqrest.MQService;

import java.awt.Point;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ibm.mq.MQAsyncStatus;
import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.MQConstants;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import com.ibm.mq.pcf.*;

/**
 * A minimal and simple application for Point-to-point messaging.
 *
 * Application makes use of fixed literals, any customisations will require
 * re-compilation of this source file. Application assumes that the named queue
 * is empty prior to a run.
 *
 * Notes:
 *
 * API type: JMS API (v2.0, simplified domain)
 *
 * Messaging domain: Point-to-point
 *
 * Provider type: IBM MQ
 *
 * Connection mode: Client connection
 *
 * JNDI in use: No
 *
 */
public class MQOperations {

	private static final String APP_USER = "MQ-JavaAPI";
	// System exit status value (assume unset value to be 1)
	private static int status = 1;

	/**
	 * 
	 * @param message
	 * @param connString
	 * @param queueName
	 */
	public static void putMessge(String message, String connString, String queueName) {

		// Variables
		JMSContext context = null;
		Destination destination = null;
		JMSProducer producer = null;
		JMSConsumer consumer = null;
		
		try {

			// Create JMS objects
			JmsConnectionFactory cf = MQUtilities.getConnectionFactory(connString);
			Connection connection = cf.createConnection();
			context = cf.createContext();
			destination = context.createQueue("queue:///" + queueName);
			cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, APP_USER);
			TextMessage messageData = context.createTextMessage(message);
			
			producer = context.createProducer();
			producer.send(destination, messageData);

			System.out.println("Sent message:\n" + messageData);

			recordSuccess();
		} catch (JMSException jmsex) {
			recordFailure(jmsex);
		}

	}

	/**
	 * 
	 * @param message
	 * @param connString
	 * @param queueName
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws DOMException
	 */
	@Autowired
	public static void putHeaderMessge(Map<Object,Object> header, String message, String connString, String queueName)
			throws DOMException, ParserConfigurationException, SAXException, IOException {
		// Variables
		JMSContext context = null;
		Destination destination = null;
		JMSProducer producer = null;
		JMSConsumer consumer = null;
		String QUEUE_NAME = queueName;
		try {
			// Create JMS objects
			List keyArray = Arrays.asList("host","connection","content-length","user-agent","cache-control","origin","postman-token","content-type","accept","accept-encoding","accept-language","expect");
			JmsConnectionFactory cf = MQUtilities.getConnectionFactory(connString);
			Connection connection = cf.createConnection();
			context = cf.createContext();
			destination = context.createQueue("queue:///" + QUEUE_NAME);
			cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, APP_USER);
			TextMessage messageData = context.createTextMessage(message);
			for (Map.Entry<Object,Object> entry : header.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				if (!keyArray.contains((entry.getKey())))
				messageData.setObjectProperty((String)entry.getKey(), (String)entry.getValue());
			}
			producer = context.createProducer();
			producer.send(destination, messageData);
			System.out.println("Sent message:\n" + messageData);

			recordSuccess();
		} catch (JMSException jmsex) {
			recordFailure(jmsex);
		}

	}

	public static TextMessage buildMQRFH2(TextMessage message, String usr)
			throws ParserConfigurationException, SAXException, IOException, DOMException, JMSException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(usr));
		// Build Document
		Document document = builder.parse(is);

		// Normalize the XML Structure; It's just too important !!
		document.getDocumentElement().normalize();

		// Here comes the root node
		Element root = document.getDocumentElement();
		System.out.println(root.getNodeName());

		// Get all employees
		NodeList nList = document.getElementsByTagName("usr");
		System.out.println("============================");

		Element element = (Element) nList.item(0);
		for (int temp = 0; temp < element.getElementsByTagName("*").getLength(); temp++) {

			message.setStringProperty(element.getElementsByTagName("*").item(temp).getNodeName(),
					element.getElementsByTagName("*").item(temp).getTextContent());
		}
		return message;
	}

	/**
	 * Record this run as successful.
	 */
	private static void recordSuccess() {
		System.out.println("SUCCESS");
		status = 0;
		return;
	}

	/**
	 * Record this run as failure.
	 *
	 * @param ex
	 */
	private static void recordFailure(Exception ex) {
		if (ex != null) {
			if (ex instanceof JMSException) {
				processJMSException((JMSException) ex);
			} else {
				System.out.println(ex);
			}
		}
		System.out.println("FAILURE");
		status = -1;
		return;
	}

	/**
	 * Process a JMSException and any associated inner exceptions.
	 *
	 * @param jmsex
	 */
	private static void processJMSException(JMSException jmsex) {
		System.out.println(jmsex);
		Throwable innerException = jmsex.getLinkedException();
		if (innerException != null) {
			System.out.println("Inner exception(s):");
		}
		while (innerException != null) {
			System.out.println(innerException);
			innerException = innerException.getCause();
		}
		return;
	}
	
	/**
	 * 
	 * @param message
	 * @param connString
	 * @param queueName
	 * @return 
	 * @throws MQException 
	 * @throws IOException 
	 */
	public static String putHeaderLessMessge(String message, String connString, String queueName, String qManager) throws MQException, IOException {
		String [] connDetails = connString.split(":");
		 String HOST = connDetails[0];
		 String CHANNEL = connDetails[1];
		 int PORT =	 Integer.parseInt(connDetails[2]);
		Hashtable<String, Object> props = new Hashtable<String, Object>();
        props.put(MQConstants.CHANNEL_PROPERTY, CHANNEL);
        props.put(MQConstants.PORT_PROPERTY, PORT);
        props.put(MQConstants.HOST_NAME_PROPERTY, HOST);
        
        MQQueueManager qMgr = null;
        
            qMgr = new MQQueueManager(qManager, props);
//            int openOptions = MQConstants.MQOO_OUTPUT | MQConstants.MQOO_INPUT_AS_Q_DEF| MQConstants.MQOO_BIND_NOT_FIXED;
            int openOptions = MQConstants.MQOO_OUTPUT | MQConstants.MQOO_BIND_NOT_FIXED;
//            int openOptions = MQC.MQOO_INQUIRE + MQC.MQOO_FAIL_IF_QUIESCING + MQC.MQOO_INPUT_SHARED+CMQC.MQOO_OUTPUT + CMQC.MQOO_FAIL_IF_QUIESCING; 
            MQQueue queue = qMgr.accessQueue(queueName, openOptions,null,null,null);
            MQPutMessageOptions pmo = new MQPutMessageOptions(); // default
            pmo.options = MQConstants.MQPMO_ASYNC_RESPONSE;
            // create message
            MQMessage mqMessage = new MQMessage();
            mqMessage.format = MQConstants.MQFMT_STRING;
            mqMessage.writeString(message);
            queue.put(mqMessage, pmo);
            queue.close();
            System.out.println(mqMessage);
            MQAsyncStatus asyncStatus = qMgr.getAsyncStatus();
             if (asyncStatus.putSuccessCount==1)
            	 return "put message to queue: "+queueName+" successfull";
			return "put message Failed";
        
        }
	

}