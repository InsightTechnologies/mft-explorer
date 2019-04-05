package com.mqrest.pcf;

import java.io.IOException;
import java.util.Map;

import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.headers.pcf.PCFException;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;
public class PCFQueueUtility {
	

@SuppressWarnings("deprecation")
public static Map<String,Object> queueProps(String ConnectionString,String qName,Map<String, Object> qProperties) throws com.ibm.mq.pcf.PCFException, MQException, IOException
{  
	String[] connDetails=ConnectionString.split(":");
	String host = connDetails[0];
	String channel = connDetails[1];
	int port=Integer.valueOf(connDetails[2]);
	 PCFMessageAgent agent=new PCFMessageAgent(host,port,channel); 
     PCFMessage request=new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q_STATUS); 
     request.addParameter(CMQC.MQCA_Q_NAME,qName); 
    request.addParameter (CMQC.MQIA_Q_TYPE, MQC.MQQT_LOCAL); 
     

     PCFMessage[] responses = agent.send(request); 
     int count=responses[0].getParameterCount(); 
     System.out.println("ReplyQueue : " + agent.replyQueueName) ; 

     String lastGetTime=null; 
     String lastPutTIme=null; 
     
    
      
     
     System.out.println(responses[0].getParameterValue(CMQC.MQIA_CURRENT_Q_DEPTH)+" len :"+responses.length);
     for (int i=0; i < responses.length ; i++ ){ 
    	 qProperties.put("Max Depth :", responses[0].getParameterValue(CMQC.MQIA_MAX_Q_DEPTH));
    	 qProperties.put("Open Input :", responses[0].getParameterValue(CMQC.MQIA_OPEN_INPUT_COUNT));
    	 qProperties.put("Open Output :", responses[0].getParameterValue(CMQC.MQIA_OPEN_OUTPUT_COUNT));
    	 
    	 qProperties.put("Current Depth :", responses[0].getParameterValue(CMQC.MQIA_CURRENT_Q_DEPTH));
    	 qProperties.put("Queue Type :", responses[0].getParameterValue(CMQC.MQIA_Q_TYPE));
    	 qProperties.put("ACCTQ :", responses[0].getParameterValue(CMQC.MQIA_ACCOUNTING_Q));
    	 
    	 qProperties.put("BOQNAME :", responses[0].getParameterValue(CMQC.MQCA_BACKOUT_REQ_Q_NAME));
    	 qProperties.put("CREATION DATE :", responses[0].getParameterValue(CMQC.MQCA_CREATION_DATE));
    	 qProperties.put("CREATION TIME:", responses[0].getParameterValue(CMQC.MQCA_CREATION_TIME));
    	 
    	 lastGetTime=(String) responses[i].getParameterValue(CMQCFC.MQCACF_LAST_GET_TIME); 
        lastPutTIme=(String) responses[i].getParameterValue(CMQCFC.MQCACF_LAST_PUT_TIME); 
        qName=(String) responses[i].getParameterValue(CMQC.MQCA_Q_NAME); 
         
        System.out.println(qName+" "+lastPutTIme+" "+lastGetTime);       
       System.out.println(qProperties);
     }
     return qProperties;
}


}
