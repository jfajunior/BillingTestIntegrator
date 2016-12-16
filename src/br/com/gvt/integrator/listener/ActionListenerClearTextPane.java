package br.com.gvt.integrator.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;

import br.com.gvt.integrator.utils.Swing;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class ActionListenerClearTextPane implements ActionListener {
	private JTabbedPane _tabbedPane;
	
	
	
	
	public ActionListenerClearTextPane(JTabbedPane tabbedPane) {
		_tabbedPane = tabbedPane;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Swing.cleanTabbedPaneTextPanes(_tabbedPane);
	}
	
}
