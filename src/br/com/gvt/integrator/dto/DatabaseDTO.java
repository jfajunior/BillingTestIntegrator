package br.com.gvt.integrator.dto;


import java.io.Serializable;




/**
 * @author José Júnior
 *         GVT - 2014.08
 */
public class DatabaseDTO implements Serializable {
	private static final long serialVersionUID = -5841058680204143706L;
	
	private String _connectionName;
	private String _username;
	private String _password;
	private String _hostname;
	private String _port;
	private String _sid;
	
	
	
	
	public DatabaseDTO() {
		new DatabaseDTO("", "", "", "", "", "");
	}
	
	
	
	
	public DatabaseDTO(String connectionName, String username, String password, String hostname, String port, String sid) {
		_connectionName = connectionName;
		_username = username;
		_password = password;
		_hostname = hostname;
		_port = port;
		_sid = sid;
	}
	
	
	
	
	public String getConnectionName() {
		return _connectionName;
	}
	
	
	
	
	public void setConnectionName(String connectionName) {
		this._connectionName = connectionName;
	}
	
	
	
	
	public String getUsername() {
		return _username;
	}
	
	
	
	
	public void setUsername(String username) {
		this._username = username;
	}
	
	
	
	
	public String getPassword() {
		return _password;
	}
	
	
	
	
	public void setPassword(String password) {
		this._password = password;
	}
	
	
	
	
	public String getHostname() {
		return _hostname;
	}
	
	
	
	
	public void setHostname(String hostname) {
		this._hostname = hostname;
	}
	
	
	
	
	public String getPort() {
		return _port;
	}
	
	
	
	
	public void setPort(String port) {
		this._port = port;
	}
	
	
	
	
	public String getSid() {
		return _sid;
	}
	
	
	
	
	public void setSid(String sid) {
		this._sid = sid;
	}
	
	
}
