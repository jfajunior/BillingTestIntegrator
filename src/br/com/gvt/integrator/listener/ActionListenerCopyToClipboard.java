package br.com.gvt.integrator.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class ActionListenerCopyToClipboard implements ActionListener {
	private JTabbedPane _tabbedPane;
	
	
	
	
	public ActionListenerCopyToClipboard(JTabbedPane tabbedPane) {
		_tabbedPane = tabbedPane;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Get selected text pane.
		JTextPane textPane = (JTextPane) ((JScrollPane) _tabbedPane.getSelectedComponent()).getViewport().getView();
		textPane.selectAll();
		textPane.copy();
	}
	
}
