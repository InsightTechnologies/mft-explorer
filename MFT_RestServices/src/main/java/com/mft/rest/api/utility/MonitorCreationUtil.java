package com.mft.rest.api.utility;

import java.io.IOException;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mft.rest.beans.Transfer_Details;

@Component
public class MonitorCreationUtil {
	@Autowired
	MFTbuildUtility mfTbuildUtility;
	
	/**
	 * 
	 * @param transfer_Details
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public String getTransferXMLString(Transfer_Details transfer_Details) throws ParserConfigurationException, IOException, TransformerException {
		Document transferDocument = getXmlDocument();
		Element monitorHeader = mfTbuildUtility.buildMFTMonitorHeader("monitor", "4.00", "http://www.ibm.com/xmlns/wmqfte/7.0.1/MonitorDefinition ./Monitor.xsd", "overwrite", transferDocument);
		mfTbuildUtility.buildMonitorName(monitorHeader, transfer_Details.getMonitor_Name(), transferDocument);
		mfTbuildUtility.buildPollInterval(transferDocument, transfer_Details.getPoll_Frequency(), 1, monitorHeader);
		addAgent(transferDocument, monitorHeader, transfer_Details.getSrc_Agent(), "agent");
		addResources(transferDocument, monitorHeader, transfer_Details);
		addTriggerMatch(transferDocument, monitorHeader, transfer_Details);
//		mfTbuildUtility.buildMFTRequestHeader("4.00", "FileTransfer.xsd", transferDocument);
		addTasks(transferDocument, monitorHeader, transfer_Details);
		Element OriginatorElement = transferDocument.createElement("originator");
		mfTbuildUtility.buildOriginator(transferDocument,OriginatorElement, transfer_Details.getSrc_HostName(), transfer_Details.getSrc_UserID());
		monitorHeader.appendChild(OriginatorElement);
		return formatXMLString(printDocument(transferDocument)).replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "").replace("xsi:xsi", "xsi");
		
	}
	
	/**
	 * Method to format the created xml as per the Monitor xsd format
	 * @param xmlString
	 * @return
	 */
	public String formatXMLString(String xmlString) {
		xmlString = xmlString.replace("<monitor", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><monitor:monitor");
		xmlString = xmlString.replace("</monitor>", "</monitor:monitor>");
		xmlString = xmlString.replace("schemaLocation=", "xsi:schemaLocation=");
		return xmlString = xmlString.replace("noNamespaceSchemaLocation", "xsi:noNamespaceSchemaLocation");
	}
	
	/**
	 * Method to create root element of the xml
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public Document getXmlDocument() throws ParserConfigurationException, IOException, TransformerException {
		DocumentBuilderFactory documentBuildFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuildFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		return document;
	}
	
	/**
	 * Utility method to create agent element
	 * @param document
	 * @param rootTag
	 * @param SAgent
	 * @param agent
	 */
	public void addAgent(Document document, Element rootTag, String SAgent, String agent) {
		Element agentElement = document.createElement(agent);
		agentElement.appendChild(document.createTextNode(SAgent));
		rootTag.appendChild(agentElement);

	}
	/**
	 * Utility method to create and add agent details elements
	 * @param document
	 * @param rootTag
	 * @param agent
	 * @param QMgr
	 * @param agentType
	 */
	public void addAgentDetails(Document document, Element rootTag, String agent, String QMgr,
			String agentType) {
		
		Element agentElement = document.createElement(agentType);
		agentElement.setAttribute("agent", agent);
		agentElement.setAttribute("QMgr", QMgr);
		rootTag.appendChild(agentElement);

	}
	
	/**
	 * Utillity Method for the creation of the resources element
	 * @param document
	 * @param rootTag
	 * @param transferData
	 */
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
	
	/**
	 * Utility Method for the creation of trigger conditions respective elements,
	 * @param document
	 * @param rootTag
	 * @param transferData
	 */
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
			if (transferData.getTrg_Condition() == "fileSize") {
				Element fSElement = document.createElement("compare");
				fSElement.setAttribute("operator", "&gt;=");
				fSElement.setAttribute("units", transferData.getFileSizeB());
				fSElement.appendChild(document.createTextNode(transferData.getPolls()));
				targetFTag.appendChild(fSElement);
			}
			if (transferData.getM_Pattern() != null ) {
				Element MPElement = document.createElement("pattern");
				MPElement.appendChild(document.createTextNode(transferData.getM_Pattern()));
				targetFTag.appendChild(MPElement);
			}
			if (transferData.getE_Pattern() != null ) {
				Element EPElement = document.createElement("exclude");
				EPElement.appendChild(document.createTextNode(transferData.getE_Pattern()));
				targetFTag.appendChild(EPElement);
			}
			conditionTag.appendChild(targetFTag);
		}

	}
	
	/**
	 * Utility method for the creation of reply queue element
	 * @param document
	 * @param rootTag
	 * @param transferData
	 */
	public void addReplyQueue(Document document, Element rootTag, Transfer_Details transferData) {
		if (transferData.getReply_Queue() != null) {
			Element replyTag = document.createElement("reply");
			replyTag.setAttribute("QMGR", transferData.getSrc_QueMgr());
			replyTag.appendChild(document.createTextNode(transferData.getReply_Queue()));
			rootTag.appendChild(replyTag);
		}
	}
	
	/**
	 * Utility Method for the creation of tasks element
	 * @param document
	 * @param rootTag
	 * @param transferData
	 * @throws DOMException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws TransformerException
	 */
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
		Element requestTag = mfTbuildUtility.buildMFTRequestHeader("request", "4.00", "FileTransfer.xsd", document);
		requestTag.appendChild(buildManagedTransfer(document, transferData, managedTag));
		transferTag.appendChild(requestTag);

	}
	/**
	 * 
	 * @param document
	 * @param transferData
	 * @param managedTag
	 * @return
	 */
	public Element buildManagedTransfer(Document document, Transfer_Details transferData, Element managedTag) {

		Element OriginatorElement = document.createElement("originator");
		managedTag.appendChild(OriginatorElement);
		mfTbuildUtility.buildOriginator(document,OriginatorElement, transferData.getSrc_HostName(), transferData.getSrc_UserID());
		if (transferData.getSchedule() != null) {
			mfTbuildUtility.buildSchedule(document, managedTag, transferData.getSchedule());
		}
		addAgentDetails(document, managedTag, transferData.getSrc_Agent(), transferData.getSrc_QueMgr(), "sourceAgent");
		addAgentDetails(document, managedTag, transferData.getTrgt_Agent(), transferData.getTrgt_QueMgr(),
				"destinationAgent");
		addTransferSet(document, managedTag, transferData);
		buildJobName(document, transferData, managedTag);
		return managedTag;

	}
	/**
	 * 
	 * @param document
	 * @param transferData
	 * @param managedTag
	 * @return
	 */
	public Element buildJobName(Document document, Transfer_Details transferData, Element managedTag) {
		Element jobTag = document.createElement("job");
		managedTag.appendChild(jobTag);
		Element jobNameTag = document.createElement("name");
		jobNameTag.appendChild(document.createTextNode(transferData.getJob_Name().trim()));
		jobTag.appendChild(jobNameTag);
		return jobTag;

	}

	
	/**
	 * Utility method for the creation of transfer set eliment
	 * @param document
	 * @param requestTag
	 * @param transferData
	 */
	public void addTransferSet(Document document, Element requestTag, Transfer_Details transferData) {
		Element transferSetTag = document.createElement("transferSet");
		if (transferData.getTransfer_Priority() != null) {
			transferSetTag.setAttribute("priority", transferData.getTransfer_Priority());
		}
		requestTag.appendChild(transferSetTag);
		if ((transferData.getPre_Src() != null) || transferData.getPost_Src() != null
				|| transferData.getPre_Dest() != null || transferData.getPost_Dest() != null) {

			Element metaDataSetTag = document.createElement("metaDataSet");
			transferSetTag.appendChild(metaDataSetTag);
			if (transferData.getPre_Src() != null) {
				mfTbuildUtility.builduserexits(document, metaDataSetTag, transferData.getPre_Src(), "PreSrc");
			}
			if (transferData.getPost_Src() != null) {
				mfTbuildUtility.builduserexits(document, metaDataSetTag, transferData.getPost_Src(), "PostSrc");
			}

			if (transferData.getPre_Dest() != null) {
				mfTbuildUtility.builduserexits(document, metaDataSetTag, transferData.getPre_Dest(), "PreDst");
			}

			if (transferData.getPost_Dest() != null) {
				mfTbuildUtility.builduserexits(document, metaDataSetTag, transferData.getPost_Dest(), "PostDst");
			}
		}

		Element itemTag = document.createElement("item");
		itemTag.setAttribute("checksumMethod", transferData.getChecksums());
		itemTag.setAttribute("mode", transferData.getTransfer_Mode());

		transferSetTag.appendChild(itemTag);
		Element sourceTag = document.createElement("source");
		if (transferData.getSrc_Disposition() == null) {
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
		Element destinagtionTag = document.createElement("destination");

		if (transferData.getDestination_Type().toLowerCase().equals("queue")) {
			destinagtionTag.setAttribute("type", transferData.getDestination_Type().toLowerCase());
			Element queueTag = document.createElement("queue");
			queueTag.setAttribute("persistent", "true");
			queueTag.setAttribute("setMqProps", "false");
			queueTag.appendChild(
					document.createTextNode(transferData.getTrgt_File() + "@" + transferData.getTrgt_QueMgr()));
			destinagtionTag.appendChild(queueTag);
		} else {
			if (transferData.getTrgt_Exists() == null) {
				destinagtionTag.setAttribute("exist", "error");
			} else {
				destinagtionTag.setAttribute("exist", transferData.getTrgt_Exists());
			}
			destinagtionTag.setAttribute("type", transferData.getDestination_Type().toLowerCase());
			itemTag.appendChild(destinagtionTag);

			Element fileTag = document.createElement("file");
			fileTag.appendChild(document.createTextNode(transferData.getTrgt_File()));
			destinagtionTag.appendChild(fileTag);
		}

	}

	/**
	 * Utility Method to print xml for reference , no business value
	 * @param doc
	 * @return
	 * @throws IOException
	 * @throws TransformerException
	 */
	public String printDocument(Document doc) throws IOException, TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		return writer.getBuffer().toString().replaceAll("\n|\r", "");
	}

}