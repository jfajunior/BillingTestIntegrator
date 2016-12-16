package br.com.gvt.integrator.listener;


import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JTextPane;




public class ItemListenerLockUnlock implements ItemListener {
	private JTextPane _textPane;
	private boolean _locked;
	
	
	
	
	public ItemListenerLockUnlock(JTextPane textPane) {
		_textPane = textPane;
		_locked = true;
	}
	
	
	
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		_textPane.setEditable(_locked);
		
		_locked = !_locked;
	}
	
}
