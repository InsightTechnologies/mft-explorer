package com.mqrest.MQService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.context.annotation.Configuration;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.ibm.mq.MQException;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.pcf.PCFException;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.wmq.compat.base.internal.MQC;
import com.mqrest.pcf.PCFQueueUtility;

@Configuration
public class MQServices {

	public List listLocalQueues(String connString) throws MQException, IOException {
		String[] connDetails = connString.split(":");
		String HOST = connDetails[0];
		String CHANNEL = connDetails[1];
		int PORT = Integer.parseInt(connDetails[2]);

		PCFMessageAgent agent = new PCFMessageAgent(HOST, PORT, CHANNEL);

		PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q_NAMES);

		request.addParameter(CMQC.MQCA_Q_NAME, "*");
		request.addParameter(CMQC.MQIA_Q_TYPE, MQC.MQQT_LOCAL);//

		PCFMessage[] responses = agent.send(request);
		String[] names = (String[]) responses[0].getParameterValue(CMQCFC.MQCACF_Q_NAMES);
		List queues = new ArrayList();
		for (int i = 0; i < names.length; i++) {
			queues.add(names[i].toString().trim());
		}
		return queues;

	}

	public String putMessage(String msg, String connString, String queueName) {
		MQOperations.putMessge(msg, connString, queueName);
		String suc = "Put Msg Successfully";
		return suc;

	}

	public String putHeaderMessage(Map<Object, Object> header, String msg, String connString, String queueName)
			throws DOMException, ParserConfigurationException, SAXException, IOException {
		MQOperations.putHeaderMessge(header, msg, connString, queueName);
		return "Message Put Successfull";

	}
	
	public String putHeaderLessMessage(String message,String connString, String queueName, String qManager) throws MQException, IOException {
		MQOperations.putHeaderLessMessge(message, connString, queueName, qManager);
		return "Message sent to "+queueName+" successfully";
	}

	public List<String> browseMessages(String qName, String connString, int rangeStart, int rangeEnd)
			throws JMSException

	{
		JmsConnectionFactory cf = MQUtilities.getConnectionFactory(connString);
		Connection connection = cf.createConnection();
		byte[] byteData = null;
		List<String> browsedMsgs = new ArrayList();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue(qName);
		QueueBrowser browser = session.createBrowser(queue);

		Enumeration e1 = browser.getEnumeration();
		int queueDepth = Collections.list(e1).size();
		Enumeration e = browser.getEnumeration();
		System.out.println(queueDepth);
		browsedMsgs.add(String.valueOf(queueDepth));
		int count = 1;
		for (int i = 0; i < queueDepth; i++)  {
			System.out.println(rangeStart);
			if (rangeStart > queueDepth)
				break;
			Message message2 = (Message) e.nextElement();

			if (count >= rangeStart) {
				if (message2.toString().contains("jms_text")) {
					browsedMsgs.add(message2.toString().substring(0, message2.toString().lastIndexOf("\n"))
							.replaceAll("JMS", "").replaceAll("_IBM_", "") + "\n" + message2.getBody(String.class));
				}
				if (message2.toString().contains("jms_bytes")) {
					browsedMsgs.add(message2.toString().replaceAll("JMS", "").replaceAll("_IBM_", ""));
//					
//					BytesMessage byteMessage = (BytesMessage) message2;
//					byteData = new byte[(int) byteMessage.getBodyLength()];
//					byteMessage.readBytes(byteData);
//					browsedMsgs.add(message2.toString().substring(0, message2.toString().lastIndexOf("\n"))
//							.replaceAll("JMS", "").replaceAll("_IBM_", "") + "\n" + new String(byteData));
				}
				if (count == rangeEnd) {
					break;
				}
			}
			count++;
			
		}
		System.out.println("messages retrieved");

		browser.close();

		return browsedMsgs;

	}

	public Map<String, Object> getQProperties(String connString, String queueName) throws PCFException, MQException, IOException {
		Map<String, Object> qProperties = new HashMap<String, Object>();
		qProperties = PCFQueueUtility.queueProps(connString, queueName, qProperties);
		return qProperties;
	}

}
