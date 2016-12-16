package br.com.gvt.integrator.action;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import br.com.gvt.integrator.ui.component.DialogFind;
import br.com.gvt.integrator.ui.component.TabPanelBillingInfoPanel;




/**
 * @author José Júnior GVT - 2014.12
 */
public class FindAction extends AbstractAction {
	private static final long serialVersionUID = 4354719994622664427L;
	
	private JFrame _mainFrame;
	private TabPanelBillingInfoPanel _tabPanelBillingInfoPanel;
	
	
	
	
	public FindAction(JFrame mainFrame, TabPanelBillingInfoPanel tabPanelBillingInfoPanel) {
		_mainFrame = mainFrame;
		_tabPanelBillingInfoPanel = tabPanelBillingInfoPanel;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// Create and show the find dialog.
		DialogFind.getInstance(_mainFrame, _tabPanelBillingInfoPanel);
	}
	
}
