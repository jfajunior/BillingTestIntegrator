package br.com.gvt.integrator.ui.component;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import br.com.gvt.integrator.utils.Resources;




/**
 * The main panel tool bar.
 * 
 * @author José Júnior
 *         GVT - 2013.07
 */
public class TabPanelBillingToolBar extends JToolBar {
	private static final long serialVersionUID = 5682645360708698860L;
	
	private static final int MAX_TEXT_LENGTH = 24;
	
	private JFrame _parent;
	private JTextPane _textPaneOrderId;
	private TabPanelBilling _tabPanelBilling;
	
	
	
	
	public TabPanelBillingToolBar(JFrame parent, TabPanelBilling panelBilling) {
		_parent = parent;
		_tabPanelBilling = panelBilling;
		init();
	}
	
	
	
	
	private void init() {
		setFloatable(false);
		addSeparator(new Dimension(6, 0));
		setPreferredSize(new Dimension(180, 20));
		
		// Order id area.
		JLabel labelOrderId = new JLabel(Resources.INSTANCE.getString("orderId"));
		add(labelOrderId);
		
		// Add a component division.
		addSeparator(new Dimension(6, 0));
		
		// Create and add the text pane for order id.
		_textPaneOrderId = new JTextPane();
		_textPaneOrderId.setMinimumSize(new Dimension(124, 24));
		_textPaneOrderId.setMaximumSize(new Dimension(124, 24));
		_textPaneOrderId.setPreferredSize(new Dimension(124, 24));
		_textPaneOrderId.setAlignmentX(Component.LEFT_ALIGNMENT);
		_textPaneOrderId.setAlignmentY(Component.CENTER_ALIGNMENT);
		_textPaneOrderId.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		// Create a document filter to allow just numeric characters.
		((AbstractDocument) _textPaneOrderId.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,
					javax.swing.text.AttributeSet attr) throws BadLocationException {
				
				if (fb.getDocument().getLength() + text.length() <= MAX_TEXT_LENGTH) {
					// super.insertString(fb, offset, text.replaceAll("[^A-Z0-9-]", ""), attr);
					super.replace(fb, offset, length, text.toUpperCase().trim(), attr);
				}
				else {
					if (!text.equalsIgnoreCase("\n")) {
						showError();
					}
				}
			}
			
			
			private void showError() {
				JOptionPane.showMessageDialog(_parent,
						Resources.INSTANCE.getString("error_order_id_length"),
						Resources.INSTANCE.getString("warning"),
						JOptionPane.WARNING_MESSAGE);
			}
		});
		
		// Listen for ENTER key to generate request.
		_textPaneOrderId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					_tabPanelBilling.getPanelBilling().getInfoPanelBottom().getButtonGenerate().doClick();
				}
			}
		});
		
		// Add the JTextPane to the JToolBar.
		add(_textPaneOrderId);
	}
	
	
	
	
	public JTextPane getTextPaneOrderId() {
		return _textPaneOrderId;
	}
	
	
}
