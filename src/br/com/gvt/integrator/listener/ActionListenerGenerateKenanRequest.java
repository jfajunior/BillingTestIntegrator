package br.com.gvt.integrator.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;
import java.io.StringWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.xml.sax.SAXParseException;

import br.com.gvt.integrator.enums.KenanFlow;
import br.com.gvt.integrator.ui.component.TabPanelBillingInfoPanel;
import br.com.gvt.integrator.utils.Resources;
import br.com.gvt.integrator.utils.SwingUtils;
import br.com.gvt.stubs.BillingOrder;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class ActionListenerGenerateKenanRequest implements ActionListener {
	private JFrame _parent;
	private TabPanelBillingInfoPanel _infoPanel;
	private TabPanelBillingInfoPanel _infoPanelTransform;
	
	final static Logger _logger = Logger.getLogger(ActionListenerGenerateKenanRequest.class);
	
	public static final String DISMEMBERMENT_OF_OFFER = "Desmembramento de Oferta";
	public static final String JUNCTION_OF_OFFERS = "Junção de Ofertas";
	public static final String CHANGE_OF_OWNERSHIP = "Mudança de Titularidade";
	
	
	
	
	public ActionListenerGenerateKenanRequest(JFrame parent, TabPanelBillingInfoPanel infoPanel,
			TabPanelBillingInfoPanel infoPanelTransform) {
		_parent = parent;
		_infoPanel = infoPanel;
		_infoPanelTransform = infoPanelTransform;
	}
	
	
	
	
	private void adviceKenanFlow() {
		String transformRequest = _infoPanelTransform.getTextPaneRequest().getText();
		
		try {
			
			if (transformRequest != null && !transformRequest.isEmpty()) {
				int crmDataIndex = transformRequest.indexOf("crmData");
				int accountTypeIndex = transformRequest.indexOf("<cus:Tipo>", crmDataIndex) + 10;
				String accountType = transformRequest.substring(accountTypeIndex,
						transformRequest.indexOf("</cus:Tipo>", accountTypeIndex));
				
				_logger.debug("Account Type: " + accountType);
				
				// Set the default action.
				if (!(accountType.matches(".*" + DISMEMBERMENT_OF_OFFER + ".*") ||
						accountType.matches(".*" + JUNCTION_OF_OFFERS + ".*") || accountType.matches(".*" +
						CHANGE_OF_OWNERSHIP + ".*"))) {
					_infoPanel.getInfoPanelBottom().getComboBoxKenanFlow().setSelectedItem(KenanFlow.EXECUTE_ORDER);
					_infoPanel.getInfoPanelBottom().setKenanFlowAdvice(KenanFlow.EXECUTE_ORDER);
				}
				else {
					_infoPanel.getInfoPanelBottom().setKenanFlowAdvice(null);
				}
			}
			else {
				_logger.debug("Transform request is empty!");
			}
		}
		catch (Exception e) {
			_logger.info("No Kenan advice for you! " +
					"Exception while trying to get Billing Order Info from Transform request.");
			_logger.debug("Exception message: " + e.getMessage());
		}
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		_logger.info("Start.");
		
		// Disable action button to avoid a second call.
		_infoPanel.getInfoPanelBottom().getButtonGenerate().setEnabled(false);
		
		try {
			// Check for request on the Transform Info panel.
			String request = _infoPanelTransform.getTextPaneResponse().getText();
			
			// Get the billing info.
			if (request.isEmpty()) {
				JOptionPane.showMessageDialog(_parent,
						Resources.INSTANCE.getString("error_no_response_transform"),
						Resources.INSTANCE.getString("error"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// Clear text panes.
			SwingUtils.cleanTabbedPaneTextPanes((JTabbedPane) _infoPanel.getComponent(0));
			
			// Focus on the request tab.
			((JTabbedPane) _infoPanel.getComponent(0)).setSelectedIndex(0);
			
			_logger.debug("Creating request.");
			
			// Unarshal the billing order.
			JAXBContext jc = JAXBContext.newInstance(BillingOrder.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			StringReader reader = new StringReader(request);
			
			BillingOrder billingOrderRequest = (BillingOrder) unmarshaller.unmarshal(reader);
			
			// Clear the "Fidelitys" field for a correct call to Kenan.
			billingOrderRequest.getFidelitys().clear();
			
			jc = JAXBContext.newInstance(BillingOrder.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
					"http://www.w3.org/2001/XMLSchema-instance");
			// marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			
			_logger.debug("Showing request.");
			
			// Show the request.
			StringWriter sw = new StringWriter();
			marshaller.marshal(billingOrderRequest, sw);
			_infoPanel.getTextPaneRequest().setText(sw.toString());
			_infoPanel.getTextPaneRequest().setCaretPosition(0);
			
			// Advise user for the correct Kenan flow.
			adviceKenanFlow();
			
			_logger.info("Success!");
		}
		catch (UnmarshalException e) {
			e.printStackTrace();
			
			if (e.getLinkedException() instanceof SAXParseException) {
				_logger.error(e.getCause());
				
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
				_logger.error(e.getMessage());
				
				JOptionPane.showMessageDialog(_parent,
						Resources.INSTANCE.getString("error_response_transform_invalid"),
						Resources.INSTANCE.getString("error"),
						JOptionPane.ERROR_MESSAGE);
			}
		}
		catch (JAXBException e) {
			_logger.error(e.getMessage());
			e.printStackTrace();
			
			// Check if the cause of the exception is a SAX parse.
			if (e.getCause() instanceof SAXParseException) {
				
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
				_logger.error(e.getMessage());
				
				JOptionPane.showMessageDialog(_parent,
						Resources.INSTANCE.getString("error_marshalling") + e.getCause().getMessage(),
						Resources.INSTANCE.getString("error"),
						JOptionPane.ERROR_MESSAGE);
			}
		}
		finally {
			_infoPanel.getInfoPanelBottom().getButtonGenerate().setEnabled(true);
		}
		
	}
	
}
