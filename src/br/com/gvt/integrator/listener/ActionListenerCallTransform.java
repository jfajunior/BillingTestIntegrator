package br.com.gvt.integrator.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.xml.sax.SAXParseException;

import br.com.gvt.integrator.engine.CallerTransformer;
import br.com.gvt.integrator.enums.Environment;
import br.com.gvt.integrator.ui.component.ProgressDialog;
import br.com.gvt.integrator.ui.component.TabPanelBillingInfoPanel;
import br.com.gvt.integrator.ui.component.TabPanelSettingsEnvironmentsPanel;
import br.com.gvt.integrator.utils.NetworkUtils;
import br.com.gvt.integrator.utils.Resources;
import br.com.gvt.integrator.utils.SwingUtils;
import br.com.gvt.stubs.BillingOrder;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class ActionListenerCallTransform implements ActionListener {
	private JFrame _parent;
	private TabPanelBillingInfoPanel _infoPanel;
	private TabPanelSettingsEnvironmentsPanel _tabPanelSettings;
	
	final static Logger _logger = Logger.getLogger(ActionListenerCallTransform.class);
	
	
	
	
	public ActionListenerCallTransform(JFrame parent, TabPanelBillingInfoPanel infoPanel,
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
				String transformerUrl = null;
				try {
					// Get request from the request tab of transform panel.
					String request = _infoPanel.getTextPaneRequest().getText();
					
					// Check for empty request.
					if (request.isEmpty()) {
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_no_request_transform"),
								Resources.INSTANCE.getString("error"),
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// Get environment URL.
					Environment environment = (Environment) _infoPanel.getInfoPanelBottom().getComboBoxEnvironment().getSelectedItem();					
					transformerUrl = _tabPanelSettings.getPanel(environment).getUrlTransform();
					
					// Check for empty URL.
					if (transformerUrl.length() == 0) {
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_no_url"),
								Resources.INSTANCE.getString("error"),
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// Validate URL.
					NetworkUtils.validateURL(transformerUrl);
					_logger.debug("URL validated: " + transformerUrl);
					
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
					
					// Clear text panes.
					SwingUtils.cleanTabbedPaneTextPanes((JTabbedPane) _infoPanel.getComponent(0), 1, 2);
					
					// Transform the billing info to the right format.
					dialog.updateLabel(Resources.INSTANCE.getString("transform_billing_info"));
					
					// Get the transformer caller and transform the billing info.
					dialog.updateLabel(Resources.INSTANCE.getString("calling_transform"));
					dialog.updateProgressValues(20, 70);
					
					CallerTransformer caller = new CallerTransformer(transformerUrl);
					BillingOrder billingOrder = caller.transform(request);
					
					_logger.info("Got Transform response!");
					_logger.trace("Billing Order: " + billingOrder);
					
					// Marshal the server response.
					dialog.updateLabel(Resources.INSTANCE.getString("marshalling_bo"));
					dialog.updateProgressValues(70, 94);
					
					JAXBContext jc = JAXBContext.newInstance(BillingOrder.class);
					Marshaller marshaller = jc.createMarshaller();
					marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
							"http://www.w3.org/2001/XMLSchema-instance");
					// marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
					
					StringWriter sw = new StringWriter();
					marshaller.marshal(billingOrder, sw);
					
					// Focus on the response tab.
					((JTabbedPane) _infoPanel.getComponent(0)).setSelectedIndex(1);
					
					// Show the response.
					_infoPanel.getTextPaneResponse().setText(sw.toString());
					
					// Show the info.
					_infoPanel.getTextPaneInfo().setText(billingOrder.toString());
					
					dialog.updateLabel(Resources.INSTANCE.getString("done"));
					dialog.updateProgressValues(98, 100);
					
					_logger.info("Success!");
				}
				catch (MalformedURLException e) {
					ProgressDialog.stop(dialog);
					_logger.error(e.getMessage());
					e.printStackTrace();
					
					JOptionPane.showMessageDialog(_parent,
							Resources.INSTANCE.getString("error_invalid_url") + transformerUrl,
							Resources.INSTANCE.getString("error"),
							JOptionPane.ERROR_MESSAGE);
				}
				catch (IOException e) {
					ProgressDialog.stop(dialog);
					_logger.error(e.getMessage());
					e.printStackTrace();
					
					JOptionPane.showMessageDialog(_parent,
							Resources.INSTANCE.getString("not_possible_to_establish_connection") + e.getMessage() +
									"\nURL: " + transformerUrl,
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
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_response_transform") +
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
								Resources.INSTANCE.getString("error_response_transform") + e.getMessage(),
								Resources.INSTANCE.getString("error"),
								JOptionPane.ERROR_MESSAGE);
					}
				}
				catch (Exception e) {
					ProgressDialog.stop(dialog);
					
					// Treat error message.
					String errorMessage = e.getMessage();
					_logger.error(errorMessage);
					e.printStackTrace();
					
					// Get just the first line.
					int endOfLineIndex = errorMessage.indexOf("\n");
					if (endOfLineIndex > 0) {
						errorMessage = errorMessage.substring(0, endOfLineIndex);
					}
					
					// Remove unnecessary data.
					int exceptionIndex = errorMessage.lastIndexOf("Exception:");
					if (exceptionIndex > 0) {
						errorMessage = errorMessage.substring(errorMessage.lastIndexOf("Exception:") + 10);
					}
					
					if (errorMessage.contains("InaccessibleWSDLException")) {
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_service_not_found") + errorMessage + "URL: " +
										transformerUrl,
								Resources.INSTANCE.getString("error"),
								JOptionPane.ERROR_MESSAGE);
					}
					else if (errorMessage.contains("is not a valid service")) {
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_invalid_service") +
										errorMessage.replaceFirst("Valid", "\nValid"),
								Resources.INSTANCE.getString("error"),
								JOptionPane.ERROR_MESSAGE);
					}
					else if (errorMessage.contains("It failed with")) {
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_invalid_service") +
										errorMessage.substring(0, errorMessage.indexOf("It failed with")),
								Resources.INSTANCE.getString("error"),
								JOptionPane.ERROR_MESSAGE);
					}
					else if (errorMessage.contains("Já existe um outro processo")) {
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_transform") +
										errorMessage.replaceFirst("\\.", ".\n"),
								Resources.INSTANCE.getString("error"),
								JOptionPane.ERROR_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(_parent,
								Resources.INSTANCE.getString("error_unknown") + errorMessage,
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
