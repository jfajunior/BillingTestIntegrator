package br.com.gvt.integrator.ui.component;


import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import br.com.gvt.integrator.dto.DatabaseDTO;
import br.com.gvt.integrator.listener.ActionListenerTestDatabaseConnection;
import br.com.gvt.integrator.utils.Resources;




/**
 * A single database settings.
 * 
 * @author junior
 *         GVT - 2014.08
 */
public class TabPanelSettingsDatabase extends JPanel {
	private static final long serialVersionUID = 1861827271228248003L;
	
	// private final Logger _logger = Logger.getLogger(TabPanelSettingsDatabase.class);
	
	private static int JTEXTFIELD_COLUMN_SIZE = 50;
	
	private JTextField _textFieldConnectionName;
	private JTextField _textFieldUsername;
	private JTextField _textFieldPassword;
	private JTextField _textFieldHostname;
	private JTextField _textFieldPort;
	private JTextField _textFieldSid;
	private JButton _buttonTestConnection;
	private JLabel _labelConnectionStatus;
	
	
	
	
	public TabPanelSettingsDatabase(String environment) {
		Font fontSettings = new Font("Tahoma", Font.PLAIN, 12);
		
		// Create the title border for the given environment.
		Border compound = new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
		setBorder(BorderFactory.createTitledBorder(compound, environment, TitledBorder.LEADING, TitledBorder.TOP,
				fontSettings.deriveFont(Font.BOLD)));
		
		_textFieldConnectionName = new JTextField(JTEXTFIELD_COLUMN_SIZE);
		_textFieldUsername = new JTextField(JTEXTFIELD_COLUMN_SIZE);
		_textFieldPassword = new JPasswordField(JTEXTFIELD_COLUMN_SIZE);
		_textFieldHostname = new JTextField(JTEXTFIELD_COLUMN_SIZE);
		_textFieldPort = new JTextField(JTEXTFIELD_COLUMN_SIZE);
		_textFieldSid = new JTextField(JTEXTFIELD_COLUMN_SIZE);
		_buttonTestConnection = new JButton(Resources.INSTANCE.getString("test"));
		
		ActionListenerTestDatabaseConnection actionListenerTestDatabaseConnection =
				new ActionListenerTestDatabaseConnection(this);
		_buttonTestConnection.addActionListener(actionListenerTestDatabaseConnection);
		
		_labelConnectionStatus = new JLabel("");
		
		JLabel labelConnectionName = new SettingsLabel(fontSettings, Resources.INSTANCE.getString("connection_name"));
		JLabel labelUsername = new SettingsLabel(fontSettings, Resources.INSTANCE.getString("username"));
		JLabel labelPassword = new SettingsLabel(fontSettings, Resources.INSTANCE.getString("password"));
		JLabel labelHostName = new SettingsLabel(fontSettings, Resources.INSTANCE.getString("hostname"));
		JLabel labelPort = new SettingsLabel(fontSettings, Resources.INSTANCE.getString("port"));
		JLabel labelSid = new SettingsLabel(fontSettings, Resources.INSTANCE.getString("sid"));
		
		// Setting the panel layout.
		GroupLayout gl_panel1 = new GroupLayout(this);
		gl_panel1
				.setHorizontalGroup(
				gl_panel1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel1
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel1.createParallelGroup(Alignment.TRAILING)
														.addComponent(_buttonTestConnection)
														.addGroup(
																gl_panel1.createParallelGroup(Alignment.LEADING, false)
																		.addComponent(labelPassword,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(labelUsername,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(labelConnectionName,
																				GroupLayout.DEFAULT_SIZE, 109,
																				Short.MAX_VALUE)))
										.addGroup(
												gl_panel1
														.createParallelGroup(Alignment.LEADING)
														.addGroup(
																gl_panel1
																		.createSequentialGroup()
																		.addGap(4)
																		.addGroup(
																				gl_panel1
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								_textFieldPassword,
																								GroupLayout.PREFERRED_SIZE,
																								212, Short.MAX_VALUE)
																						.addComponent(
																								_textFieldUsername,
																								GroupLayout.PREFERRED_SIZE,
																								212, Short.MAX_VALUE)
																						.addComponent(
																								_textFieldConnectionName,
																								GroupLayout.PREFERRED_SIZE,
																								212,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(ComponentPlacement.UNRELATED)
																		.addGroup(
																				gl_panel1
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								labelSid,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								labelPort,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								labelHostName,
																								GroupLayout.PREFERRED_SIZE,
																								95,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panel1
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								_textFieldSid,
																								GroupLayout.PREFERRED_SIZE,
																								212,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								_textFieldPort,
																								GroupLayout.PREFERRED_SIZE,
																								212,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								_textFieldHostname,
																								GroupLayout.PREFERRED_SIZE,
																								212,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																gl_panel1.createSequentialGroup()
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addComponent(_labelConnectionStatus,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)))
										.addGap(102))
				);
		gl_panel1
				.setVerticalGroup(
				gl_panel1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel1
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel1
														.createParallelGroup(Alignment.LEADING)
														.addGroup(
																gl_panel1
																		.createSequentialGroup()
																		.addGroup(
																				gl_panel1
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								labelHostName,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								_textFieldHostname,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panel1
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								labelPort,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								_textFieldPort,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panel1
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								_textFieldSid,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								labelSid,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																gl_panel1
																		.createSequentialGroup()
																		.addGroup(
																				gl_panel1
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								labelConnectionName,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								_textFieldConnectionName,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panel1
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								labelUsername,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								_textFieldUsername,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panel1
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								_textFieldPassword,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								labelPassword,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(
												gl_panel1.createParallelGroup(Alignment.BASELINE)
														.addComponent(_buttonTestConnection)
														.addComponent(_labelConnectionStatus,
																GroupLayout.PREFERRED_SIZE, 19,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(186, Short.MAX_VALUE))
				);
		setLayout(gl_panel1);
	}
	
	
	
	
	public void loadEnvironment(DatabaseDTO database) {
		setConnectionName(database.getConnectionName());
		setUsername(database.getUsername());
		setPassword(database.getPassword());
		setHostname(database.getHostname());
		setPort(database.getPort());
		setSid(database.getSid());
	}
	
	
	
	
	public String getConnectionName() {
		return _textFieldConnectionName.getText();
	}
	
	
	
	
	public void setConnectionName(String connectionName) {
		_textFieldConnectionName.setText(connectionName);
	}
	
	
	
	
	public String getUsername() {
		return _textFieldUsername.getText();
	}
	
	
	
	
	public void setUsername(String username) {
		_textFieldUsername.setText(username);
	}
	
	
	
	
	public String getPassword() {
		return _textFieldPassword.getText();
	}
	
	
	
	
	public void setPassword(String password) {
		_textFieldPassword.setText(password);
	}
	
	
	
	
	public String getHostName() {
		return _textFieldHostname.getText();
	}
	
	
	
	
	public void setHostname(String hostname) {
		_textFieldHostname.setText(hostname);
	}
	
	
	
	
	public String getPort() {
		return _textFieldPort.getText();
	}
	
	
	
	
	public void setPort(String port) {
		_textFieldPort.setText(port);
	}
	
	
	
	
	public String getSid() {
		return _textFieldSid.getText();
	}
	
	
	
	
	public void setSid(String sid) {
		_textFieldSid.setText(sid);
	}
	
	
	
	
	public String getConnectionStatus() {
		return _labelConnectionStatus.getText();
	}
	
	
	
	
	public void setConnectionStatus(String status) {
		_labelConnectionStatus.setText(status);
	}
}
