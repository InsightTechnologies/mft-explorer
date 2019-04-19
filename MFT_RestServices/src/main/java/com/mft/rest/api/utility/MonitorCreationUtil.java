package com.mft.rest.api.utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.context.annotation.Configuration;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mft.rest.beans.Schedule_details;
import com.mft.rest.beans.Transfer_Details;

@Configuration
public class MonitorCreationUtil {
	
	public String getTransferXMLString(Transfer_Details transferDetails) throws ParserConfigurationException, IOException, TransformerException {
		Document transferDocument = getXmlDocument();
		Element monitorHeader = buildMFTMonitorHeader("monitor", "1.0", "xsd", "overwrite", transferDocument);
		buildMonitorName(monitorHeader, "monitorname123", transferDocument);
		buildPollInterval(transferDocument, "minutes", 1, monitorHeader);
		addAgent(transferDocument, monitorHeader, "SAgent", "agent");
		addResources(transferDocument, monitorHeader, transferDetails);
		addTriggerMatch(transferDocument, monitorHeader, transferDetails);
		addTasks(transferDocument, monitorHeader, transferDetails);
		Element OriginatorElement = transferDocument.createElement("originator");
		buildOriginator(transferDocument,OriginatorElement, transferDetails.getSrc_HostName(), transferDetails.getSrc_UserID());
		monitorHeader.appendChild(OriginatorElement);
		return formatXMLString(printDocument(transferDocument));
		
	}

	public String formatXMLString(String xmlString) {
		xmlString = xmlString.replace("<monitor", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><monitor:monitor");
		xmlString = xmlString.replace("</monitor>", "</monitor:monitor>");
		xmlString = xmlString.replace("schemaLocation=", "xsi:schemaLocation=");
		return xmlString = xmlString.replace("noNamespaceSchemaLocation", "xsi:noNamespaceSchemaLocation");
	}
	public Document getXmlDocument() throws ParserConfigurationException, IOException, TransformerException {
		DocumentBuilderFactory documentBuildFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuildFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		return document;
	}

	public void buildMonitorName(Element mointorHeader, String monitorname, Document document)
			throws ParserConfigurationException, IOException, TransformerException {
		Element monitorTag = document.createElement("name");
		monitorTag.appendChild(document.createTextNode(monitorname));
		mointorHeader.appendChild(monitorTag);
		
	}

	public Element buildMFTMonitorHeader(String monitortype, String version, String xsd, String overwrite,
			Document document) throws ParserConfigurationException, IOException, TransformerException {

		Element firstelement = document.createElement(monitortype);
		firstelement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		firstelement.setAttribute("version", version);
		firstelement.setAttribute("xmlns:monitor", "http://www.ibm.com/xmlns/wmqfte/7.0.1/MonitorDefinition");
		firstelement.setAttribute("xsi:schemaLocation", xsd);
		firstelement.setAttribute("overwrite", overwrite);
		document.appendChild(firstelement);
		return firstelement;
	}

	public void buildPollInterval(Document document, String pollfreq, int pollintvl, Element rootTag) {

		Element PollElement = document.createElement("pollInterval");
		PollElement.setAttribute("units", pollfreq);
		PollElement.appendChild(document.createTextNode(Integer.toString(pollintvl)));
		rootTag.appendChild(PollElement);
	}

	public void addAgent(Document document, Element rootTag, String SAgent, String agent) {
		Element agentElement = document.createElement(agent);
		agentElement.appendChild(document.createTextNode(SAgent));
		rootTag.appendChild(agentElement);

	}

	public void addAgentDetails(Document document, Element rootTag, String agent, String QMgr,
			String agentType) {
		
		Element agentElement = document.createElement(agentType);
		agentElement.setAttribute("agent", agent);
		agentElement.setAttribute("QMgr", QMgr);
		rootTag.appendChild(agentElement);

	}

	public void addResources(Document document, Element rootTag, Transfer_Details transferData) {
		
		Element resourceTag = document.createElement("resources");
		rootTag.appendChild(resourceTag);
		String strResourceType = transferData.getResource_Type().toLowerCase();
		Element resourceTypeElement = document.createElement(strResourceType);
		if (strResourceType == "directory") {
			resourceTypeElement.setAttribute("recursionLevel", "0");
		}
		resourceTypeElement.appendChild(document.createTextNode(transferData.getResource_Info()));
		resourceTag.appendChild(resourceTypeElement);

	}

	public void addTriggerMatch(Document document, Element rootTag, Transfer_Details transferData) {
		Element triggerMatchTag = document.createElement("triggerMatch");
		rootTag.appendChild(triggerMatchTag);
		Element conditionsTag = document.createElement("conditions");
		triggerMatchTag.appendChild(conditionsTag);
		Element allOfTag = document.createElement("allOf");
		conditionsTag.appendChild(allOfTag);
		Element conditionTag = document.createElement("condition");
		allOfTag.appendChild(conditionTag);
		if (transferData.getResource_Type().toLowerCase() == "queue") {
			Element targetQueueTag = document.createElement(transferData.getTrg_Condition());
			conditionTag.appendChild(targetQueueTag);
		} else {
			Element targetFTag = document.createElement(transferData.getTrg_Condition());
			if (transferData.getTrg_Condition() == "fileSizeSame") {
				targetFTag.setAttribute("polls", transferData.getPolls());
			}
			conditionTag.appendChild(targetFTag);

			if (transferData.getTrg_Condition() == "fileSize") {
				Element fSElement = document.createElement("compare");
				fSElement.setAttribute("operator", "&gt;=");
				fSElement.setAttribute("units", transferData.getFileSizeB());
				fSElement.appendChild(document.createTextNode(transferData.getPolls()));
				targetFTag.appendChild(fSElement);
			}
			if (transferData.getM_Pattern() == null || transferData.getM_Pattern().isEmpty()) {
				Element MPElement = document.createElement("pattern");
				MPElement.appendChild(document.createTextNode(transferData.getM_Pattern()));
				targetFTag.appendChild(MPElement);
			}
			if (transferData.getE_Pattern() == null || transferData.getM_Pattern().isEmpty()) {
				Element EPElement = document.createElement("exclude");
				EPElement.appendChild(document.createTextNode(transferData.getE_Pattern()));
				targetFTag.appendChild(EPElement);
			}
		}

	}

	public void addReplyQueue(Document document, Element rootTag, Transfer_Details transferData) {
		if (transferData.getReply_Queue() == null || transferData.getReply_Queue().isEmpty()) {
			Element replyTag = document.createElement("reply");
			replyTag.setAttribute("QMGR", transferData.getSrc_QueMgr());
			replyTag.appendChild(document.createTextNode(transferData.getReply_Queue()));
			rootTag.appendChild(replyTag);
		}
	}

	public void addTasks(Document document, Element rootTag, Transfer_Details transferData)
			throws DOMException, ParserConfigurationException, IOException, TransformerException {

		Element tasksTag = document.createElement("tasks");
		rootTag.appendChild(tasksTag);
		Element taskTag = document.createElement("task");
		tasksTag.appendChild(taskTag);
		Element nameTag = document.createElement("name");
		taskTag.appendChild(nameTag);
		Element transferTag = document.createElement("transfer");
		taskTag.appendChild(transferTag);
		Element managedTag = document.createElement("managedTransfer");
		transferTag.appendChild(buildMFTRequestHeader("request", "6.00", "FileTransfer.xsd", document)
				.appendChild(buildManagedTransfer(document, transferData, managedTag)));

	}

	public void buildOriginator(Document document,Element OriginatorTag, String hName, String userId) {

//		Element OriginatorTag = document.createElement("originator");
//		OriginatorElement.appendChild(OriginatorTag);
		Element NewHostTag = document.createElement("hostName");
		NewHostTag.appendChild(document.createTextNode(hName));
		Element NewUserTag = document.createElement("userID");
		NewUserTag.appendChild(document.createTextNode(userId));
		OriginatorTag.appendChild(NewHostTag);
		OriginatorTag.appendChild(NewUserTag);

	}

	public Element buildMFTRequestHeader(String requestType, String version, String xsd, Document document)
			throws ParserConfigurationException, IOException, TransformerException {
		Element requestTag = document.createElement("request");
		requestTag.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		requestTag.setAttribute("version", version);
		requestTag.setAttribute("xsi:noNamespaceSchemaLocation", xsd);
		return requestTag;
	}

	public Element buildManagedTransfer(Document document, Transfer_Details transferData, Element managedTag) {

		Element OriginatorElement = document.createElement("originator");
		managedTag.appendChild(OriginatorElement);
		buildOriginator(document,OriginatorElement, transferData.getSrc_HostName(), transferData.getSrc_UserID());
		if (transferData.getSchedule() != null) {
			buildSchedule(document, managedTag, transferData.getSchedule());
		}
		addAgentDetails(document, managedTag, transferData.getSrc_Agent(), transferData.getSrc_QueMgr(), "sourceAgent");
		addAgentDetails(document, managedTag, transferData.getTrgt_Agent(), transferData.getTrgt_QueMgr(),
				"destinationAgent");
		addTransferSet(document, managedTag, transferData);
		buildJobName(document, transferData, managedTag);
		return managedTag;

	}

	public Element buildJobName(Document document, Transfer_Details transferData, Element managedTag) {
		Element jobTag = document.createElement("job");
		managedTag.appendChild(jobTag);
		Element jobNameTag = document.createElement("name");
		jobNameTag.appendChild(document.createTextNode(transferData.getJob_Name().trim()));
		jobTag.appendChild(jobNameTag);
		return jobTag;

	}

	public void buildSchedule(Document document, Element OriginatorNode, Schedule_details schedule) {
		Element ScheduleElement = document.createElement("schedule");
		OriginatorNode.appendChild(ScheduleElement);

		Element submitTag = document.createElement("submit");
		submitTag.setAttribute("timebase", schedule.getTimebase());
		// submitTag.SetAttribute("timezone", MFTCommon.GetDefaultTimeZoneName());
		submitTag.setAttribute("timezone", "current time zone"); // need to update with time zone
		submitTag.appendChild(document.createTextNode(schedule.getStarttime()));
		ScheduleElement.appendChild(submitTag);
		Element repeatTag = document.createElement("repeat");
		Element FrequencyTag = document.createElement("frequency");
		FrequencyTag.setAttribute("interval", schedule.getFrequency());
		FrequencyTag.appendChild(document.createTextNode(schedule.getInterval()));
		repeatTag.appendChild(FrequencyTag);

		if (schedule.getEndtime() == null || schedule.getEndtime().isEmpty()) {
			Element ExpireTimeTag = document.createElement("expireTime");
			ExpireTimeTag.appendChild(document.createTextNode(schedule.getEndtime()));
			repeatTag.appendChild(ExpireTimeTag);
		}
		ScheduleElement.appendChild(repeatTag);

	}

	public void addTransferSet(Document document, Element requestTag, Transfer_Details transferData) {
		Element transferSetTag = document.createElement("transferSet");
		if (transferData.getTrans_Priority() != null) {
			transferSetTag.setAttribute("priority", transferData.getTrans_Priority());
		}
		requestTag.appendChild(transferSetTag);
		if ((transferData.getPre_Src() != null) || transferData.getPost_Src() != null
				|| transferData.getPre_Dest() != null || transferData.getPost_Dest() != null) {

			Element metaDataSetTag = document.createElement("metaDataSet");
			transferSetTag.appendChild(metaDataSetTag);
			if (transferData.getPre_Src() != null) {
				builduserexits(document, metaDataSetTag, transferData.getPre_Src(), "PreSrc");
			}
			if (transferData.getPost_Src() != null) {
				builduserexits(document, metaDataSetTag, transferData.getPost_Src(), "PostSrc");
			}

			if (transferData.getPre_Dest() != null) {
				builduserexits(document, metaDataSetTag, transferData.getPre_Dest(), "PreDst");
			}

			if (transferData.getPost_Dest() != null) {
				builduserexits(document, metaDataSetTag, transferData.getPost_Dest(), "PostDst");
			}
		}

		Element itemTag = document.createElement("item");
		itemTag.setAttribute("checksumMethod", transferData.getChecksums());
		itemTag.setAttribute("mode", transferData.getTrans_Mode());

		transferSetTag.appendChild(itemTag);
		Element sourceTag = document.createElement("source");
		if (transferData.getSrc_Disposition() == null || transferData.getSrc_Disposition().isEmpty()) {
			sourceTag.setAttribute("disposition", "leave");
		} else {
			sourceTag.setAttribute("disposition", transferData.getSrc_Disposition());
		}

		sourceTag.setAttribute("recursive", "false");
		if (transferData.getResource_Type().toLowerCase().equals("queue")) {
			sourceTag.setAttribute("type", "queue");
		}

		itemTag.appendChild(sourceTag);

		if (transferData.getResource_Type().toLowerCase().equals("queue")) {
			Element queueTag = document.createElement("queue");
			queueTag.setAttribute("useGroups", "false");
			if (transferData.getAction().trim().equals("transfer")) {
				queueTag.appendChild(
						document.createTextNode(transferData.getResource_Info() + "@" + transferData.getTrgt_QueMgr()));

			} else {
				queueTag.appendChild(document.createTextNode("${QueueName}@" + transferData.getTrgt_QueMgr()));
			}

			sourceTag.appendChild(queueTag);
		} else {

			Element fileTag = document.createElement("file");
			fileTag.appendChild(document.createTextNode(transferData.getSrc_File()));
			sourceTag.appendChild(fileTag);

		}
		///////////////// continue from here////////////////
		Element destinagtionTag = document.createElement("destination");

		if (transferData.getDest_Type().toLowerCase().equals("queue")) {
			destinagtionTag.setAttribute("type", transferData.getDest_Type().toLowerCase());
			Element queueTag = document.createElement("queue");
			queueTag.setAttribute("persistent", "true");
			queueTag.setAttribute("setMqProps", "false");
			queueTag.appendChild(
					document.createTextNode(transferData.getTrgt_File() + "@" + transferData.getTrgt_QueMgr()));
			destinagtionTag.appendChild(queueTag);
		} else {
			if (transferData.getTrgt_Exists() == null || transferData.getTrgt_Exists().isEmpty()) {
				destinagtionTag.setAttribute("exist", "error");
			} else {
				destinagtionTag.setAttribute("exist", transferData.getTrgt_Exists());
			}
			destinagtionTag.setAttribute("type", transferData.getDest_Type().toLowerCase());
			itemTag.appendChild(destinagtionTag);

			Element fileTag = document.createElement("file");
			fileTag.appendChild(document.createTextNode(transferData.getTrgt_File()));
			destinagtionTag.appendChild(fileTag);
		}

	}

	public void builduserexits(Document document, Element metaDataSetTag, String metaDetaInnerText,
			String metaDataValue) {

		Element metaDataTag = document.createElement("metaData");
		metaDataTag.setAttribute("key", metaDataValue);
		metaDataTag.appendChild(document.createTextNode(metaDetaInnerText));
		metaDataSetTag.appendChild(metaDataTag);

	}

	/*
	 * utility to print xml for reference , no business value
	 */

	public String printDocument(Document doc) throws IOException, TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		StringWriter writer = new StringWriter();
//		transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(out, "UTF-8")));
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		return writer.getBuffer().toString().replaceAll("\n|\r", "");
	}

}