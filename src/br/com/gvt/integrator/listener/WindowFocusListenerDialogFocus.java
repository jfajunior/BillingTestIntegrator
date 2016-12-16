package br.com.gvt.integrator.listener;


import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;




public class WindowFocusListenerDialogFocus implements WindowFocusListener {
	private JFrame _dialogFrame;
	
	
	
	
	public WindowFocusListenerDialogFocus(JFrame dialogFrame) {
		_dialogFrame = dialogFrame;
	}
	
	
	
	
	@Override
	public void windowLostFocus(WindowEvent e) {
		// System.out.println("Focus lost!");
	}
	
	
	
	
	@Override
	public void windowGainedFocus(WindowEvent e) {
		// System.out.println("Focus gained!");
		_dialogFrame.toFront();
	}
}
