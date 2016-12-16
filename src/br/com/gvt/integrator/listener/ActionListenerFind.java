package br.com.gvt.integrator.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextPane;

import br.com.gvt.integrator.ui.component.DialogFind;
import br.com.gvt.integrator.ui.component.TabPanelBillingInfoPanel;




public class ActionListenerFind implements ActionListener {
	private DialogFind _dialogFind;
	
	
	
	
	public ActionListenerFind(DialogFind dialogFind) {
		_dialogFind = dialogFind;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		TabPanelBillingInfoPanel tabPanelBillingInfoPanel = _dialogFind.getPanelTabBillingInfoPanel();
		
		// Get selected text pane.
		JTextPane textPane = tabPanelBillingInfoPanel.getSelectedTextPane();
		
		// Get the string from the editing area.
		String editorText = textPane.getText();
		
		// Get the string the user wants to search for.
		String searchValue = DialogFind.getTextFieldFind().getText();
		
		// If the search is case insensitive, change the editing text and search string to lower case.
		if (!DialogFind.getCheckBoxCaseSensitive().isSelected()) {
			editorText = editorText.toLowerCase();
			searchValue = searchValue.toLowerCase();
		}
		
		// If the option to select the whole word is selected.
		if (DialogFind.getCheckBoxWholeWord().isSelected()) {
			if (!searchValue.startsWith(" ")) {
				searchValue = " " + searchValue;
			}
			if (!searchValue.endsWith(" ")) {
				searchValue = searchValue + " ";
			}
		}
		
		// Find the next occurrence, searching forward or backward depending on the setting of the reverse box.
		int start;
		if (DialogFind.getCheckBoxSearchBackwards().isSelected()) {
			start = editorText.lastIndexOf(searchValue, textPane.getSelectionStart() - 1);
			if (start == -1 && DialogFind.getCheckBoxWrapAround().isSelected()) {
				start = editorText.lastIndexOf(searchValue);
			}
		}
		else {
			start = editorText.indexOf(searchValue, textPane.getSelectionEnd());
			if (start == -1 && DialogFind.getCheckBoxWrapAround().isSelected()) {
				start = editorText.indexOf(searchValue);
			}
		}
		
		// If the string was found, move the selection so that the found string is highlighted.
		if (start >= 0) {
			if (DialogFind.getCheckBoxWholeWord().isSelected()) {
				// Remove white spaces in case of whole word search.
				searchValue = searchValue.trim();
				start++;
			}
			
			textPane.setCaretPosition(start);
			textPane.moveCaretPosition(start + searchValue.length());
			textPane.getCaret().setSelectionVisible(true);
		}
		
	}
	
}
