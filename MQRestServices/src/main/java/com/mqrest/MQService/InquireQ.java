package com.mqrest.MQService;

import java.util.Hashtable; 

import com.ibm.mq.*; 
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.headers.MQRFH2;
import com.ibm.mq.headers.pcf.PCFException;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;

public class InquireQ  
{ 
   public static void main(String args[])
   { 
      MQEnvironment.hostname = "192.168.1.121"; 
      MQEnvironment.channel = "SYSTEM.ADMIN.SVRCONN"; 
      MQEnvironment.port = 1414; 
      int openOptions = MQC.MQOO_INQUIRE + MQC.MQOO_FAIL_IF_QUIESCING + MQC.MQOO_INPUT_SHARED+CMQC.MQOO_OUTPUT + CMQC.MQOO_FAIL_IF_QUIESCING; 
      MQPutMessageOptions pmo = new MQPutMessageOptions();
      try{ 
         MQQueueManager qMgr = new MQQueueManager("DR12101"); 
         MQQueue queue = qMgr.accessQueue("DR.TEST.01", openOptions, null, null, null); 
         String msgData = "This is a test message for MQTest71"; 
         MQMessage sendmsg = new MQMessage();
         
         // Set the RFH2 Values
         MQRFH2 rfh2 = new MQRFH2();
         rfh2.setEncoding(CMQC.MQENC_NATIVE);
         rfh2.setCodedCharSetId(CMQC.MQCCSI_INHERIT);
         rfh2.setFormat(CMQC.MQFMT_STRING);
         rfh2.setFlags(0);
         rfh2.setNameValueCCSID(1208);
 
         /**
          * First, set the 'Message Service Domain' value.
          * Valid values for mcd.Msd are:
          * jms_none, jms_text, jms_bytes, jms_map, jms_stream & jms_object
          */
         rfh2.setFieldValue("mcd", "Msd", "jms_text");
 
         /**
          * Set the destination JMS queue name.
          */
         rfh2.setFieldValue("jms", "Dst", "queue:///"+queue);
 
         /**
          * Set user values.
          */
         rfh2.setFieldValue("usr", "SomeNum", 123);
         rfh2.setFieldValue("usr", "SomeText", "TEST");
 
         // Set the MQRFH2 structure to the message
         rfh2.write(sendmsg);
 
         // Write message data
         sendmsg.writeString(msgData);
 
         // Set MQMD values
         sendmsg.messageId = CMQC.MQMI_NONE;
         sendmsg.correlationId = CMQC.MQCI_NONE;
         sendmsg.messageType = CMQC.MQMT_DATAGRAM;
         // IMPORTANT: Set the format to MQRFH2 aka JMS Message.
         sendmsg.format = CMQC.MQFMT_RF_HEADER_2;
 
         // put the message on the queue
         queue.put(sendmsg, pmo);
         System.out.println("Message Data>>>" + sendmsg);
      
     
         
         System.out.println("The Maximum depth    : "+ queue.getMaximumDepth()); 
         System.out.println("The current depth    : "+ queue.getCurrentDepth()); 
         System.out.println("Input Process count  : "+ queue.getOpenInputCount()); 
         System.out.println("Output Process count : "+ queue.getOpenOutputCount()); 
      }catch(Exception e){ 
            System.out.println("Exception occured"); 
      } 
   } 
}