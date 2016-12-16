package br.com.gvt.integrator.dto;


import java.io.Serializable;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class SettingsEnvironmentDTO implements Serializable {
	private static final long serialVersionUID = -8234938559350449436L;
	
	private EnvironmentDTO _environmentDevelopment;
	private EnvironmentDTO _environmentQuality;
	private EnvironmentDTO _environmentProduction;
	
	
	
	
	public SettingsEnvironmentDTO() {
		new SettingsEnvironmentDTO(new EnvironmentDTO(), new EnvironmentDTO(), new EnvironmentDTO());
	}
	
	
	
	
	public SettingsEnvironmentDTO(EnvironmentDTO development, EnvironmentDTO quality, EnvironmentDTO production) {
		_environmentDevelopment = development;
		_environmentQuality = quality;
		_environmentProduction = production;
	}
	
	
	
	
	public EnvironmentDTO getEnvironmentDevelopment() {
		return _environmentDevelopment;
	}
	
	
	
	
	public void setEnvironmentDevelopment(EnvironmentDTO development) {
		_environmentDevelopment = development;
	}
	
	
	
	
	public EnvironmentDTO getEnvironmentQuality() {
		return _environmentQuality;
	}
	
	
	
	
	public void setEnvironmentQuality(EnvironmentDTO quality) {
		_environmentQuality = quality;
	}
	
	
	
	
	public EnvironmentDTO getEnvironmentProduction() {
		return _environmentProduction;
	}
	
	
	
	
	public void setEnvironmentProduction(EnvironmentDTO production) {
		_environmentProduction = production;
	}
	
}
