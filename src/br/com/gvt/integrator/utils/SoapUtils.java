package br.com.gvt.integrator.utils;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;




public class SoapUtils {
	
	
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
		return new String(outputStream.toByteArray(), Constants.CHARSET_ENCODING);
	}
	
	
	
	
	/**
	 * Returns the SOAP message object from a SOAP message string.
	 * 
	 * @param soapMessageString
	 *            The string with the SOAP message text.
	 * @return A SOAP message.
	 * @throws SOAPException
	 * @throws IOException
	 */
	public static SOAPMessage getSoapMessageFromSoapMessageString(String soapMessageString, String soapAction)
			throws SOAPException, IOException {
		
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage soapMessage = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(
				soapMessageString.getBytes(Charset.forName(Constants.CHARSET_ENCODING))));
		
		// Set mime headers.
		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapAction);
		
		// Save SOAP message changes.
		soapMessage.saveChanges();
		
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
	
	
}
