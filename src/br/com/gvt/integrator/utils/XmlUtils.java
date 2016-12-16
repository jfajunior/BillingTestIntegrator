package br.com.gvt.integrator.utils;


import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;




/**
 * XML helper class.
 * @author José Júnior
 *         GVT - 2014.08
 * 
 */
public class XmlUtils {
	private static final String XML_NAMESPACE_REGEX = " xmlns=\"[a-zA-Z:/.]*\"";
	private static final int XML_IDENT = 3;
	private static final int XML_LINE_WIDTH = 0;
	private static final String XML_ENCODING = "UTF-8";
	
	
	
	
	public static String getTextValue(Element element, String tagName) {
		NodeList nodeList = element.getElementsByTagName(tagName);
		if (nodeList != null && nodeList.getLength() > 0 && nodeList.item(0).getFirstChild() != null) {
			return nodeList.item(0).getFirstChild().getNodeValue();
		}
		
		return null;
	}
	
	
	
	
	public static boolean setTextValue(Element element, String tagName, String textValue) {
		NodeList nodeList = element.getElementsByTagName(tagName);
		if (nodeList != null && nodeList.getLength() > 0) {
			nodeList.item(0).getFirstChild().setNodeValue(textValue);
			return true;
		}
		
		return false;
	}
	
	
	
	
	public static List<Element> getElementsList(NodeList nodeList) {
		List<Element> elementsList = new ArrayList<>();
		
		if (nodeList != null && nodeList.getLength() > 0) {
			int nodeListLength = nodeList.getLength();
			for (int i = 0; i < nodeListLength; i++) {
				Element element = (Element) nodeList.item(i);
				elementsList.add(element);
			}
		}
		return elementsList;
	}
	
	
	
	
	/**
	 * Transforms an XML document into a formatted string.
	 * @param doc
	 *            The XML document.
	 * @return The XML document in a string representation.
	 */
	public static String documentToString(Document doc) {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, XML_ENCODING);
			
			StringWriter sw = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(sw));
			return sw.toString();
		}
		catch (Exception e) {
			throw new RuntimeException(Resources.INSTANCE.getString("error_converting_xml"), e);
		}
	}
	
	
	
	
	/**
	 * Converts an XML string to a Document object.
	 * @param xmlString
	 *            The XML string to be formatted.
	 * @return Document The Document object with the formatted XML.
	 * @throws IOException
	 */
	public static Document parseXmlString(String xmlString) throws IOException {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			InputSource inputSource = new InputSource(new StringReader(xmlString));
			Document document = documentBuilder.parse(inputSource);
			
			// Normalize document. Optional, but recommended! Read this:
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			document.getDocumentElement().normalize();
			
			return document;
		}
		catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		catch (SAXException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	/**
	 * Formats/Indents an XML string.
	 * 
	 * @param unformattedXml
	 *            - XML String
	 * @param removeNamespacePrefix
	 * @return Properly formatted XML String
	 * @throws IOException
	 */
	public static String format(String unformattedXml, boolean removeNamespacePrefix) throws IOException {
		String formattedXml = null;
		
		// Create a W3C Document Object Model from an unformatted XML string.
		Document document = parseXmlString(unformattedXml);
		
		// Create and set an output format object.
		OutputFormat outputFormat = new OutputFormat(document);
		outputFormat.setLineWidth(XML_LINE_WIDTH);
		outputFormat.setIndent(XML_IDENT);
		outputFormat.setIndenting(true);
		
		// Set encoding.
		outputFormat.setEncoding(XML_ENCODING);
		
		// Serialize the XML document.
		Writer stringWriter = new StringWriter();
		XMLSerializer serializer = new XMLSerializer(stringWriter, outputFormat);
		serializer.serialize(document);
		formattedXml = stringWriter.toString();
		
		// Remove namespace prefix.
		if (removeNamespacePrefix) {
			formattedXml = formattedXml.replaceAll(XmlUtils.XML_NAMESPACE_REGEX, "");
		}
		
		return formattedXml;
	}
	
	
	
	
	public static String format(String unformattedXml) throws IOException {
		return format(unformattedXml, false);
	}
	
	
	
	
	/* *********************************************************************************************
	 * Entry point for class Unit Testing.
	 * *********************************************************************************************
	 */
	public static void main(String args[]) {
		String book = "<soapenv:Envelope xmlns:soapenv=" +
				"\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cus=" +
				"\"http://www.gvt.com.br/siebel/customerorder\" xmlns:cus1=" +
				"\"http://www.gvt.com.br/siebel/customerorderin\"><soapenv:Header/>" +
				"<soapenv:Body><cus:GetCustomerOrderBillInfo><Request>" +
				"<cus1:GetCustomerBillInfoIn><!--Optional:-->" +
				"<cus1:NumeroOrdem>8-238950742-1</cus1:NumeroOrdem><!--1 or more repetitions:-->" +
				"<cus1:IdIntegracao>*</cus1:IdIntegracao></cus1:GetCustomerBillInfoIn></Request>" +
				"</cus:GetCustomerOrderBillInfo></soapenv:Body></soapenv:Envelope>";
		
		System.out.println("Before: " + book);
		try {
			book = XmlUtils.format(book);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("After:  " + book);
	}
	
}
