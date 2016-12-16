package br.com.gvt.integrator.listener;


import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.gvt.integrator.ui.component.TabPanelBillingInfoPanel;




/**
 * @author José Júnior
 *         GVT - 2013.10
 */
public class ChangeListenerEnableDisableButtonForEditableTab implements ChangeListener {
	private TabPanelBillingInfoPanel _infoPanel;
	private JButton _button;
	
	
	
	
	public ChangeListenerEnableDisableButtonForEditableTab(TabPanelBillingInfoPanel infoPanel, JButton button) {
		_infoPanel = infoPanel;
		_button = button;
	}
	
	
	
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
		int selectedIndex = _infoPanel.getTabbedPane().getSelectedIndex();
		_button.setEnabled(selectedIndex == 0 || selectedIndex == 1);
	}
	
}
