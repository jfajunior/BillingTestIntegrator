package br.com.gvt.integrator.listener;


import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

import org.apache.log4j.Logger;




public class CaretListenerTextHighlighter implements CaretListener {
	private final Logger _logger = Logger.getLogger(CaretListenerTextHighlighter.class);
	
	private static final int COLOR_BLUE = 0xACC8FF;
	
	
	
	
	public void caretUpdate(CaretEvent event) {
		// Get the text pane associated with the event.
		JTextPane textPane = (JTextPane) event.getSource();
		
		// Get the text pane highlighter and remove all highlights.
		DefaultHighlighter highlighter = (DefaultHighlighter) textPane.getHighlighter();
		highlighter.removeAllHighlights();
		
		// If there is no text selection, return.
		if (event.getDot() == event.getMark()) {
			return;
		}
		
		// Get selected text.
		String text = textPane.getText();
		String selectedText = textPane.getSelectedText();
		
		// Let's not highlight blank spaces.
		if (selectedText.trim().isEmpty()) {
			return;
		}
		
		// Add highlight to selected text.
		int index = 0;
		DefaultHighlightPainter highlightPainter = new DefaultHighlightPainter(new Color(COLOR_BLUE));
		while ((index = text.indexOf(selectedText, index)) > -1) {
			try {
				highlighter.addHighlight(index, selectedText.length() + index, highlightPainter);
				index = index + selectedText.length();
			}
			catch (BadLocationException e) {
				_logger.error("Highlight error. Cause: " + e.getCause() + ", Message: " + e.getMessage());
			}
		}
	}
}
