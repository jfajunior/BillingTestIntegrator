package br.com.gvt.integrator.listener;


import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;




/**
 * @author José Júnior
 *         GVT - 2013.10
 */
public class ActionListenerPasteFromClipboard implements ActionListener {
	private JTabbedPane _tabbedPane;
	
	
	
	
	public ActionListenerPasteFromClipboard(JTabbedPane tabbedPane) {
		_tabbedPane = tabbedPane;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Get selected text pane.
		JTextPane textPane = (JTextPane) ((JScrollPane) _tabbedPane.getSelectedComponent()).getViewport().getView();
		
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable transferable = clipboard.getContents(null);
		if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			try {
				textPane.setText((String) transferable.getTransferData(DataFlavor.stringFlavor));
			}
			catch (UnsupportedFlavorException | IOException e) {
				System.err.println(e);
			}
		}
	}
	
}

