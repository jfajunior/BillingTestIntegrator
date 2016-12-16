package br.com.gvt.stubs;


import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

import br.com.gvt.integrator.utils.Network;




// wsdlLocation = "http://localhost:7001/BillingAdapter/BillingAdapterService?WSDL")
@WebServiceClient(name = "BillingAdapterService", targetNamespace = "http://adapter.billing.gvt.com/")
public class BillingAdapterService extends Service {
	
	
	public BillingAdapterService(String urlString) throws MalformedURLException {
		super(Network.getUrl(urlString), new QName("http://adapter.billing.gvt.com/", "BillingAdapterService"));
	}
	
	
	
	
	public BillingAdapterService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	
	
	@WebEndpoint(name = "BillingAdapterPort")
	public BillingAdapter getBillingAdapterPort() {
		return ((BillingAdapter) super.getPort(new QName("http://adapter.billing.gvt.com/", "BillingAdapterPort"),
				BillingAdapter.class));
	}
	
	
	
	
	@WebEndpoint(name = "BillingAdapterPort")
	public BillingAdapter getBillingAdapterPort(WebServiceFeature[] features) {
		return ((BillingAdapter) super.getPort(new QName("http://adapter.billing.gvt.com/", "BillingAdapterPort"),
				BillingAdapter.class, features));
	}
	
	
}
