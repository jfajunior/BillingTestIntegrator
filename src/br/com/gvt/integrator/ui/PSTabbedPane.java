package br.com.gvt.integrator.ui;


import javax.swing.JTabbedPane;
import javax.swing.UIManager;




/**
 * PSTabbedPane is a tabbed pane that uses PSTabbedPaneUI to draw tabs.
 * 
 * @author José Júnior
 *         GVT - 2013.08
 */
public class PSTabbedPane extends JTabbedPane {
	private static final long serialVersionUID = 879589823050667907L;
	
	
	
	
	public PSTabbedPane() {
		super();
	}
	
	
	
	
	public PSTabbedPane(int tabPlacement) {
		super(tabPlacement);
	}
	
	
	
	
	public PSTabbedPane(int tabPlacement, int tabLayoutPolicy) {
		super(tabPlacement, tabLayoutPolicy);
	}
	
	
	
	
	@Override
	public void updateUI() {
		super.updateUI();
		if (UIManager.getLookAndFeel().getName().equalsIgnoreCase("Windows")) {
			setUI(new PSTabbedPaneUI());
		}
	}
}
