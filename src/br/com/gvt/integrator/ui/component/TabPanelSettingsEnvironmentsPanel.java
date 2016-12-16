package br.com.gvt.integrator.ui.component;


import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.dto.SettingsEnvironmentDTO;
import br.com.gvt.integrator.enums.Environment;
import br.com.gvt.integrator.utils.Constants;
import br.com.gvt.integrator.utils.FileSystemUtils;




/**
 * A panel with the application settings.
 * 
 * @author José Júnior
 *         GVT - 2013.07
 */
public class TabPanelSettingsEnvironmentsPanel extends JPanel {
	private static final long serialVersionUID = 1448724664794030024L;
	
	private final Logger _logger = Logger.getLogger(TabPanelSettingsEnvironmentsPanel.class);
	
	private TabPanelSettingsEnvironmentsToolBar _tabPanelSettingsToolBar;
	private TabPanelSettingsEnvironments _tabPanelSettingsEnvironments;
	
	
	
	
	public TabPanelSettingsEnvironmentsPanel() {
		init();
	}
	
	
	
	
	private void init() {
		_logger.info("Initializing....");
		setLayout(new BorderLayout());
		
		_tabPanelSettingsToolBar = new TabPanelSettingsEnvironmentsToolBar(this);
		add(_tabPanelSettingsToolBar, BorderLayout.NORTH);
		
		_tabPanelSettingsEnvironments = new TabPanelSettingsEnvironments();
		add(_tabPanelSettingsEnvironments, BorderLayout.CENTER);
		
		SettingsEnvironmentDTO settings = null;
		try {
			settings = (SettingsEnvironmentDTO) FileSystemUtils.loadObjectFromFile(Constants.SETTINGS_FILE_PATH_ENVIRONMENT);
		}
		catch (ClassNotFoundException | IOException e) {
			_logger.warn("Settings file could not be loaded! (First time running?)");
		}
		loadEnvironment(settings);
		
		_logger.info("Finished panel initialization.");
	}
	
	
	
	
	public void loadEnvironment(SettingsEnvironmentDTO settings) {
		_logger.debug("Loading environment settings....");
		
		if (settings != null) {
			_tabPanelSettingsEnvironments.getPanelDevelopment().loadEnvironment(settings.getEnvironmentDevelopment());
			_tabPanelSettingsEnvironments.getPanelQuality().loadEnvironment(settings.getEnvironmentQuality());
			_tabPanelSettingsEnvironments.getPanelProduction().loadEnvironment(settings.getEnvironmentProduction());
			_logger.debug("Environments loaded!");
		}
		else {
			_logger.debug("No settings to load this time.");
		}
	}
	
	
	
	
	public TabPanelSettingsEnvironment getPanelDevelopment() {
		return _tabPanelSettingsEnvironments.getPanelDevelopment();
	}
	
	
	
	
	public TabPanelSettingsEnvironment getPanelQuality() {
		return _tabPanelSettingsEnvironments.getPanelQuality();
	}
	
	
	
	
	public TabPanelSettingsEnvironment getPanelProduction() {
		return _tabPanelSettingsEnvironments.getPanelProduction();
	}
	
	
	
	
	public TabPanelSettingsEnvironment getPanel(Environment environment) throws Exception {
		switch (environment) {
			case DEVELOPMENT:
				return getPanelDevelopment();
			case QUALITY_ASSURANCE:
				return getPanelQuality();
			case PRODUCTION:
				return getPanelProduction();
			default:
				throw new Exception("Invalid environmnet!");
		}
	}
	
}
