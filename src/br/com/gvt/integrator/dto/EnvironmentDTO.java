package br.com.gvt.integrator.dto;


import java.io.Serializable;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class EnvironmentDTO implements Serializable {
	private static final long serialVersionUID = 5328122555939391359L;
	
	private String _urlSiebelCustomerOrder;
	private String _urlSiebelAccountProfile;
	private String _urlTransform;
	private String _urlKenan;
	
	
	
	
	public EnvironmentDTO() {
		new EnvironmentDTO("", "", "", "");
	}
	
	
	
	
	public EnvironmentDTO(String urlSiebelCustomerOrder, String urlSiebelAccountProfile, String urlTransform, String urlKenan) {
		this._urlSiebelCustomerOrder = urlSiebelCustomerOrder;
		this._urlSiebelAccountProfile = urlSiebelAccountProfile;
		this._urlTransform = urlTransform;
		this._urlKenan = urlKenan;
	}
	
	
	
	
	public String getUrlSiebelCustomerOrder() {
		return _urlSiebelCustomerOrder;
	}
	
	
	
	
	public void setUrlSiebelCustomerOrder(String urlSiebelCustomerOrder) {
		_urlSiebelCustomerOrder = urlSiebelCustomerOrder;
	}
	
	
	
	
	public String getUrlSiebelAccountProfile() {
		return _urlSiebelAccountProfile;
	}
	
	
	
	
	public void setUrlSiebelAccountProfile(String urlSiebelAccountProfile) {
		_urlSiebelAccountProfile = urlSiebelAccountProfile;
	}
	
	
	
	
	public String get_urlTransform() {
		return _urlTransform;
	}
	
	
	
	
	public void set_urlTransform(String urlTransform) {
		_urlTransform = urlTransform;
	}
	
	
	
	
	public String getUrlKenan() {
		return _urlKenan;
	}
	
	
	
	
	public void setUrlKenan(String urlKenan) {
		_urlKenan = urlKenan;
	}
	
	
}
