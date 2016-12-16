package br.com.gvt.integrator.ui.component;


import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.dto.SettingsDatabasesDTO;
import br.com.gvt.integrator.enums.Environment;
import br.com.gvt.integrator.utils.Constants;
import br.com.gvt.integrator.utils.FileSystemUtils;




/**
 * A panel with the application database settings.
 * 
 * @author José Júnior
 *         GVT - 2014.08
 */
public class TabPanelSettingsDatabasesPanel extends JPanel {
	private static final long serialVersionUID = 1448724664794030024L;
	
	private final Logger _logger = Logger.getLogger(TabPanelSettingsDatabasesPanel.class);
	
	private TabPanelSettingsDatabasesToolBar _tabPanelSettingsDatabasesToolBar;
	private TabPanelSettingsDatabases _tabPanelSettingsDbs;
	
	
	
	
	public TabPanelSettingsDatabasesPanel() {
		init();
	}
	
	
	
	
	private void init() {
		_logger.info("Initializing....");
		setLayout(new BorderLayout());
		
		_tabPanelSettingsDatabasesToolBar = new TabPanelSettingsDatabasesToolBar(this);
		add(_tabPanelSettingsDatabasesToolBar, BorderLayout.NORTH);
		
		_tabPanelSettingsDbs = new TabPanelSettingsDatabases();
		add(_tabPanelSettingsDbs, BorderLayout.CENTER);
		
		SettingsDatabasesDTO settings = null;
		try {
			settings = (SettingsDatabasesDTO) FileSystemUtils.loadObjectFromFile(Constants.SETTINGS_FILE_PATH_DATABASE);
		}
		catch (ClassNotFoundException | IOException e) {
			_logger.warn("Settings file could not be loaded! (First time running?)");
		}
		loadEnvironment(settings);
		
		_logger.info("Finished panel initialization.");
	}
	
	
	
	
	public void loadEnvironment(SettingsDatabasesDTO settings) {
		_logger.debug("Loading environment settings....");
		
		if (settings != null) {
			_tabPanelSettingsDbs.getPanelDevelopment().loadEnvironment(settings.getEnvironmentDevelopment());
			_tabPanelSettingsDbs.getPanelQuality().loadEnvironment(settings.getEnvironmentQuality());
			_tabPanelSettingsDbs.getPanelProduction().loadEnvironment(settings.getEnvironmentProduction());
			_logger.debug("Environments loaded!");
		}
		else {
			_logger.debug("No settings to load this time.");
		}
	}
	
	
	
	
	public TabPanelSettingsDatabase getPanelDevelopment() {
		return _tabPanelSettingsDbs.getPanelDevelopment();
	}
	
	
	
	
	public TabPanelSettingsDatabase getPanelQuality() {
		return _tabPanelSettingsDbs.getPanelQuality();
	}
	
	
	
	
	public TabPanelSettingsDatabase getPanelProduction() {
		return _tabPanelSettingsDbs.getPanelProduction();
	}
	
	
	
	
	public TabPanelSettingsDatabase getPanel(Environment environment) throws Exception {
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
