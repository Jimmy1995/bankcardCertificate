package com.common.util.security;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;

public class DocumentUtil {

	public static DocumentBuilder getDocBuilder()
			throws ParserConfigurationException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		docBuilderFactory.setNamespaceAware(true);
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		return docBuilder;
	}

	public static Document parseXmlData(String repData) throws Exception {
		DocumentBuilder docBuilder = getDocBuilder();
		Document doc = docBuilder
				.parse(IOUtils.toInputStream(repData, "UTF-8"));
		return doc;
	}

}
