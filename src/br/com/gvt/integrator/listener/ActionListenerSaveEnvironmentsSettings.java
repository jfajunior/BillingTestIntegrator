package br.com.gvt.integrator.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.dto.EnvironmentDTO;
import br.com.gvt.integrator.dto.SettingsEnvironmentDTO;
import br.com.gvt.integrator.ui.component.TabPanelSettingsEnvironmentsPanel;
import br.com.gvt.integrator.utils.Constants;
import br.com.gvt.integrator.utils.FileSystemUtils;
import br.com.gvt.integrator.utils.Resources;




/**
 * @author José Júnior
 *         GVT - 2014.08
 */
public class ActionListenerSaveEnvironmentsSettings implements ActionListener {
	private final Logger _logger = Logger.getLogger(ActionListenerSaveEnvironmentsSettings.class);
	
	private final TabPanelSettingsEnvironmentsPanel _tabPanelSettings;
	
	
	
	
	public ActionListenerSaveEnvironmentsSettings(TabPanelSettingsEnvironmentsPanel tabPanelSettings) {
		_tabPanelSettings = tabPanelSettings;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		_logger.info("Saving environments...");
		
		EnvironmentDTO environmentDevelopment = new EnvironmentDTO(
				_tabPanelSettings.getPanelDevelopment().getUrlSiebelCustomerOrder(),
				_tabPanelSettings.getPanelDevelopment().getUrlSiebelAccountProfile(),
				_tabPanelSettings.getPanelDevelopment().getUrlTransform(),
				_tabPanelSettings.getPanelDevelopment().getUrlKenan());
		EnvironmentDTO environmentQuality = new EnvironmentDTO(
				_tabPanelSettings.getPanelQuality().getUrlSiebelCustomerOrder(),
				_tabPanelSettings.getPanelQuality().getUrlSiebelAccountProfile(),
				_tabPanelSettings.getPanelQuality().getUrlTransform(),
				_tabPanelSettings.getPanelQuality().getUrlKenan());
		EnvironmentDTO environmentProduction = new EnvironmentDTO(
				_tabPanelSettings.getPanelProduction().getUrlSiebelCustomerOrder(),
				_tabPanelSettings.getPanelProduction().getUrlSiebelAccountProfile(),
				_tabPanelSettings.getPanelProduction().getUrlTransform(),
				_tabPanelSettings.getPanelProduction().getUrlKenan());
		
		SettingsEnvironmentDTO settings = new SettingsEnvironmentDTO(environmentDevelopment, environmentQuality,
				environmentProduction);
		
		try {
			FileSystemUtils.save(Constants.SETTINGS_FILE_PATH_ENVIRONMENT, settings);
			JOptionPane.showMessageDialog(_tabPanelSettings,
					Resources.INSTANCE.getString("settings_environments_saved"),
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
