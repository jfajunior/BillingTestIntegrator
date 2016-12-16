package br.com.gvt.integrator.dto;


import java.io.Serializable;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class SettingsDatabasesDTO implements Serializable {
	private static final long serialVersionUID = 374247632598712767L;
	
	private DatabaseDTO _databaseDevelopment;
	private DatabaseDTO _databaseQuality;
	private DatabaseDTO _databaseProduction;
	
	
	
	
	public SettingsDatabasesDTO() {
		new SettingsDatabasesDTO(new DatabaseDTO(), new DatabaseDTO(), new DatabaseDTO());
	}
	
	
	
	
	public SettingsDatabasesDTO(DatabaseDTO development, DatabaseDTO quality, DatabaseDTO production) {
		_databaseDevelopment = development;
		_databaseQuality = quality;
		_databaseProduction = production;
	}
	
	
	
	
	public DatabaseDTO getEnvironmentDevelopment() {
		return _databaseDevelopment;
	}
	
	
	
	
	public void setEnvironmentDevelopment(DatabaseDTO development) {
		_databaseDevelopment = development;
	}
	
	
	
	
	public DatabaseDTO getEnvironmentQuality() {
		return _databaseQuality;
	}
	
	
	
	
	public void setEnvironmentQuality(DatabaseDTO quality) {
		_databaseQuality = quality;
	}
	
	
	
	
	public DatabaseDTO getEnvironmentProduction() {
		return _databaseProduction;
	}
	
	
	
	
	public void setEnvironmentProduction(DatabaseDTO production) {
		_databaseProduction = production;
	}
	
}
