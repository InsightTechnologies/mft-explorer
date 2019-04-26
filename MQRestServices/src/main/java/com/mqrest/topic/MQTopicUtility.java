package com.mqrest.topic;

import java.util.ArrayList;
import java.util.List;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.mqrest.MQService.MQUtilities;

@Configuration
public class MQTopicUtility {
	@Autowired
	XmlToJsonConvertor conversion;

	public List<String> topicConsumer(String topicName, String selector, String connString) throws JMSException{
		byte[] byteData = null;
		List<String> messageBody = new ArrayList<String>();
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
			message = consumer.receiveNoWait();

			if (message != null) {
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
