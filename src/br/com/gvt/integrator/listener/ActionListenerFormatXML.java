package br.com.gvt.integrator.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import org.xml.sax.SAXParseException;

import br.com.gvt.integrator.utils.Resources;
import br.com.gvt.integrator.utils.XmlUtils;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class ActionListenerFormatXML implements ActionListener {
	private JTabbedPane _tabbedPane;
	
	
	
	
	public ActionListenerFormatXML(JTabbedPane tabbedPane) {
		_tabbedPane = tabbedPane;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Get selected text pane.
		JTextPane textPane = (JTextPane) ((JScrollPane) _tabbedPane.getSelectedComponent()).getViewport().getView();
		
		try {
			textPane.setText(XmlUtils.format(textPane.getText(), true));
			textPane.setCaretPosition(0);
		}
		catch (Exception e) {
			// Check if the cause of the exception is a SAX parse.
			if (e.getCause() instanceof SAXParseException) {
				
				// Get sax parse exception.
				SAXParseException saxParseException = (SAXParseException) e.getCause();
				
				// Inform.
				JOptionPane.showMessageDialog(textPane,
						Resources.INSTANCE.getString("error_invalid_xml") +
								e.getCause().getMessage() + "\n" +
								Resources.INSTANCE.getString("line") +
								saxParseException.getLineNumber() + ", " +
								Resources.INSTANCE.getString("column") +
								saxParseException.getColumnNumber(),
						Resources.INSTANCE.getString("error"),
						JOptionPane.ERROR_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(textPane,
						Resources.INSTANCE.getString("error_invalid_xml") +
								e.getCause().getMessage(),
						Resources.INSTANCE.getString("error"),
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	
}
