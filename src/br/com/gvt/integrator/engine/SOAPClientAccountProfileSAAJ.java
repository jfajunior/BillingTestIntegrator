package br.com.gvt.integrator.engine;


import java.io.IOException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.utils.Constants;
import br.com.gvt.integrator.utils.SoapUtils;
import br.com.gvt.integrator.utils.XmlUtils;




/**
 * This is a SOAP client class implemented with SAAJ.
 * SAAJ (SOAP with Attachments API for Java) is mainly used for dealing directly with SOAP Request/Response messages
 * which happens behind the scenes in any Web Service API. It allows the developers to directly send and receive soap
 * messages instead of using JAX-WS.
 * 
 * @author José Júnior
 *         GVT - 2015.03
 */
public class SOAPClientAccountProfileSAAJ {

	final static Logger _logger = Logger.getLogger(SOAPClientAccountProfileSAAJ.class);
	
	
	
	
	/**
	 * 
	 * Create the SOAP Request for the GetAccountProfile webservice.
	 * 
	 * @param accountId
	 *            The customer order number to search for.
	 * @return The SOAPMessage request.
	 * @throws SOAPException
	 * @throws IOException
	 */
	public static SOAPMessage createGetAccountProfileSOAPRequest(String accountId)
			throws SOAPException, IOException {
		// Create the SOAP message structure.
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
		
		// Get the SOAP envelope and add its namespaces.
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(Constants.CUSTOMER_ORDER_NS_PREFIX, Constants.CUSTOMER_ORDER_NS_URI);
		envelope.addNamespaceDeclaration(Constants.CUSTOMER_ORDER_IN_NS_PREFIX, Constants.CUSTOMER_ORDER_IN_NS_URI);
		
		// Get the SOAP Body and add and fill its elements.
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("GetBillingProfileInfo", Constants.CUSTOMER_ORDER_NS_PREFIX);
		
		SOAPElement soapBodyElemRequest = soapBodyElem.addChildElement("Request");
		
		SOAPElement soapBodyElemGetBillingProfileInfoIn = soapBodyElemRequest.addChildElement(
				"GetBillingProfileInfoIn", Constants.CUSTOMER_ORDER_IN_NS_PREFIX);
		
		SOAPElement soapBodyElemNumeroOrdem = soapBodyElemGetBillingProfileInfoIn.addChildElement(
				"IdConta", Constants.CUSTOMER_ORDER_IN_NS_PREFIX);
		
		// Remove MA prefix from account id.
		accountId = accountId.substring(2).trim();
		soapBodyElemNumeroOrdem.addTextNode(accountId);
		
//		SOAPElement soapBodyElemIdIntegracao = soapBodyElemGetBillingProfileInfoIn.addChildElement(
//				"IdIntegracao", CUSTOMER_ORDER_IN_NS_PREFIX);
//		soapBodyElemIdIntegracao.addTextNode("*");
		
		/*
		 * SOAP Message Request: 
		    <?xml version="1.0" encoding="UTF-8"?>
            <SOAP-ENV:Envelope
                xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
                xmlns:co="http://www.gvt.com.br/siebel/customerorder" xmlns:coi="http://www.gvt.com.br/siebel/customerorderin">
                <SOAP-ENV:Header/>
                <SOAP-ENV:Body>
                    <co:GetBillingProfileInfo>
                        <Request>
                            <coi:GetBillingProfileInfoIn>
                                <coi:IdConta>1-CI7-534</coi:IdConta>
                            </coi:GetBillingProfileInfoIn>
                        </Request>
                    </co:GetBillingProfileInfo>
                </SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
		 */
		
		// Set mime headers.
		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", Constants.SOAP_ACTION_FOR_GET_CUSTOMER_ORDER_BILL_PROFILE_INFO_OPERATION);
		
		// Save SOAP message changes.
		soapMessage.saveChanges();
		
		// Print the SOAP request.
		String soapRequestString = SoapUtils.getSoapMessageStringFromSoapMessage(soapMessage);
		soapRequestString = XmlUtils.format(soapRequestString);
		_logger.debug("SOAP Message Request: " + soapRequestString);
		
		return soapMessage;
	}
	
	
	
	
	/* *****************************************************************************************************************
	 * Entry point for the SAAJ - SOAP client Unit Testing.
	 * *****************************************************************************************************************
	 */
	public static void main(String args[]) {
		
		// Dummy data.
		String url = "http://10.41.254.48:8888/crm/siebel8/GetAccountProfile";
		String orderNumber = "1-CI7-534";
		
		try {
			// Create the request and execute SOAP call.
			SOAPMessage soapRequest = createGetAccountProfileSOAPRequest(orderNumber);
			SOAPMessage soapResponse = SoapUtils.executeSoapCall(url, soapRequest);
			
			// Print the SOAP response.
			String soapResponseString = SoapUtils.getSoapMessageStringFromSoapMessage(soapResponse);
			soapResponseString = XmlUtils.format(soapResponseString, true);
			_logger.debug("SOAP Message Response: " + soapResponseString);
		}
		catch (IOException | SOAPException e) {
			e.printStackTrace();
		}
	}
	
	
}
