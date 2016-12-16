package br.com.gvt.integrator.action;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class LookAndFeelAction extends AbstractAction {
	private static final long serialVersionUID = -2387156089895438838L;
	
	private JFrame _mainFrame;
	private String _lnfClassName;
	
	
	final static Logger _logger = Logger.getLogger(LookAndFeelAction.class);
	
	
	
	
	public LookAndFeelAction(JFrame mainFrame, String className) {
		_mainFrame = mainFrame;
		_lnfClassName = className;
		try {
			Class<?> lnfClass = Class.forName(_lnfClassName);
			LookAndFeel lnf = (LookAndFeel) lnfClass.newInstance();
			setEnabled(lnf.isSupportedLookAndFeel());
		}
		catch (Exception e) {
			setEnabled(false);
		}
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			UIManager.setLookAndFeel(_lnfClassName);
			SwingUtilities.updateComponentTreeUI(_mainFrame);
		}
		catch (Exception e) {
			_logger.error(e.getMessage());
		}
	}
	
}
