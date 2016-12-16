package br.com.gvt.integrator.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.enums.Environment;
import br.com.gvt.integrator.ui.component.TabPanelBillingInfoPanel;
import br.com.gvt.integrator.ui.component.TabPanelSettingsDatabase;
import br.com.gvt.integrator.ui.component.TabPanelSettingsDatabasesPanel;
import br.com.gvt.integrator.utils.DatabaseUtils;
import br.com.gvt.integrator.utils.Resources;




/**
 * @author José Júnior
 *         GVT - 2014.08
 */
public class ActionListenerUnlockAccount implements ActionListener {
	private JFrame _parent;
	private TabPanelBillingInfoPanel _infoPanel;
	private TabPanelSettingsDatabasesPanel _tabPanelSettings;
	
	final static Logger _logger = Logger.getLogger(ActionListenerUnlockAccount.class);
	
	
	
	
	public ActionListenerUnlockAccount(JFrame parent, TabPanelBillingInfoPanel infoPanel,
			TabPanelSettingsDatabasesPanel settingsTabPanel) {
		_parent = parent;
		_infoPanel = infoPanel;
		_tabPanelSettings = settingsTabPanel;
	}
	
	
	
	
	private static ArrayList<String> getAccountExternalIds(String transformRequest) {
		String accountExternalId;
		int invoiceProfleCodeIndex;
		ArrayList<String> accountExternalIdArray = new ArrayList<String>();
		
		try {
			// At least one profile index is mandatory.
			int invoiceProfileIndex = transformRequest.indexOf("<cus:PerfilFatura>");
			while (invoiceProfileIndex != -1) {
				// Where the code beings.
				invoiceProfleCodeIndex = transformRequest.indexOf("<cus:Codigo>", invoiceProfileIndex) + 12;
				
				// Get the external id and add it to the array list.
				accountExternalId = transformRequest.substring(invoiceProfleCodeIndex,
						transformRequest.indexOf("</cus:Codigo>", invoiceProfleCodeIndex));
				
				accountExternalIdArray.add(accountExternalId);
				
				// Go to the next invoice profile index.
				invoiceProfileIndex = transformRequest.indexOf("<cus:PerfilFatura>", invoiceProfleCodeIndex);
			}
		}
		catch (StringIndexOutOfBoundsException e) {
			_logger.warn("There is no invoice profile code in the request!");
		}
		return accountExternalIdArray;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Get request from the request tab of transform panel.
		String request = _infoPanel.getTextPaneRequest().getText();
		
		// Check for empty request.
		if (request.isEmpty()) {
			JOptionPane.showMessageDialog(_parent,
					Resources.INSTANCE.getString("error_no_request_transform"),
					Resources.INSTANCE.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Get all profile codes from the request message.
		ArrayList<String> profileCodeArray = getAccountExternalIds(request);
		
		if (profileCodeArray == null || profileCodeArray.isEmpty()) {
			JOptionPane.showConfirmDialog(
					_parent,
					Resources.INSTANCE.getString("error_no_invoice_profile"),
					Resources.INSTANCE.getString("error"),
					JOptionPane.CLOSED_OPTION,
					JOptionPane.ERROR_MESSAGE
					);
		}
		
		// Unlock each account from the request.
		int option = 0;
		for (String profileCode : profileCodeArray) {

			// Option Ok or No.
			if (option == 0 || option == 2) {
				String[] buttons = { Resources.INSTANCE.getString("yes"),
						Resources.INSTANCE.getString("Button.yes_to_all"),
						Resources.INSTANCE.getString("no"),
						Resources.INSTANCE.getString("Button.cancel")
				};
				
				// Get user choice.
				option = JOptionPane.showOptionDialog(
						_parent,
						String.format(Resources.INSTANCE.getString("unlock_account"), profileCode),
						Resources.INSTANCE.getString("InfoDialog.title"),
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE,
						null,
						buttons,
						buttons[0]
						);
			}
			
			// Option No: go to the next profile code.
			if (option == 2) {
				continue;
			}
			
			// Option Cancel: exit dialog.
			if (option == 3) {
				return;
			}
			
			
			// Get environment URL.
			Environment environment = (Environment)
					_infoPanel.getInfoPanelBottom().getComboBoxEnvironment().getSelectedItem();
			
			TabPanelSettingsDatabase tabPanelSettingsDatabase = null;
			try {
				tabPanelSettingsDatabase = _tabPanelSettings.getPanel(environment);
			}
			catch (Exception ex) {
				_logger.error(ex.getMessage());
				
				JOptionPane.showMessageDialog(_parent,
						Resources.INSTANCE.getString("error_unknown") + ex.getMessage(),
						Resources.INSTANCE.getString("error"),
						JOptionPane.ERROR_MESSAGE);
			}
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			}
			catch (ClassNotFoundException ex) {
				_logger.error("Driver not found!");
				tabPanelSettingsDatabase.setConnectionStatus(Resources.INSTANCE.getString("error_driver_not_found"));
			}
			
			ResultSet resultSet = null;
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			try {
				// Get database connection info.
				String username = tabPanelSettingsDatabase.getUsername();
				String password = tabPanelSettingsDatabase.getPassword();
				String hostname = tabPanelSettingsDatabase.getHostName();
				String port = tabPanelSettingsDatabase.getPort();
				String sid = tabPanelSettingsDatabase.getSid();
				
				connection = DriverManager.getConnection("jdbc:oracle:thin:@" + hostname + ":" + port + ":" + sid,
						username, password);
				
				preparedStatement = connection.prepareStatement(
						" DELETE FROM SAVVIONBILLING_OWNER.account_external_id " +
								" WHERE account_external_id = ? ");
				
				_logger.debug("Profile Code: " + profileCode);
				
				preparedStatement.setString(1, profileCode);
				
				int updateCounter = preparedStatement.executeUpdate();
				
				// Check for result.
				if (updateCounter > 0) {
					JOptionPane.showMessageDialog(_parent,
							String.format(Resources.INSTANCE.getString("invoice_profile_code"), profileCode) + "\n" +
									String.format(Resources.INSTANCE.getString("success_rows_updated"), updateCounter),
							Resources.INSTANCE.getString("InfoDialog.title"),
							JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(_parent,
							String.format(Resources.INSTANCE.getString("invoice_profile_code"), profileCode) + "\n" +
									Resources.INSTANCE.getString("no_rows_updated"),
							Resources.INSTANCE.getString("warning"),
							JOptionPane.INFORMATION_MESSAGE);
				}
				_logger.info("Rows updated: " + updateCounter);
			}
			catch (SQLException ex) {
				_logger.error("Got an SQL exception!");
				_logger.error("Error message: " + ex.getMessage());
				
				JOptionPane.showMessageDialog(_parent,
						Resources.INSTANCE.getString("error_sql") + ex.getMessage(),
						Resources.INSTANCE.getString("error"),
						JOptionPane.ERROR_MESSAGE);
			}
			
			finally {
				DatabaseUtils.closeQuietly(connection, preparedStatement, resultSet);
			}
		}
	}
	
	
}
