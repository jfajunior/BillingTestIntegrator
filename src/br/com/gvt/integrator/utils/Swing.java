package br.com.gvt.integrator.utils;


import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;




/**
 * Java Swing helper class.
 * @author José Júnior
 *         GVT - 2014.08
 * 
 */
public class Swing {
	
	
	public static void cleanTabbedPaneTextPanes(JTabbedPane tabbedPane) {
		// Get all text panes.
		int totalTabs = tabbedPane.getTabCount();
		for (int i = 0; i < totalTabs; i++) {
			JTextPane textPane = (JTextPane) ((JScrollPane) tabbedPane.getComponentAt(i)).getViewport().getView();
			textPane.setText("");
		}
	}
	
	
	
	
	public static void cleanTabbedPaneTextPanes(JTabbedPane tabbedPane, int... indexes) {
		// Get text panes with the given indexes.
		for (int index : indexes) {
			JTextPane textPane =
					(JTextPane) ((JScrollPane) tabbedPane.getComponentAt(index)).getViewport().getView();
			textPane.setText("");
		}
	}
}
