package com.mft.rest.api.utility;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mft.rest.beans.Transfer_Details;;


public class TransferCreationutil {

	
	public static Element buildMFTMonitorHeader(String requestType, String version, String xsd,
			Document document) throws ParserConfigurationException, IOException, TransformerException {

		Element firstElement = document.createElement(requestType);
		firstElement.setAttribute("version", version);
		firstElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		firstElement.setAttribute("xsi:noNamespaceSchemaLocation", xsd);
		document.appendChild(firstElement);
		return firstElement;
	}
	
	

    
}
