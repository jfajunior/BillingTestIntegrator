package br.com.gvt.stubs;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;




@XmlRootElement(name = "BillingOrder")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillingOrder", propOrder = { "services", "accounts", "components", "corridors", "fidelitys", "exception", "hasReRate",
		"sLog", "erro" })
public class BillingOrder implements Serializable, Cloneable {
	private static final long serialVersionUID = -6919673154639073524L;
	
	@XmlElement(name = "Services", namespace = "")
	protected List<Service> services;
	
	@XmlElement(name = "Accounts", namespace = "")
	protected List<Account> accounts;
	
	@XmlElement(name = "Components", namespace = "")
	protected List<Component> components;
	
	@XmlElement(name = "Corridors", namespace = "")
	protected List<Corridor> corridors;
	
	@XmlElement(name = "Fidelitys", namespace = "")
	protected List<Fidelity> fidelitys;
	
	@XmlElement(name = "Exception", namespace = "")
	protected String exception;
	
	@XmlElement(name = "HasReRate", namespace = "")
	private String hasReRate;
	
	@XmlElement(name = "SLog", namespace = "")
	protected String sLog;
	
	@XmlElement(name = "Erro", namespace = "")
	protected boolean erro;
	
	@XmlTransient
	private String orderId;
	
	
	
	
	public String getOrderId() {
		return orderId;
	}
	
	
	
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	
	
	public List<Service> getServices() {
		if (this.services == null) {
			this.services = new ArrayList<Service>();
		}
		return this.services;
	}
	
	
	
	
	public List<Account> getAccounts() {
		if (this.accounts == null) {
			this.accounts = new ArrayList<Account>();
		}
		return this.accounts;
	}
	
	
	
	
	public List<Component> getComponents() {
		if (this.components == null) {
			this.components = new ArrayList<Component>();
		}
		return this.components;
	}
	
	
	
	
	public List<Corridor> getCorridors() {
		if (this.corridors == null) {
			this.corridors = new ArrayList<Corridor>();
		}
		return this.corridors;
	}
	
	
	
	
	public List<Fidelity> getFidelitys() {
		if (this.fidelitys == null) {
			this.fidelitys = new ArrayList<Fidelity>();
		}
		return this.fidelitys;
	}
	
	
	
	
	public String getException() {
		return this.exception;
	}
	
	
	
	
	public void setException(String value) {
		this.exception = value;
	}
	
	
	
	
	public String getHasReRate() {
		return hasReRate;
	}
	
	
	
	
	public void setHasReRate(String hasReRate) {
		this.hasReRate = hasReRate;
	}
	
	
	
	
	public String getSLog() {
		return this.sLog;
	}
	
	
	
	
	public void setSLog(String value) {
		this.sLog = value;
	}
	
	
	
	
	public boolean isErro() {
		return this.erro;
	}
	
	
	
	
	public void setErro(boolean value) {
		this.erro = value;
	}
	
	
	
	
	private String getDate(XMLGregorianCalendar activeDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		return activeDate != null ? simpleDateFormat.format(activeDate.toGregorianCalendar().getTimeInMillis()) : "N/A";
	}
	
	
	
	
	@Override
	public String toString() {
		java.lang.StringBuilder resultString = new java.lang.StringBuilder();
		
		// Accounts.
		resultString.append("-------------------------------------\n");
		resultString.append("-- Accounts\n");
		resultString.append("-------------------------------------\n\n");
		for (Account account : getAccounts()) {
			resultString.append("> Account: " + account.getAccountExternalId() + " -> " + account.getAction() +
					"\n    Server Id: " + account.getServerId() +
					"\n    Active Date: " + getDate(account.dateActive) +
					"\n    Inactive Date: " + getDate(account.getDateInactive()) +
					"\n    Bill Period: " + account.getBillPeriod());
		}
		// Services.
		resultString.append("\n\n-------------------------------------\n");
		resultString.append("-- Services\n");
		resultString.append("-------------------------------------");
		for (Service service : getServices()) {
			resultString.append("\n\n> Service: " + service.getBServiceLname() + " -> " + service.getAction() +
					"\n    Service Id: " + service.getServiceExternalId() +
					"\n    Active Date: " + getDate(service.getServiceActiveDt()) +
					"\n    Inactive Date: " + getDate(service.getServiceInactiveDt()));
		}
		// Components.
		resultString.append("\n\n-------------------------------------\n");
		resultString.append("-- Components\n");
		resultString.append("-------------------------------------");
		for (Component component : getComponents()) {
			resultString.append("\n\n> Component: " + component.getComponentName() + " -> " + component.getAction() +
					"\n    Component Id: " + component.getComponentId() +
					"\n    Package Id: " + component.getPackageId() +
					"\n    Active Date: " + getDate(component.getActiveDt()) +
					"\n    Inactive Date: " + getDate(component.getInactiveDt()) +
					"\n    Of Account: " + component.isComponentOfAccount() +
					"\n    Inst. Id: " + component.getComponentInstId());
		}
		// Fidelity.
		resultString.append("\n\n-------------------------------------\n");
		resultString.append("-- Fidelities\n");
		resultString.append("-------------------------------------");
		for (Fidelity fidelity : getFidelitys()) {
			resultString.append("\n\n> Fidelity: " + fidelity.getProduto() + " -> " + fidelity.getAction() +
					"\n    Term: " + fidelity.getIdTermo() +
					"\n    Term Name: " + fidelity.getNomeTermo() +
					"\n    Active Date: " + getDate(fidelity.getDataInicio()) +
					"\n    Inactive Date: " + getDate(fidelity.getDataFim()) +
					"\n    Value: " + fidelity.getValor());
		}
		resultString.append("\n\n");
		
		return resultString.toString();
	}
}
