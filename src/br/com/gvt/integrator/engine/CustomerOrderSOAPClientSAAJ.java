package br.com.gvt.integrator.engine;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.utils.XmlUtils;





/**
 * This is a SOAP client class implemented with SAAJ.
 * SAAJ (SOAP with Attachments API for Java) is mainly used for dealing directly with SOAP Request/Response messages
 * which happens behind the scenes in any Web Service API. It allows the developers to directly send and receive soap
 * messages instead of using JAX-WS.
 * 
 * @author José Júnior
 *         GVT - 2013.06
 */
public class CustomerOrderSOAPClientSAAJ {
	
	private static final String CUSTOMER_ORDER_NS_PREFIX = "co";
	private static final String CUSTOMER_ORDER_NS_URI = "http://www.gvt.com.br/siebel/customerorder";
	
	private static final String CUSTOMER_ORDER_IN_NS_PREFIX = "coi";
	private static final String CUSTOMER_ORDER_IN_NS_URI = "http://www.gvt.com.br/siebel/customerorderin";
	
	private static final String SOAP_ACTION_FOR_GET_CUSTOMER_ORDER_BILL_INFO_OPERATION =
			"rpc/http://www.gvt.com.br/siebel/customerorder:GetCustomerOrderBillInfo";
	
	public static final String CHARSET_ENCODING = "UTF-8";
	

	final static Logger _logger = Logger.getLogger(CustomerOrderSOAPClientSAAJ.class);
	
	
	
	
	/**
	 * Returns the SOAP message String from a SOAP message object.
	 * 
	 * @param soapMessage
	 *            The object with the SOAP message.
	 * @return A string with the SOAP message text.
	 * @throws SOAPException
	 * @throws IOException
	 */
	public static String getSoapMessageStringFromSoapMessage(SOAPMessage soapMessage) throws SOAPException, IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		soapMessage.writeTo(outputStream);
		return new String(outputStream.toByteArray(), CHARSET_ENCODING);
	}
	
	
	
	
	/**
	 * Returns the SOAP message object from a SOAP message string.
	 * 
	 * @param soapMessageString The string with the SOAP message text.
	 * @return A SOAP message.
	 * @throws SOAPException
	 * @throws IOException
	 */
	public static SOAPMessage getSoapMessageFromSoapMessageString(String soapMessageString) throws SOAPException, IOException {
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage soapMessage = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(
						soapMessageString.getBytes(Charset.forName(CHARSET_ENCODING))));
		
		// Set mime headers.
		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", SOAP_ACTION_FOR_GET_CUSTOMER_ORDER_BILL_INFO_OPERATION);
		
		// Save SOAP message changes.
		soapMessage.saveChanges();

		return soapMessage;
	}
	
	
	
	
	/**
	 * 
	 * Create the SOAP Request for the GetCustomerOrderBillInfo webservice.
	 * 
	 * @param orderNumber
	 *            The customer order number to search for.
	 * @return The SOAPMessage request.
	 * @throws SOAPException
	 * @throws IOException
	 */
	public static SOAPMessage createGetCustomerOrderBillInfoSOAPRequest(String orderNumber)
			throws SOAPException, IOException {
		// Create the SOAP message structure.
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
		
		// Get the SOAP envelope and add its namespaces.
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(CUSTOMER_ORDER_NS_PREFIX, CUSTOMER_ORDER_NS_URI);
		envelope.addNamespaceDeclaration(CUSTOMER_ORDER_IN_NS_PREFIX, CUSTOMER_ORDER_IN_NS_URI);
		
		// Get the SOAP Body and add and fill its elements.
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("GetCustomerOrderBillInfo", CUSTOMER_ORDER_NS_PREFIX);
		
		SOAPElement soapBodyElemRequest = soapBodyElem.addChildElement("Request");
		
		SOAPElement soapBodyElemGetCustomerBillInfoIn = soapBodyElemRequest.addChildElement(
				"GetCustomerBillInfoIn", CUSTOMER_ORDER_IN_NS_PREFIX);
		
		SOAPElement soapBodyElemNumeroOrdem = soapBodyElemGetCustomerBillInfoIn.addChildElement(
				"NumeroOrdem", CUSTOMER_ORDER_IN_NS_PREFIX);
		soapBodyElemNumeroOrdem.addTextNode(orderNumber);
		
		SOAPElement soapBodyElemIdIntegracao = soapBodyElemGetCustomerBillInfoIn.addChildElement(
				"IdIntegracao", CUSTOMER_ORDER_IN_NS_PREFIX);
		soapBodyElemIdIntegracao.addTextNode("*");
		
		/*
		 * Constructed SOAP Request Message:
		    <?xml version="1.0" encoding="UTF-8"?>
			<env:Envelope xmlns:co="http://www.gvt.com.br/siebel/customerorder"
			    xmlns:coi="http://www.gvt.com.br/siebel/customerorderin" 
			    xmlns:env="http://schemas.xmlsoap.org/soap/envelope/">
			    <env:Header/>
			    <env:Body>
			        <co:GetCustomerOrderBillInfo>
			            <Request>
			                <coi:GetCustomerBillInfoIn>
			                    <coi:NumeroOrdem>8-238950742-1</coi:NumeroOrdem>
			                    <coi:IdIntegracao>*</coi:IdIntegracao>
			                </coi:GetCustomerBillInfoIn>
			            </Request>
			        </co:GetCustomerOrderBillInfo>
			    </env:Body>
			</env:Envelope>
		 */
		
		// Set mime headers.
		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", SOAP_ACTION_FOR_GET_CUSTOMER_ORDER_BILL_INFO_OPERATION);
		
		// Save SOAP message changes.
		soapMessage.saveChanges();
		
		// Print the SOAP request.
		String soapRequestString = getSoapMessageStringFromSoapMessage(soapMessage);
		soapRequestString = XmlUtils.format(soapRequestString);
		_logger.debug("SOAP Message Request: " + soapRequestString);
		
		return soapMessage;
	}
	
	
	
	
	/**
	 * Executes a SOAP call with the given SOAP message for the given URL.
	 * 
	 * @param url
	 *            The URL where to make the SOAP call.
	 * @param soapMessage
	 *            The SOAP message to send in the SOAP request.
	 * @return A SOAP message with the server response.
	 * @throws SOAPException
	 */
	public static SOAPMessage executeSoapCall(String url, SOAPMessage soapMessage) throws SOAPException {
		SOAPConnection soapConnection = null;
		SOAPMessage soapResponse = null;
		
		try {
			// Create SOAP connection.
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			soapConnection = soapConnectionFactory.createConnection();
			
			// Send SOAP message to server and get response.
			soapResponse = soapConnection.call(soapMessage, url);
		}
		catch (SOAPException e) {
			System.err.println("Error occurred while sending SOAP Request to Server.");
			throw e;
		}
		finally {
			// Close connection.
			try {
				if (soapConnection != null) {
					soapConnection.close();
				}
			}
			catch (SOAPException e) {
				System.err.println("Could not close SOAP connection.");
				e.printStackTrace();
			}
		}
		// Return server response.
		return soapResponse;
	}
	
	
	
	
	/* *****************************************************************************************************************
	 * Entry point for the SAAJ - SOAP client Unit Testing.
	 * *****************************************************************************************************************
	 */
	public static void main(String args[]) {
		
		// Dummy data.
		String url = "http://10.41.254.48:8888/crm/siebel8/getCustomerOrderBillInfo";
		String orderNumber = "8-238950742-1";
		
		try {
			// Create the request and execute SOAP call.
			SOAPMessage soapRequest = createGetCustomerOrderBillInfoSOAPRequest(orderNumber);
			SOAPMessage soapResponse = executeSoapCall(url, soapRequest);
			
			// Print the SOAP response.
			String soapResponseString = getSoapMessageStringFromSoapMessage(soapResponse);
			soapResponseString = XmlUtils.format(soapResponseString, true);
			_logger.debug("SOAP Message Response: " + soapResponseString);
		}
		catch (IOException | SOAPException e) {
			e.printStackTrace();
		}
	}
	
	
}
