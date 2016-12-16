package br.com.gvt.integrator.listener;


import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class DocumentListenerEnableButton implements DocumentListener {
	private JButton[] _buttons;
	private boolean _enabled;
	
	
	
	
	public DocumentListenerEnableButton(JButton... buttons) {
		_buttons = buttons;
		_enabled = false;
	}
	
	
	
	
	@Override
	public synchronized void insertUpdate(DocumentEvent e) {
		if (!_enabled) {
			for (JButton button : _buttons) {
				button.setEnabled(true);
			}
			_enabled = true;
		}
	}
	
	
	
	
	@Override
	public synchronized void removeUpdate(DocumentEvent e) {
		int length = e.getDocument().getLength();
		if (length == 0 && _enabled) {
			for (JButton button : _buttons) {
				button.setEnabled(false);
			}
			_enabled = false;
		}
	}
	
	
	
	
	@Override
	public void changedUpdate(DocumentEvent e) {
		// Plain text components do not fire these events.
	}
	
}
