package br.com.gvt.integrator.ui.component;


import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JToolBar;

import br.com.gvt.integrator.listener.ActionListenerSaveEnvironmentsSettings;
import br.com.gvt.integrator.utils.Resources;




/**
 * The environment settings panel tool bar.
 * 
 * @author José Júnior
 *         GVT - 2013.07
 */
public class TabPanelSettingsEnvironmentsToolBar extends JToolBar {
	private static final long serialVersionUID = -4631086646459291796L;
	
	
	
	
	public TabPanelSettingsEnvironmentsToolBar(final TabPanelSettingsEnvironmentsPanel tabPanelSettings) {
		init(tabPanelSettings);
	}
	
	
	
	
	private void init(final TabPanelSettingsEnvironmentsPanel tabPanelSettings) {
		setPreferredSize(new Dimension(180, 20));
		
		JButton btnSave = new JButton(Resources.INSTANCE.getString("Button.save"));
		btnSave.addActionListener(new ActionListenerSaveEnvironmentsSettings(tabPanelSettings));
		add(btnSave);
		
		setFloatable(false);
	}
	
}
