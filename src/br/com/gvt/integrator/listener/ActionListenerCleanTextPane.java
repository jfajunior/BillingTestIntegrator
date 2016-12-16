package br.com.gvt.integrator.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;

import br.com.gvt.integrator.utils.SwingUtils;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class ActionListenerCleanTextPane implements ActionListener {
	private JTabbedPane _tabbedPane;
	
	
	
	
	public ActionListenerCleanTextPane(JTabbedPane tabbedPane) {
		_tabbedPane = tabbedPane;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtils.cleanTabbedPaneTextPanes(_tabbedPane);
	}
	
}
