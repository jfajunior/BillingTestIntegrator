package br.com.gvt.integrator.ui.component;


import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import net.boplicity.xmleditor.XmlTextPane;
import br.com.gvt.integrator.listener.CaretListenerTextHighlighter;
import br.com.gvt.integrator.ui.PSTabbedPane;




/**
 * A single billing panel instance, which is used for each operation independently.
 * 
 * @author José Júnior
 *         GVT - 2013.07
 */
public class TabPanelBillingInfoPanel extends JPanel {
	private static final long serialVersionUID = -2933058942057915206L;
	
	// Text pane font settings.
	private static final String TEXT_PANE_FONT_NAME = "Tahoma"; // Tahoma | Monospaced
	private static final int TEXT_PANE_FONT_STYLE = Font.PLAIN;
	private static final int TEXT_PANE_FONT_SIZE = 14;
	
	private TabPanelBillingInfoPanelBottom _infoPanelBottom;
	private JTabbedPane _tabbedPane;
	private JTextPane _textPaneResponse;
	private JTextPane _textPaneRequest;
	private JTextPane _textPaneInfo;
	
	
	
	
	public TabPanelBillingInfoPanel(String buttonName, JTextPane listeningTextPane) {
		init(buttonName, listeningTextPane);
	}
	
	
	
	
	private void init(String buttonName, JTextPane listeningActionButtonTextPane) {
		setLayout(new BorderLayout(0, 0));
		
		// Create the panel text pane for the request.
		_textPaneRequest = new XmlTextPane();
		_textPaneRequest.setEditable(false);
		_textPaneRequest.setFont(new Font(TEXT_PANE_FONT_NAME, TEXT_PANE_FONT_STYLE, TEXT_PANE_FONT_SIZE));
		
		// Create the panel text pane for the response.
		_textPaneResponse = new XmlTextPane();
		_textPaneResponse.setEditable(false);
		_textPaneResponse.setFont(new Font(TEXT_PANE_FONT_NAME, TEXT_PANE_FONT_STYLE, TEXT_PANE_FONT_SIZE));
		
		// Create the panel text pane for the info.
		_textPaneInfo = new XmlTextPane();
		_textPaneInfo.setEditable(false);
		_textPaneInfo.setFont(new Font(TEXT_PANE_FONT_NAME, Font.BOLD, TEXT_PANE_FONT_SIZE));
		
		// Create the text pane's scroll pane.
		JScrollPane scrollPaneRequest = new JScrollPane(_textPaneRequest);
		JScrollPane scrollPaneResponse = new JScrollPane(_textPaneResponse);
		JScrollPane scrollPaneInfo = new JScrollPane(_textPaneInfo);
		
		// Create the tabbed pane with 3 tabs: Response, Request and Info.
		_tabbedPane = new PSTabbedPane(JTabbedPane.TOP);
		_tabbedPane.addTab("Request", null, scrollPaneRequest);
		_tabbedPane.addTab("Response", null, scrollPaneResponse);
		_tabbedPane.addTab("Info", null, scrollPaneInfo);
		add(_tabbedPane);
		
		// Create and add the info panel's bottom panel.
		_infoPanelBottom = new TabPanelBillingInfoPanelBottom(buttonName, _tabbedPane, _textPaneRequest);
		add(_infoPanelBottom, BorderLayout.SOUTH);
		
		// Add text highlight listeners.
		_textPaneRequest.addCaretListener(new CaretListenerTextHighlighter());
		_textPaneResponse.addCaretListener(new CaretListenerTextHighlighter());
		_textPaneInfo.addCaretListener(new CaretListenerTextHighlighter());
		
		// TODO LOCK Problema do listener: buttonAction.setEnabled.
		
		// Add text pane document listener for listening for document changes.
		// DocumentListener documentListenerGenerateButton =
		// new DocumentListenerEnableButton(_infoPanelBottom.getButtonGenerate());
		// listeningActionButtonTextPane.getDocument().addDocumentListener(documentListenerGenerateButton);
		//
		// DocumentListener documentListenerButtonsRequest = new DocumentListenerEnableButton(
		// _infoPanelBottom.getButtonAction(), _infoPanelBottom.getButtonCopy(),
		// _infoPanelBottom.getButtonClear(), _infoPanelBottom.getButtonFormat());
		//
		// DocumentListener documentListenerButtonsRespose = new DocumentListenerEnableButton(
		// _infoPanelBottom.getButtonCopy(), _infoPanelBottom.getButtonClear(),
		// _infoPanelBottom.getButtonFormat());
		//
		// _textPaneRequest.getDocument().addDocumentListener(documentListenerButtonsRequest);
		// _textPaneResponse.getDocument().addDocumentListener(documentListenerButtonsRespose);
	}
	
	
	
	
	public TabPanelBillingInfoPanelBottom getInfoPanelBottom() {
		return _infoPanelBottom;
	}
	
	
	
	
	public JTextPane getSelectedTextPane() {
		return (JTextPane) ((JScrollPane) _tabbedPane.getSelectedComponent()).getViewport().getView();
	}
	
	
	
	
	public JTabbedPane getTabbedPane() {
		return _tabbedPane;
	}
	
	
	
	
	public JTextPane getTextPaneResponse() {
		return _textPaneResponse;
	}
	
	
	
	
	public JTextPane getTextPaneRequest() {
		return _textPaneRequest;
	}
	
	
	
	
	public JTextPane getTextPaneInfo() {
		return _textPaneInfo;
	}
	
	
}
