package br.com.gvt.stubs;


import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

import br.com.gvt.integrator.utils.Network;




// wsdlLocation = "http://localhost:7001/Transformer/TransformerService?WSDL")
@WebServiceClient(name = "TransformerService", targetNamespace = "http://billingRetail.gvt.com.br/")
public class TransformerService extends Service {
	
	
	public TransformerService(String urlString) throws MalformedURLException {
		super(Network.getUrl(urlString), new QName("http://billingRetail.gvt.com.br/", "TransformerService"));
	}
	
	
	
	
	public TransformerService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	
	
	@WebEndpoint(name = "TransformerPort")
	public Transformer getTransformerPort() {
		return ((Transformer) super.getPort(new QName("http://billingRetail.gvt.com.br/", "TransformerPort"),
				Transformer.class));
	}
	
	
	
	
	@WebEndpoint(name = "TransformerPort")
	public Transformer getTransformerPort(WebServiceFeature[] features) {
		return ((Transformer) super.getPort(new QName("http://billingRetail.gvt.com.br/", "TransformerPort"),
				Transformer.class, features));
	}
}
