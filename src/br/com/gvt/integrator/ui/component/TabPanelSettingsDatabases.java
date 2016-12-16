package br.com.gvt.integrator.ui.component;


import java.awt.GridLayout;

import javax.swing.JPanel;




/**
 * A panel that groups all the database settings.
 * 
 * @author José Júnior
 *         GVT - 2014.08
 */
public class TabPanelSettingsDatabases extends JPanel {
	private static final long serialVersionUID = 1448724664794030024L;
	
	private TabPanelSettingsDatabase _settingsSubPanelDevelopment;
	private TabPanelSettingsDatabase _settingsSubPanelQuality;
	private TabPanelSettingsDatabase _settingsSubPanelProduction;
	
	
	
	
	public TabPanelSettingsDatabases() {
		init();
	}
	
	
	
	
	private void init() {
		
		setLayout(new GridLayout(0, 1, 0, 0));
		
		// Development.
		_settingsSubPanelDevelopment = new TabPanelSettingsDatabase("Development");
		add(_settingsSubPanelDevelopment);
		
		// Quality assurance.
		_settingsSubPanelQuality = new TabPanelSettingsDatabase("Quality");
		add(_settingsSubPanelQuality);
		
		// Production.
		_settingsSubPanelProduction = new TabPanelSettingsDatabase("Production");
		add(_settingsSubPanelProduction);
	}
	
	
	
	
	public TabPanelSettingsDatabase getPanelDevelopment() {
		return _settingsSubPanelDevelopment;
	}
	
	
	
	
	public TabPanelSettingsDatabase getPanelQuality() {
		return _settingsSubPanelQuality;
	}
	
	
	
	
	public TabPanelSettingsDatabase getPanelProduction() {
		return _settingsSubPanelProduction;
	}
	
}
