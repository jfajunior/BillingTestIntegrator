package br.com.gvt.integrator.enums;


/**
 * @author José Júnior
 *         GVT - 2014.06
 */
public enum KenanFlow {
	EXECUTE_ORDER("Execute Order"), SERVICE_TRANSFER("Service Transfer"), SERVICE_BY_SERVICE("Service By Service");
	
	private final String value;
	
	
	
	
	private KenanFlow(String value) {
		this.value = value;
	}
	
	
	
	
	@Override
	public String toString() {
		return value;
	}
	
	
}
