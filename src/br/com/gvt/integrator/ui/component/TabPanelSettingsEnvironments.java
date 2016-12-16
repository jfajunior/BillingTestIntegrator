package br.com.gvt.integrator.ui.component;


import java.awt.GridLayout;

import javax.swing.JPanel;




/**
 * A panel that groups all the environment settings.
 * 
 * @author José Júnior
 *         GVT - 2013.07
 */
public class TabPanelSettingsEnvironments extends JPanel {
	private static final long serialVersionUID = 1448724664794030024L;
	
	private TabPanelSettingsEnvironment _settingsSubPanelDevelopment;
	private TabPanelSettingsEnvironment _settingsSubPanelQuality;
	private TabPanelSettingsEnvironment _settingsSubPanelProduction;
	
	
	
	
	public TabPanelSettingsEnvironments() {
		init();
	}
	
	
	
	
	private void init() {
		
		setLayout(new GridLayout(0, 1, 0, 0));
		
		// Development.
		_settingsSubPanelDevelopment = new TabPanelSettingsEnvironment("Development");
		add(_settingsSubPanelDevelopment);
		
		// Quality assurance.
		_settingsSubPanelQuality = new TabPanelSettingsEnvironment("Quality");
		add(_settingsSubPanelQuality);
		
		// Production.
		_settingsSubPanelProduction = new TabPanelSettingsEnvironment("Production");
		add(_settingsSubPanelProduction);
	}
	
	
	
	
	public TabPanelSettingsEnvironment getPanelDevelopment() {
		return _settingsSubPanelDevelopment;
	}
	
	
	
	
	public TabPanelSettingsEnvironment getPanelQuality() {
		return _settingsSubPanelQuality;
	}
	
	
	
	
	public TabPanelSettingsEnvironment getPanelProduction() {
		return _settingsSubPanelProduction;
	}
	
}
