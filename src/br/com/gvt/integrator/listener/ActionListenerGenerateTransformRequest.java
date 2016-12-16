package br.com.gvt.integrator.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import org.apache.log4j.Logger;
import org.xml.sax.SAXParseException;

import br.com.gvt.integrator.ui.component.TabPanelBillingInfoPanel;
import br.com.gvt.integrator.utils.Resources;
import br.com.gvt.integrator.utils.SwingUtils;
import br.com.gvt.integrator.utils.XmlUtils;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class ActionListenerGenerateTransformRequest implements ActionListener {
	private JFrame _parent;
	private TabPanelBillingInfoPanel _infoPanel;
	private JTextPane _textPaneBillingInfo;
	
	final static Logger _logger = Logger.getLogger(ActionListenerGenerateTransformRequest.class);
	
	
	
	
	public ActionListenerGenerateTransformRequest(JFrame parent, TabPanelBillingInfoPanel infoPanel,
			JTextPane textPaneBillingInfo) {
		_parent = parent;
		_infoPanel = infoPanel;
		_textPaneBillingInfo = textPaneBillingInfo;
	}
	
	
	
	
	private String getAccountId(String request) {
		int beginIndex = request.indexOf("<Id>") + 4;
		int endIndex = request.indexOf("</Id>");
		return request.substring(beginIndex, endIndex);
	}
	
	
	
	
	private String transformRequest(String billingInfo) {
		// Check is its a Modified Account.
		boolean isModifiedAccount = billingInfo.indexOf("<GetBillingProfileInfoOut>") > 0;
		
		// Remove headers.
		int beginIndex;
		int endIndex;
		String result;
		if (isModifiedAccount) {
			beginIndex = billingInfo.indexOf("<GetBillingProfileInfoOut>") + 26;
			endIndex = billingInfo.indexOf("</GetBillingProfileInfoOut>");
			result = billingInfo.substring(beginIndex, endIndex);
			
			// TODO Resolver melhor este problema. Pegar a data de aprovisionamento em algum lugar, ou ver qual o
			// impacto de se inserir uma data fixa ou atual no caso das contas MA.
			// Add mandatory fields that are not returned from GetAccountProfile webservice.
			result = "<Numero>" + getAccountId(billingInfo) + "</Numero> " +
					"<DataAprovisionamento>2015-01-01T08:20:26</DataAprovisionamento> " +
					"<Tipo>Modify Account</Tipo>" + result;
		}
		else {
			beginIndex = billingInfo.indexOf("<Ordem>") + 7;
			endIndex = billingInfo.indexOf("</Ordem>");
			result = billingInfo.substring(beginIndex, endIndex);
		}
		
		// Set namespace prefix on starting and ending tags.
		result = result.replaceAll("</", "</cus:");
		result = result.replaceAll("<([a-zA-Z])", "<cus:$1");
		
		// Add new namespaces.
		result = "<transforme xmlns=\"http://billingRetail.gvt.com.br/\" "
				+ " xmlns:cus=\"http://www.gvt.com.br/siebel/customerorderout\">"
				+ "\n<crmData xmlns=\"\">".concat(result).concat("</crmData></transforme>");
		return result;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		_logger.info("Start.");
		
		// Disable action button to avoid a second call.
		_infoPanel.getInfoPanelBottom().getButtonGenerate().setEnabled(false);
		
		try {
			// Check for request on the Billing Info panel.
			String request = _textPaneBillingInfo.getText();
			
			// Get the billing info.
			if (request.isEmpty()) {
				JOptionPane.showMessageDialog(_parent,
						Resources.INSTANCE.getString("error_no_request_transform"),
						Resources.INSTANCE.getString("error"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// Clear text panes.
			SwingUtils.cleanTabbedPaneTextPanes((JTabbedPane) _infoPanel.getComponent(0));
			
			// Focus on the request tab.
			((JTabbedPane) _infoPanel.getComponent(0)).setSelectedIndex(0);
			
			_logger.debug("Creating request.");
			
			// Transform the request.
			request = transformRequest(request);
			
			_logger.debug("Showing request.");
			
			// Show the request.
			_infoPanel.getTextPaneRequest().setText(XmlUtils.format(request));
			_infoPanel.getTextPaneRequest().setCaretPosition(0);
			
			_logger.info("Success!");
		}
		catch (StringIndexOutOfBoundsException e) {
			_logger.error(e.getMessage());
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(_parent,
					Resources.INSTANCE.getString("error_response_billing_invalid") + e.getMessage(),
					Resources.INSTANCE.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		catch (Exception e) {
			_logger.error(e.getCause().getMessage());
			e.printStackTrace();
			
			// Check if the cause of the exception is a SAX parse.
			if (e.getCause() instanceof SAXParseException) {
				
				// Get sax parse exception.
				SAXParseException saxParseException = (SAXParseException) e.getCause();
				
				// Inform.
				JOptionPane.showMessageDialog(_parent,
						Resources.INSTANCE.getString("error_invalid_xml") +
								e.getCause().getMessage() + "\n" +
								Resources.INSTANCE.getString("line") +
								saxParseException.getLineNumber() + ", " +
								Resources.INSTANCE.getString("column") +
								saxParseException.getColumnNumber(),
						Resources.INSTANCE.getString("error"),
						JOptionPane.ERROR_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(_parent,
						Resources.INSTANCE.getString("error_invalid_xml") + e.getCause().getMessage(),
						Resources.INSTANCE.getString("error"),
						JOptionPane.ERROR_MESSAGE);
			}
		}
		finally {
			_infoPanel.getInfoPanelBottom().getButtonGenerate().setEnabled(true);
		}
	}
	
}
