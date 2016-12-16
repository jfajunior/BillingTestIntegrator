package br.com.gvt.integrator.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.enums.Environment;
import br.com.gvt.integrator.ui.component.ProgressDialog;
import br.com.gvt.integrator.ui.component.TabPanelBillingInfoPanel;
import br.com.gvt.integrator.ui.component.TabPanelSettingsEnvironmentsPanel;
import br.com.gvt.integrator.utils.Constants;
import br.com.gvt.integrator.utils.NetworkUtils;
import br.com.gvt.integrator.utils.Resources;
import br.com.gvt.integrator.utils.SoapUtils;
import br.com.gvt.integrator.utils.SwingUtils;
import br.com.gvt.integrator.utils.XmlUtils;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class ActionListenerCallGetBillingInfo implements ActionListener {
	private JFrame _parent;
	private TabPanelBillingInfoPanel _infoPanel;
	private TabPanelSettingsEnvironmentsPanel _tabPanelSettings;
	
	final static Logger _logger = Logger.getLogger(ActionListenerCallGetBillingInfo.class);
	
	
	
	
	public ActionListenerCallGetBillingInfo(JFrame parent, TabPanelBillingInfoPanel infoPanel,
			TabPanelSettingsEnvironmentsPanel settingsTabPanel) {
		_parent = parent;
		_infoPanel = infoPanel;
		_tabPanelSettings = settingsTabPanel;
	}
	
	
	
	
	private String getIdFromSoapRequest(String soapRequestString, boolean isModifiedAccount) {
		if (isModifiedAccount) {
			return soapRequestString.substring(soapRequestString.indexOf("<coi:IdConta>") + 13,
					soapRequestString.indexOf("</coi:IdConta>"));
		}
		return soapRequestString.substring(soapRequestString.indexOf("<coi:NumeroOrdem>") + 17,
				soapRequestString.indexOf("</coi:NumeroOrdem>"));
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		_logger.info("Start.");
		
		// Disable action button to avoid a second call.
		_infoPanel.getInfoPanelBottom().getButtonAction().setEnabled(false);
		
		new Thread() {
			@Override
			public void run() {
				
				ProgressDialog dialog = null;
				String customerInfoUrl = null;
				try {
					// Get request from the request tab of billing panel.
					String request = _infoPanel.getTextPaneRequest().getText();
					
					// Check for empty request.
					if (request.isEmpty()) {
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_no_request_billing"),
								Resources.INSTANCE.getString("error"),
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// Check what service to call: GetAccountProfile or GetCustomerOrderBillInfo.
					boolean isModifiedAccount = request.indexOf("<coi:GetBillingProfileInfoIn>") > 0;
					
					// Get environment URL.
					Environment environment;
					String soapAction;
					if (isModifiedAccount) {
						environment = (Environment) _infoPanel.getInfoPanelBottom().getComboBoxEnvironment().getSelectedItem();
						customerInfoUrl = _tabPanelSettings.getPanel(environment).getUrlSiebelAccountProfile();
						soapAction = Constants.SOAP_ACTION_FOR_GET_CUSTOMER_ORDER_BILL_PROFILE_INFO_OPERATION;
					}
					else {
						environment = (Environment) _infoPanel.getInfoPanelBottom().getComboBoxEnvironment().getSelectedItem();
						customerInfoUrl = _tabPanelSettings.getPanel(environment).getUrlSiebelCustomerOrder();
						soapAction = Constants.SOAP_ACTION_FOR_GET_CUSTOMER_ORDER_BILL_INFO_OPERATION;
					}
					
					// Check for empty URL.
					if (customerInfoUrl.length() == 0) {
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_no_url"),
								Resources.INSTANCE.getString("error"),
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// Validate URL.
					NetworkUtils.validateURL(customerInfoUrl);
					_logger.debug("URL validated: " + customerInfoUrl);
					
					// Create a progress dialog.
					dialog = new ProgressDialog(_parent, Resources.INSTANCE.getString("progress"));
					dialog.updateLabel(Resources.INSTANCE.getString("getting_data"));
					dialog.updateProgressMaximumValue(30);
					
					// Clear text panes.
					SwingUtils.cleanTabbedPaneTextPanes((JTabbedPane) _infoPanel.getComponent(0), 1, 2);
					
					// Create the SOAP request.
					dialog.updateLabel(Resources.INSTANCE.getString("creating_soap_request"));
					SOAPMessage soapRequest = SoapUtils.getSoapMessageFromSoapMessageString(request, soapAction);
					
					// Print the SOAP request.
					String soapRequestString = SoapUtils.getSoapMessageStringFromSoapMessage(soapRequest);
					soapRequestString = XmlUtils.format(soapRequestString);
					
					_logger.debug("SOAP Message Request from caller: " + soapRequestString);
					
					// Execute SOAP call.
					dialog.updateLabel(Resources.INSTANCE.getString("calling_get_billing_info"));
					dialog.updateProgressValues(20, 70);
					
					// Check if dialog is still running.
					if (dialog.isRunning()) {
						dialog.disableCancel();

						// Get the server response.
						SOAPMessage soapResponse = SoapUtils.executeSoapCall(customerInfoUrl, soapRequest);
						
						_logger.info("Got Billing Info response!");
						
						dialog.updateLabel(Resources.INSTANCE.getString("processing_response"));
						dialog.updateProgressValues(70, 94);
						
						String soapResponseString = SoapUtils.getSoapMessageStringFromSoapMessage(soapResponse);
						
						_logger.trace("Billing Info: " + soapResponseString);
						
						// Format.
						dialog.updateLabel(Resources.INSTANCE.getString("formatting"));
						soapResponseString = XmlUtils.format(soapResponseString, true);
						
						// Focus on the response tab.
						((JTabbedPane) _infoPanel.getComponent(0)).setSelectedIndex(1);
						
						// Show the response.
						_infoPanel.getTextPaneResponse().setText(soapResponseString);
						
						// Show the info.
						_infoPanel.getTextPaneInfo().setText("Request Id: " + getIdFromSoapRequest(soapRequestString, isModifiedAccount));
						
						dialog.updateLabel(Resources.INSTANCE.getString("done"));
						dialog.updateProgressValues(98, 100);
					}
				}
				catch (MalformedURLException e) {
					ProgressDialog.stop(dialog);
					_logger.error(e.getMessage());
					e.printStackTrace();
					
					JOptionPane.showMessageDialog(_parent,
							Resources.INSTANCE.getString("error_invalid_url") + customerInfoUrl,
							Resources.INSTANCE.getString("error"),
							JOptionPane.ERROR_MESSAGE);
				}
				catch (IOException e) {
					ProgressDialog.stop(dialog);
					_logger.error(e.getMessage());
					e.printStackTrace();
					
					JOptionPane.showMessageDialog(_parent,
							Resources.INSTANCE.getString("error_unable_establish_connection") + e.getMessage() +
									"\nURL: " + customerInfoUrl,
							Resources.INSTANCE.getString("error"),
							JOptionPane.ERROR_MESSAGE);
				}
				catch (SOAPException e) {
					ProgressDialog.stop(dialog);
					_logger.error(e.getMessage());
					e.printStackTrace();
					
					JOptionPane.showMessageDialog(_parent,
							Resources.INSTANCE.getString("error_soap_request") + e.getMessage(),
							Resources.INSTANCE.getString("error"),
							JOptionPane.ERROR_MESSAGE);
				}
				catch (Exception e) {
					ProgressDialog.stop(dialog);
					
					// Treat error message.
					String errorMessage = e.getMessage();
					_logger.error(errorMessage);
					e.printStackTrace();
					
					// Get just first line.
					errorMessage = errorMessage.substring(0, errorMessage.indexOf("\n"));
					
					if (errorMessage.contains("InaccessibleWSDLException")) {
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_service_not_found") + e.getMessage() + "URL: " +
										customerInfoUrl,
								Resources.INSTANCE.getString("error"),
								JOptionPane.ERROR_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_unknown") + e.getMessage(),
								Resources.INSTANCE.getString("error"),
								JOptionPane.ERROR_MESSAGE);
					}
				}
				finally {
					_infoPanel.getInfoPanelBottom().getButtonAction().setEnabled(true);
				}
			}
		}.start();
		
		_logger.info("Success!");
	}
	
}
