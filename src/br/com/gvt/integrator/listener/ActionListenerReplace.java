package br.com.gvt.integrator.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextPane;

import br.com.gvt.integrator.ui.component.DialogFind;
import br.com.gvt.integrator.ui.component.TabPanelBillingInfoPanel;




public class ActionListenerReplace implements ActionListener {
	private DialogFind _dialogFind;
	private JButton _buttonFind;
	
	
	
	
	public ActionListenerReplace(DialogFind dialogFind, JButton buttonFind) {
		_dialogFind = dialogFind;
		_buttonFind = buttonFind;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		TabPanelBillingInfoPanel tabPanelBillingInfoPanel = _dialogFind.getPanelTabBillingInfoPanel();
		
		// Get selected text pane.
		JTextPane textPane = tabPanelBillingInfoPanel.getSelectedTextPane();
		
		// Get the string from the editing area.
		String editorText = textPane.getText();
		
		// Compare selected text with text to search.
		String selectedText = editorText.substring(textPane.getSelectionStart(), textPane.getSelectionEnd());
		String searchedText = DialogFind.getTextFieldFind().getText();
		
		// Replace the selected text with text to replace.
		if (searchedText.equalsIgnoreCase(selectedText)) {
			
			// Find the substrings that appear before and after the selection.
			String startSubstring = editorText.substring(0, textPane.getSelectionStart());
			String endSubstring = editorText.substring(textPane.getSelectionEnd());
			
			// Replace the selected portion with the contents of the replace field.
			editorText = startSubstring + DialogFind.getTextFieldReplace().getText() + endSubstring;
			
			// Set the new text with the selected string replaced.
			textPane.setText(editorText);
			
			// Search for the next word.
			_buttonFind.doClick();
		}
	}
	
}
