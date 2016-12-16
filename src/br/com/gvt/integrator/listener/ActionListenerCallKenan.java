package br.com.gvt.integrator.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.xml.sax.SAXParseException;

import br.com.gvt.integrator.engine.CallerKenan;
import br.com.gvt.integrator.enums.Environment;
import br.com.gvt.integrator.enums.KenanFlow;
import br.com.gvt.integrator.ui.component.ProgressDialog;
import br.com.gvt.integrator.ui.component.TabPanelBillingInfoPanel;
import br.com.gvt.integrator.ui.component.TabPanelSettingsEnvironmentsPanel;
import br.com.gvt.integrator.utils.NetworkUtils;
import br.com.gvt.integrator.utils.Resources;
import br.com.gvt.integrator.utils.SwingUtils;
import br.com.gvt.stubs.Account;
import br.com.gvt.stubs.BillingOrder;
import br.com.gvt.stubs.GvtCheckRunningBipIn;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class ActionListenerCallKenan implements ActionListener {
	private JFrame _parent;
	private TabPanelBillingInfoPanel _infoPanel;
	private TabPanelSettingsEnvironmentsPanel _tabPanelSettings;
	
	final static Logger _logger = Logger.getLogger(ActionListenerCallKenan.class);
	
	
	
	
	public ActionListenerCallKenan(JFrame parent, TabPanelBillingInfoPanel infoPanel,
			TabPanelSettingsEnvironmentsPanel settingsTabPanel) {
		_parent = parent;
		_infoPanel = infoPanel;
		_tabPanelSettings = settingsTabPanel;
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
				String kenanUrl = null;
				boolean marshalled = false;
				try {
					// Get request from the request tab of kenan panel.
					String request = _infoPanel.getTextPaneRequest().getText();
					
					// Check for empty request.
					if (request.isEmpty()) {
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_no_request_kenan"),
								Resources.INSTANCE.getString("error"),
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// Get environment URL.
					Environment environment =
							(Environment) _infoPanel.getInfoPanelBottom().getComboBoxEnvironment().getSelectedItem();
					
					kenanUrl = _tabPanelSettings.getPanel(environment).getUrlKenan();
					
					// Check for empty URL.
					if (kenanUrl.length() == 0) {
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_no_url"),
								Resources.INSTANCE.getString("error"),
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// Validate URL.
					NetworkUtils.validateURL(kenanUrl);
					_logger.debug("URL validated: " + kenanUrl);
					
					// Check and warn for production environment!
					if (environment.equals(Environment.PRODUCTION)) {
						int option = JOptionPane.showConfirmDialog(
								_parent,
								Resources.INSTANCE.getString("production_environment_warning"),
								Resources.INSTANCE.getString("warning"),
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE
								);
						
						if (option == JOptionPane.YES_OPTION) {
							_logger.info("Going for a production environment call!");
						}
						else {
							_logger.info("User rejected production environment call!");
							return;
						}
					}
					
					// Create a progress dialog.
					dialog = new ProgressDialog(_parent, Resources.INSTANCE.getString("progress"));
					dialog.updateLabel(Resources.INSTANCE.getString("getting_data"));
					dialog.updateProgressMaximumValue(30);
					
					// Clean text panes.
					SwingUtils.cleanTabbedPaneTextPanes((JTabbedPane) _infoPanel.getComponent(0), 1, 2);
					
					// Get the selected Kenan flow.
					KenanFlow kenanFlowSelected =
							(KenanFlow) _infoPanel.getInfoPanelBottom().getComboBoxKenanFlow().getSelectedItem();
					KenanFlow kenanFlowAdvice = (KenanFlow) _infoPanel.getInfoPanelBottom().getKenanFlowAdvice();
					
					// Check and warn for Kenan flow different than recommended!
					if (kenanFlowAdvice != null && kenanFlowAdvice != kenanFlowSelected) {
						int option = JOptionPane.showConfirmDialog(
								_parent,
								String.format(Resources.INSTANCE.getString("change_to_recommended_kenan_flow"),
										kenanFlowAdvice),
								Resources.INSTANCE.getString("warning"),
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE
								);
						
						if (option == JOptionPane.YES_OPTION) {
							_logger.info("User accepted recommended Kenan flow! Changing to " + kenanFlowAdvice);
							_infoPanel.getInfoPanelBottom().getComboBoxKenanFlow().setSelectedItem(kenanFlowAdvice);
						}
						else {
							_logger.info("User rejected recommended Kenan flow! Using " + kenanFlowSelected);
						}
					}
					else {
						_logger.info("No advice about Kenan flow.");
					}
					
					// Unarshal the billing order.
					dialog.updateLabel(Resources.INSTANCE.getString("unmarshalling_bo"));
					dialog.updateProgressValues(10, 20);
					
					JAXBContext jc = JAXBContext.newInstance(BillingOrder.class);
					Unmarshaller unmarshaller = jc.createUnmarshaller();
					StringReader reader = new StringReader(request);
					BillingOrder billingOrderRequest = (BillingOrder) unmarshaller.unmarshal(reader);
					
					CallerKenan caller = new CallerKenan(kenanUrl);
					
					// Check billing cycle for client accounts
					dialog.updateLabel(Resources.INSTANCE.getString("check_bip_running"));
					dialog.updateProgressValues(20, 30);
					
					// Check if a cycle of one of these Accounts in the billing order request is running.
					List<Account> boRequestAccounts = billingOrderRequest.getAccounts();
					if (!boRequestAccounts.isEmpty()) {
						
						for (Account account : boRequestAccounts) {
							GvtCheckRunningBipIn in = new GvtCheckRunningBipIn();
							in.setVExternalId(account.getAccountExternalId());
							in.setVServerId(account.getServerId());
							in.setVBillPeriod(account.getBillPeriod());
							
							if (caller.callKenanCheckBipRunning(in)) {
								
								int option = JOptionPane.showConfirmDialog(
										_parent,
										Resources.INSTANCE.getString("confirm_account_bip_running.message"),
										Resources.INSTANCE.getString("warning"),
										JOptionPane.YES_NO_OPTION,
										JOptionPane.WARNING_MESSAGE
										);
								
								if (option == JOptionPane.YES_OPTION) {
									_logger.info("Going to execute order through even billing cycle running for the account [" +
											account.getAccountExternalId() + "]!");
									break;
								}
								else {
									_logger.info("User rejected execute order because the client account is in billing cycle!");
									ProgressDialog.stop(dialog);
									return;
								}
							}
						}
					}
					
					// Get the Kenan caller and send the billing info.
					dialog.updateLabel(Resources.INSTANCE.getString("calling_kenan"));
					dialog.updateProgressValues(30, 70);
					
					// Call the selected Kenan flow.
					BillingOrder billingOrderResponse = null;
					switch (kenanFlowSelected) {
						case EXECUTE_ORDER:
							billingOrderResponse = caller.callKenan(billingOrderRequest);
							_logger.info("Call Kenan: Execute default.");
							break;
						case SERVICE_TRANSFER:
							billingOrderResponse = caller.callKenanServiceTransfer(billingOrderRequest);
							_logger.info("Call Kenan: Execute Service Transfer.");
							break;
						case SERVICE_BY_SERVICE:
							billingOrderResponse = caller.callKenanServiceByService(billingOrderRequest);
							_logger.info("Call Kenan: Execute Service By Service.");
							break;
						default:
							throw new Exception("Invalid Kenan flow!");
					}
					
					_logger.info("Got Kenan response!");
					_logger.trace("Billing Order: " + billingOrderResponse);
					
					// Marshal the server response.
					marshalled = true;
					dialog.updateLabel(Resources.INSTANCE.getString("marshalling_bo"));
					dialog.updateProgressValues(70, 94);
					
					jc = JAXBContext.newInstance(BillingOrder.class);
					Marshaller marshaller = jc.createMarshaller();
					marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
							"http://www.w3.org/2001/XMLSchema-instance");
					// marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
					
					// Focus on the response tab.
					((JTabbedPane) _infoPanel.getComponent(0)).setSelectedIndex(1);
					
					// Show the response.
					StringWriter sw = new StringWriter();
					marshaller.marshal(billingOrderResponse, sw);
					_infoPanel.getTextPaneResponse().setText(sw.toString());
					
					// Show the info.
					_infoPanel.getTextPaneInfo().setText(billingOrderResponse.toString());
					
					dialog.updateLabel(Resources.INSTANCE.getString("done"));
					dialog.updateProgressValues(98, 100);
					
					_logger.info("Success!");
				}
				catch (MalformedURLException e) {
					ProgressDialog.stop(dialog);
					_logger.error(e.getMessage());
					e.printStackTrace();
					
					JOptionPane.showMessageDialog(_parent,
							Resources.INSTANCE.getString("error_invalid_url") + kenanUrl,
							Resources.INSTANCE.getString("error"),
							JOptionPane.ERROR_MESSAGE);
				}
				catch (IOException e) {
					ProgressDialog.stop(dialog);
					_logger.error(e.getMessage());
					e.printStackTrace();
					
					JOptionPane.showMessageDialog(_parent,
							Resources.INSTANCE.getString("not_possible_to_establish_connection") + e.getMessage() +
									"\nURL: " + kenanUrl,
							Resources.INSTANCE.getString("error"),
							JOptionPane.ERROR_MESSAGE);
				}
				catch (JAXBException e) {
					ProgressDialog.stop(dialog);
					_logger.error(e.getMessage());
					e.printStackTrace();
					
					// Check if the cause of the exception is a SAX parse.
					if (e.getCause() instanceof SAXParseException) {
						_logger.error(e.getMessage());
						
						// Get sax parse exception.
						SAXParseException saxParseException = (SAXParseException) e.getCause();
						
						// Inform.
						String errorMessage = null;
						errorMessage = marshalled ? Resources.INSTANCE.getString("error_response_kenan")
								: Resources.INSTANCE.getString("error_response_transform");
						
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_response_transform") +
										errorMessage + e.getCause().getMessage() + "\n" +
										Resources.INSTANCE.getString("line") +
										saxParseException.getLineNumber() + ", " +
										Resources.INSTANCE.getString("column") +
										saxParseException.getColumnNumber(),
								Resources.INSTANCE.getString("error"),
								JOptionPane.ERROR_MESSAGE);
					}
					else {
						_logger.error(e.getMessage());
						
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_marshalling") + e.getCause().getMessage(),
								Resources.INSTANCE.getString("error"),
								JOptionPane.ERROR_MESSAGE);
					}
				}
				catch (Exception e) {
					ProgressDialog.stop(dialog);
					_logger.error(e.getMessage());
					e.printStackTrace();
					
					// Treat error message.
					if (e.getMessage().contains("InaccessibleWSDLException")) {
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_invalid_url_or_service_down") + e.getMessage() +
										"URL: " + kenanUrl,
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
		
	}
	
}
