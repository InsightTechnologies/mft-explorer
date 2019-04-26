package com.mqrest.pcf;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.headers.pcf.PCFException;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;
import com.ibm.msg.client.wmq.compat.base.internal.MQC;
import com.mqrest.MQService.LocalQueues;

@Configuration
public class PCFLocalQueues {

	public static List<LocalQueues> getLocalQueues(String connString, String sysQueue) throws MQException, IOException {
		List<LocalQueues> localQueues = new ArrayList<LocalQueues>();
		String[] connDetails = connString.split(":");
		String HOST = connDetails[0];
		String CHANNEL = connDetails[1];
		int PORT = Integer.parseInt(connDetails[2]);
		PCFMessageAgent agent = new PCFMessageAgent(HOST, PORT, CHANNEL);
		// PCFMessageAgent agent = new PCFMessageAgent ("DR12101");

		System.out.println(agent.getQManagerName());
		PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q_NAMES);
		request.addParameter(CMQC.MQCA_Q_NAME, "*");
		request.addParameter(CMQC.MQIA_Q_TYPE, MQC.MQQT_LOCAL);//

		PCFMessage[] responses = agent.send(request);
		String[] names = (String[]) responses[0].getParameterValue(CMQCFC.MQCACF_Q_NAMES);
		MQQueueManager qMgr = getQmngr(agent.getQManagerName(), HOST, PORT, CHANNEL);
		for (int i = 0; i < names.length; i++) {
			LocalQueues lqueues = new LocalQueues();
			System.out.println("Queue: " + i + " " + names[i]);
			if (!(names[i].contains("AMQ.")) &&(sysQueue.equalsIgnoreCase("normal")) && !(names[i].contains("SYSTEM"))) {
				localQueues.add(getQueueDetails(lqueues, qMgr, names[i].trim()));
			}else if ((sysQueue.equalsIgnoreCase("SYSTEM")) && names[i].contains("SYSTEM")){
				localQueues.add(getQueueDetails(lqueues, qMgr, names[i].trim()));
			}

		}
		return localQueues;
	}

	public static LocalQueues getQueueDetails(LocalQueues lqueues, MQQueueManager qmngr, String queuName)
			throws MQException {

		// int openOptions = MQC.MQOO_INQUIRE + MQC.MQOO_FAIL_IF_QUIESCING +
		// MQC.MQOO_INPUT_SHARED+CMQC.MQOO_OUTPUT + CMQC.MQOO_FAIL_IF_QUIESCING;
		int openOptions = MQC.MQOO_INQUIRE;

		MQPutMessageOptions pmo = new MQPutMessageOptions();
		MQQueue queue = qmngr.accessQueue(queuName, openOptions, null, null, null);
		lqueues.setQueueName(queuName);
		lqueues.setInputCount(queue.getOpenInputCount());
		lqueues.setOutputCount(queue.getOpenOutputCount());
		lqueues.setQueueDepth(queue.getCurrentDepth());
		lqueues.setQueueMaxDepth(queue.getMaximumDepth());

		return lqueues;
	}

	public static MQQueueManager getQmngr(String qmngr, String HOST, int PORT, String CHANNEL) throws MQException {
		MQEnvironment.hostname = HOST;
		MQEnvironment.channel = CHANNEL;
		MQEnvironment.port = PORT;
		MQQueueManager qMgr = new MQQueueManager(qmngr);
		return qMgr;
	}
}
