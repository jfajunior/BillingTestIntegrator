package br.com.gvt.integrator.ui.component;


import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JToolBar;

import br.com.gvt.integrator.listener.ActionListenerSaveDatabasesSettings;
import br.com.gvt.integrator.utils.Resources;




/**
 * The database settings panel tool bar.
 * 
 * @author José Júnior
 *         GVT - 2014.08
 */
public class TabPanelSettingsDatabasesToolBar extends JToolBar {
	private static final long serialVersionUID = -1943174278322949450L;
	
	
	
	
	public TabPanelSettingsDatabasesToolBar(final TabPanelSettingsDatabasesPanel tabPanelSettings) {
		init(tabPanelSettings);
	}
	
	
	
	
	private void init(final TabPanelSettingsDatabasesPanel tabPanelSettings) {
		setPreferredSize(new Dimension(180, 20));
		
		JButton btnSave = new JButton(Resources.INSTANCE.getString("Button.save"));
		btnSave.addActionListener(new ActionListenerSaveDatabasesSettings(tabPanelSettings));
		add(btnSave);
		
		setFloatable(false);
	}
	
}
