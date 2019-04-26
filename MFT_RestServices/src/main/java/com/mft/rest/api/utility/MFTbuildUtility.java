package com.mft.rest.api.utility;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mft.rest.beans.Schedule_details;

@Component
public class MFTbuildUtility {
	@Autowired
	MonitorCreationUtil monitorCreationUtil;
	
	/**
	 * 
	 * @param mointorHeader
	 * @param monitorname
	 * @param document
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public void buildMonitorName(Element mointorHeader, String monitorname, Document document)
			throws ParserConfigurationException, IOException, TransformerException {
		Element monitorTag = document.createElement("name");
		monitorTag.appendChild(document.createTextNode(monitorname));
		mointorHeader.appendChild(monitorTag);
		
	}
	/**
	 * 
	 * @param monitortype
	 * @param version
	 * @param xsd
	 * @param overwrite
	 * @param document
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public Element buildMFTMonitorHeader(String monitortype, String version, String xsd, String overwrite,
			Document document) throws ParserConfigurationException, IOException, TransformerException {

		Element firstelement = document.createElement(monitortype);
		firstelement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		firstelement.setAttribute("xmlns:monitor", "http://www.ibm.com/xmlns/wmqfte/7.0.1/MonitorDefinition");
		firstelement.setAttribute("version", version);
		firstelement.setAttribute("xsi:schemaLocation", xsd);
		firstelement.setAttribute("overwrite", overwrite);
		document.appendChild(firstelement);
		return firstelement;
	}
	
	/**
	 * 
	 * @param document
	 * @param pollfreq
	 * @param pollintvl
	 * @param rootTag
	 */
	public void buildPollInterval(Document document, String pollfreq, int pollintvl, Element rootTag) {

		Element PollElement = document.createElement("pollInterval");
		PollElement.setAttribute("units", pollfreq);
		PollElement.appendChild(document.createTextNode(Integer.toString(pollintvl)));
		rootTag.appendChild(PollElement);
	}
	/**
	 * 
	 * @param document
	 * @param OriginatorTag
	 * @param hName
	 * @param userId
	 */
	public void buildOriginator(Document document,Element OriginatorTag, String hName, String userId) {

		Element NewHostTag = document.createElement("hostName");
		NewHostTag.appendChild(document.createTextNode(hName));
		Element NewUserTag = document.createElement("userID");
		NewUserTag.appendChild(document.createTextNode(userId));
		OriginatorTag.appendChild(NewHostTag);
		OriginatorTag.appendChild(NewUserTag);

	}
	/**
	 * 
	 * @param requestType
	 * @param version
	 * @param xsd
	 * @param document
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public Element buildMFTRequestHeader(String requestType, String version, String xsd, Document document)
			throws ParserConfigurationException, IOException, TransformerException {
		Element requestTag = document.createElement(requestType);
		requestTag.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		requestTag.setAttribute("version", version);
		requestTag.setAttribute("xsi:noNamespaceSchemaLocation", xsd);
		return requestTag;
	}

	/**
	 * 
	 * @param document
	 * @param OriginatorNode
	 * @param schedule
	 */
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

		if (schedule.getEndtime() != null || !schedule.getEndtime().isEmpty()) {
			Element ExpireTimeTag = document.createElement("expireTime");
			ExpireTimeTag.appendChild(document.createTextNode(schedule.getEndtime()));
			repeatTag.appendChild(ExpireTimeTag);
		}
		ScheduleElement.appendChild(repeatTag);

	}
	/**
	 * 
	 * @param document
	 * @param metaDataSetTag
	 * @param metaDetaInnerText
	 * @param metaDataValue
	 */
	public void builduserexits(Document document, Element metaDataSetTag, String metaDetaInnerText,
			String metaDataValue) {

		Element metaDataTag = document.createElement("metaData");
		metaDataTag.setAttribute("key", metaDataValue);
		metaDataTag.appendChild(document.createTextNode(metaDetaInnerText));
		metaDataSetTag.appendChild(metaDataTag);

	}
}
