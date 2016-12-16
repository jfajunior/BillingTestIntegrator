package br.com.gvt.integrator.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.engine.SOAPClientAccountProfileSAAJ;
import br.com.gvt.integrator.engine.SOAPClientCustomerOrderSAAJ;
import br.com.gvt.integrator.ui.component.TabPanelBillingInfoPanel;
import br.com.gvt.integrator.utils.Resources;
import br.com.gvt.integrator.utils.SoapUtils;
import br.com.gvt.integrator.utils.SwingUtils;
import br.com.gvt.integrator.utils.XmlUtils;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class ActionListenerGenerateBillingRequest implements ActionListener {
	private JFrame _parent;
	private TabPanelBillingInfoPanel _infoPanel;
	private JTextPane _textPaneOrderId; 
	
	final static Logger _logger = Logger.getLogger(ActionListenerGenerateBillingRequest.class);
	
	
	
	
	public ActionListenerGenerateBillingRequest(JFrame parent, TabPanelBillingInfoPanel infoPanel,
			JTextPane textPaneOrderId) {
		_parent = parent;
		_infoPanel = infoPanel;
		_textPaneOrderId = textPaneOrderId;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		_logger.info("Start.");
		
		// Disable action button to avoid a second call.
		_infoPanel.getInfoPanelBottom().getButtonGenerate().setEnabled(false);
		
		try {
			// Get the order number.
			String orderId = _textPaneOrderId.getText();
			
			// Check for empty order number.
			if (orderId.length() == 0) {
				JOptionPane.showMessageDialog(_parent,
						Resources.INSTANCE.getString("error_order_id_cannot_be_empty"),
						Resources.INSTANCE.getString("error"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// Clear text panes.
			SwingUtils.cleanTabbedPaneTextPanes((JTabbedPane) _infoPanel.getComponent(0));
			
			// Focus on the request tab.
			((JTabbedPane) _infoPanel.getComponent(0)).setSelectedIndex(0);
			
			_logger.debug("Creating request.");
			
			// Check if it is a Modified Account to know what service to call: GetAccountProfile or GetCustomerOrderBillInfo.
			boolean isModifiedAccount = false;
			if (orderId.startsWith("MA")) {
				isModifiedAccount = true;
			}
			
			// Create the SOAP request.
			SOAPMessage soapRequest;
			if (isModifiedAccount) {
				soapRequest = SOAPClientAccountProfileSAAJ.createGetAccountProfileSOAPRequest(orderId);
			}
			else {
				soapRequest = SOAPClientCustomerOrderSAAJ.createGetCustomerOrderBillInfoSOAPRequest(orderId);
			}
			
			_logger.debug("Showing request.");
			
			// Show the request.
			_infoPanel.getTextPaneRequest().setText(XmlUtils.format(
					SoapUtils.getSoapMessageStringFromSoapMessage(soapRequest)));
			
			_logger.info("Success!");
		}
		catch (IOException | SOAPException e) {
			_logger.error(e.getMessage());
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(_parent,
					Resources.INSTANCE.getString("error_generating_soap_request") + e.getMessage(),
					Resources.INSTANCE.getString("error"),
					JOptionPane.ERROR_MESSAGE);
		}
		finally {
			_infoPanel.getInfoPanelBottom().getButtonGenerate().setEnabled(true);
		}
	}
	
}
