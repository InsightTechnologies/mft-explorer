package com.mqrest.topic;

import java.util.ArrayList;
import java.util.List;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import com.mqrest.MQService.MQUtilities;

import ch.qos.logback.core.net.SyslogOutputStream;

import com.ibm.msg.client.jms.JmsConnectionFactory;

public class MQTopicUtility {
	Connection connection = null;
	Destination destination = null;
	MessageConsumer consumer = null;

	byte[] byteData = null;
	List<String> messageBody = new ArrayList<String>();
	XmlToJsonConvertor conversion = new XmlToJsonConvertor();

	public List<String> topicConsumer(String topicName, String selector, String connString) throws JMSException

	{
		JmsConnectionFactory cf = MQUtilities.getConnectionFactory(connString);
		Connection connection = cf.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		System.out.println("connected suscccessfully");
		String slash = "/";
		String hash = "#";
		String topicString = topicName + slash + selector + slash + hash;
		MessageConsumer consumer = session.createConsumer(session.createTopic(topicString));
		connection.start();
		Message message = null;
		while (true) {
			// System.out.println(consumer);
			message = consumer.receiveNoWait();

			if (message != null) {
				// System.out.println(message);
				BytesMessage byteMessage = (BytesMessage) message;

				byteData = new byte[(int) byteMessage.getBodyLength()];
				byteMessage.readBytes(byteData);

				messageBody.add(new String(byteData));

			} else
				break;

		}
		List<String> convertedJsonString = conversion.xmlToJson(messageBody);
		System.out.println(convertedJsonString);
		session.close();
		consumer.close();
		connection.close();
		return convertedJsonString;
	}

}
