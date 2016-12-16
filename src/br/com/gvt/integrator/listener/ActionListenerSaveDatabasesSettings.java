package br.com.gvt.integrator.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.dto.DatabaseDTO;
import br.com.gvt.integrator.dto.SettingsDatabasesDTO;
import br.com.gvt.integrator.ui.component.TabPanelSettingsDatabasesPanel;
import br.com.gvt.integrator.utils.Constants;
import br.com.gvt.integrator.utils.FileSystemUtils;
import br.com.gvt.integrator.utils.Resources;




/**
 * @author José Júnior
 *         GVT - 2014.08
 */
public class ActionListenerSaveDatabasesSettings implements ActionListener {
	private final Logger _logger = Logger.getLogger(ActionListenerSaveDatabasesSettings.class);
	
	private final TabPanelSettingsDatabasesPanel _tabPanelSettings;
	
	
	
	
	public ActionListenerSaveDatabasesSettings(TabPanelSettingsDatabasesPanel tabPanelSettings) {
		_tabPanelSettings = tabPanelSettings;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		_logger.info("Saving database settings...");
		
		DatabaseDTO databaseDevelopment = new DatabaseDTO(
				_tabPanelSettings.getPanelDevelopment().getConnectionName(),
				_tabPanelSettings.getPanelDevelopment().getUsername(),
				_tabPanelSettings.getPanelDevelopment().getPassword(),
				_tabPanelSettings.getPanelDevelopment().getHostName(),
				_tabPanelSettings.getPanelDevelopment().getPort(),
				_tabPanelSettings.getPanelDevelopment().getSid()
				);
		
		DatabaseDTO databaseQuality = new DatabaseDTO(
				_tabPanelSettings.getPanelQuality().getConnectionName(),
				_tabPanelSettings.getPanelQuality().getUsername(),
				_tabPanelSettings.getPanelQuality().getPassword(),
				_tabPanelSettings.getPanelQuality().getHostName(),
				_tabPanelSettings.getPanelQuality().getPort(),
				_tabPanelSettings.getPanelQuality().getSid()
				);
		
		DatabaseDTO databaseProduction = new DatabaseDTO(
				_tabPanelSettings.getPanelProduction().getConnectionName(),
				_tabPanelSettings.getPanelProduction().getUsername(),
				_tabPanelSettings.getPanelProduction().getPassword(),
				_tabPanelSettings.getPanelProduction().getHostName(),
				_tabPanelSettings.getPanelProduction().getPort(),
				_tabPanelSettings.getPanelProduction().getSid()
				);
		
		SettingsDatabasesDTO settings =
				new SettingsDatabasesDTO(databaseDevelopment, databaseQuality, databaseProduction);
		
		try {
			FileSystemUtils.save(Constants.SETTINGS_FILE_PATH_DATABASE, settings);
			JOptionPane.showMessageDialog(_tabPanelSettings,
					Resources.INSTANCE.getString("settings_databases_saved"),
					Resources.INSTANCE.getString("InfoDialog.title"),
					JOptionPane.INFORMATION_MESSAGE);
			
			_logger.info("Environments saved!");
		}
		catch (IOException e1) {
			JOptionPane.showMessageDialog(_tabPanelSettings,
					Resources.INSTANCE.getString("error_save_environment"),
					Resources.INSTANCE.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			
			_logger.error("Error while trying to save the environments!");
		}
		
	}
	
}
