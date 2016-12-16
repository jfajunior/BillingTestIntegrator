package br.com.gvt.integrator.listener;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;




public class KeyListenerEnterKeyToButtonClick implements KeyListener {
	private JButton[] _buttons;
	
	
	
	
	public KeyListenerEnterKeyToButtonClick(JButton... buttons) {
		_buttons = buttons;
	}
	
	
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// Nothing to do.
	}
	
	
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			for (JButton button : _buttons) {
				button.doClick();
			}
		}
	}
	
	
	
	
	@Override
	public void keyReleased(KeyEvent e) {
		// Nothing to do.
	}
	
}

