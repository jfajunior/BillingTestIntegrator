package br.com.gvt.integrator.enums;


/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public enum Environment {
	DEVELOPMENT("Development"), QUALITY_ASSURANCE("Quality Assurance"), PRODUCTION("Production");
	
	private final String value;
	
	
	
	
	private Environment(String value) {
		this.value = value;
	}
	
	
	
	
	@Override
	public String toString() {
		return value;
	}
	
	
}
