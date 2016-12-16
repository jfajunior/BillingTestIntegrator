package br.com.gvt.integrator.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.ui.component.TabPanelSettingsDatabase;
import br.com.gvt.integrator.utils.DatabaseUtils;
import br.com.gvt.integrator.utils.Resources;




/**
 * @author José Júnior
 *         GVT - 2014.08
 */
public class ActionListenerTestDatabaseConnection implements ActionListener {
	private TabPanelSettingsDatabase _tabPanelSettingsDatabase;
	
	final static Logger _logger = Logger.getLogger(ActionListenerTestDatabaseConnection.class);
	
	
	
	
	public ActionListenerTestDatabaseConnection(TabPanelSettingsDatabase tabPanelSettingsDatabase) {
		_tabPanelSettingsDatabase = tabPanelSettingsDatabase;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		_tabPanelSettingsDatabase.setConnectionStatus(Resources.INSTANCE.getString("wait"));
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch (ClassNotFoundException ex) {
			_logger.error("Driver not found!");
			_tabPanelSettingsDatabase.setConnectionStatus(Resources.INSTANCE.getString("error_driver_not_found"));
		}
		
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			String username = _tabPanelSettingsDatabase.getUsername();
			String password = _tabPanelSettingsDatabase.getPassword();
			String hostname = _tabPanelSettingsDatabase.getHostName();
			String port = _tabPanelSettingsDatabase.getPort();
			String sid = _tabPanelSettingsDatabase.getSid();
			
			connection = DriverManager.getConnection("jdbc:oracle:thin:@" + hostname + ":" + port + ":" + sid,
					username, password);
			
			preparedStatement = connection.prepareStatement("SELECT 1 FROM DUAL");
			resultSet = preparedStatement.executeQuery();
			
			// Check for result.
			if (resultSet.next()) {
				_tabPanelSettingsDatabase.setConnectionStatus(Resources.INSTANCE.getString("status_success"));
				_logger.info("Connection OK!");
			}
			else {
				_tabPanelSettingsDatabase.setConnectionStatus(Resources.INSTANCE.getString("status_no_results"));
				_logger.error("Connection NOK!");
			}
		}
		catch (SQLException ex) {
			_logger.error("Got an SQL exception!");
			_logger.error("Error message: " + ex.getMessage());
			_tabPanelSettingsDatabase.setConnectionStatus(Resources.INSTANCE.getString("status_error") +
					ex.getMessage());
		}
		
		finally {
			DatabaseUtils.closeQuietly(connection, preparedStatement, resultSet);
		}
	}
}
