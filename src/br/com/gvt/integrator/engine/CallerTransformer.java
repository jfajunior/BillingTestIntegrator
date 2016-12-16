package br.com.gvt.integrator.engine;


import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import br.com.gvt.stubs.BillingOrder;
import br.com.gvt.stubs.Exception_Exception;
import br.com.gvt.stubs.OrdemData;
import br.com.gvt.stubs.Transforme;
import br.com.gvt.stubs.Transformer;
import br.com.gvt.stubs.TransformerService;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class CallerTransformer {
	
	private static final String CONTEXT_PATH = "br.com.gvt.stubs";
	
	private Transformer _transformer;
	
	
	
	
	public CallerTransformer(String urlTransformer) throws JAXBException, MalformedURLException {
		_transformer = new TransformerService(new URL(urlTransformer), 
				new QName("http://billingRetail.gvt.com.br/", "TransformerService")).getTransformerPort();
	}
	
	
	
	
	private static OrdemData loadCrmData(String source) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(CONTEXT_PATH);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
		
		StringReader reader = new StringReader(source);
		JAXBElement<?> element = (JAXBElement<?>) unmarshaller.unmarshal(reader);
		
		Transforme transforme = (Transforme) element.getValue();
		
		return transforme.getCrmData();
	}
	
	
	
	
	/**
	 * Call Transformer with the given request.
	 * @param request
	 *            The request to send to transform.
	 * @return The resulting billing order after the transformation.
	 * @throws JAXBException
	 * @throws Exception_Exception
	 */
	public BillingOrder transform(String request) throws JAXBException, Exception_Exception {
		OrdemData ordem = loadCrmData(request);
		return _transformer.transforme(ordem);
	}
	
	
}
