package br.com.gvt.integrator.action;


import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;

import br.com.gvt.integrator.ui.component.TabPanelBilling;
import br.com.gvt.integrator.ui.component.TabPanelBillingInfoPanel;
import br.com.gvt.integrator.utils.Resources;
import br.com.gvt.integrator.utils.Swing;
import br.com.gvt.integrator.utils.XmlUtils;




/**
 * @author José Júnior
 *         GVT - 2014.09
 */
public class SimulateTransformRequestFromExistingRequestAction extends AbstractAction {
	private static final long serialVersionUID = 5740404297404280968L;
	private final Logger _logger = Logger.getLogger(SimulateTransformRequestFromExistingRequestAction.class);
	
	private JFrame _mainFrame;
	private TabPanelBilling _panelTabBilling;
	
	public static final String TAG_ID = "cus:Id";
	public static final String TAG_NAME = "cus:Nome";
	public static final String TAG_ACTIVE = "cus:Ativo";
	public static final String TAG_CODE = "cus:Codigo";
	public static final String TAG_ACCOUNT = "cus:Conta";
	public static final String TAG_ACCOUNT_ID = "cus:IdConta";
	public static final String TAG_INVOICE_PROFILE = "cus:PerfilFatura";
	public static final String TAG_INVOICE_PROFILE_ID = "cus:IdPerfilFatura";
	public static final String TAG_PRODUCT = "cus:Produto";
	public static final String TAG_DESIGNATOR = "cus:Designador";
	public static final String TAG_PREVIOUS_DESIGNATOR = "cus:DesignadorAnterior";
	
	
	
	
	public SimulateTransformRequestFromExistingRequestAction(JFrame mainFrame, TabPanelBilling panelTabBilling) {
		_mainFrame = mainFrame;
		_panelTabBilling = panelTabBilling;
	}
	
	
	
	
	public static String getIntRandom(int length) {
		String random = "";
		for (int i = 0; i < length; i++) {
			random += (int) (Math.round(Math.random() * 9));
		}
		return random;
	}
	
	
	
	
	private static String generateNewAccountAndProfileId() {
		return "7-" + getIntRandom(9) + "GEN";
	}
	
	
	
	
	private static String generateNewAccountName(String accountName) {
		if (accountName.indexOf(" - GEN") > 0) {
			accountName = accountName.substring(0, accountName.indexOf(" - GEN"));
		}
		return accountName + " - GEN" + getIntRandom(6);
	}
	
	
	
	
	private static String generateNewDesignator(String designator) {
		if (designator.indexOf("GEN") > 0) {
			designator = designator.substring(0, designator.indexOf("GEN"));
		}
		return designator + "GEN" + getIntRandom(6);
	}
	
	
	
	
	private static String generateNewInvoiceProfileCode() {
		return "900" + getIntRandom(9);
	}
	
	
	
	
	private static Map<String, String> generateAccount(Document document) {
		// Get account node list.
		NodeList nodeListAccount = document.getElementsByTagName(TAG_ACCOUNT);
		List<Element> elementListAccount = XmlUtils.getElementsList(nodeListAccount);
		
		// Change each account id by a new generated random.
		String accountId;
		String newAccountId;
		String accountName;
		Hashtable<String, String> accountIdMap = new Hashtable<String, String>();
		for (Element elementAccount : elementListAccount) {
			accountId = XmlUtils.getTextValue(elementAccount, TAG_ID);
			newAccountId = generateNewAccountAndProfileId();
			if (!accountIdMap.containsKey(accountId)) {
				accountIdMap.put(accountId, newAccountId);
			}
			XmlUtils.setTextValue(elementAccount, TAG_ID, newAccountId);
			
			accountName = XmlUtils.getTextValue(elementAccount, TAG_NAME);
			XmlUtils.setTextValue(elementAccount, TAG_NAME, generateNewAccountName(accountName));
		}
		
		return accountIdMap;
	}
	
	
	
	
	private static Map<String, String> generateInvoiceProfile(Document document, Map<String, String> accountIdMap) {
		// Get invoice profile node list.
		NodeList nodeListInvoiceProfile = document.getElementsByTagName(TAG_INVOICE_PROFILE);
		List<Element> elementListInvoiceProfile = XmlUtils.getElementsList(nodeListInvoiceProfile);
		
		// Change each invoice profile codes by a new generated random.
		String accountId;
		String newAccountId;
		String invoiceProfileId;
		String newInvoiceProfileId;
		Hashtable<String, String> invoiceProfileIdMap = new Hashtable<String, String>();
		for (Element elementInvoiceProfile : elementListInvoiceProfile) {
			invoiceProfileId = XmlUtils.getTextValue(elementInvoiceProfile, TAG_ID);
			newInvoiceProfileId = generateNewAccountAndProfileId();
			if (!invoiceProfileIdMap.containsKey(invoiceProfileId)) {
				invoiceProfileIdMap.put(invoiceProfileId, newInvoiceProfileId);
			}
			XmlUtils.setTextValue(elementInvoiceProfile, TAG_ID, newInvoiceProfileId);
			XmlUtils.setTextValue(elementInvoiceProfile, TAG_CODE, generateNewInvoiceProfileCode());
			
			accountId = XmlUtils.getTextValue(elementInvoiceProfile, TAG_ACCOUNT_ID);
			newAccountId = accountIdMap.get(accountId);
			XmlUtils.setTextValue(elementInvoiceProfile, TAG_ACCOUNT_ID, newAccountId);
		}
		return invoiceProfileIdMap;
	}
	
	
	
	
	private static void generateProductAndActive(Document document, Map<String, String> invoiceProfileIdMap,
			NodeList nodeList) {
		// Get element node list.
		List<Element> elementList = XmlUtils.getElementsList(nodeList);
		
		// Change each product or active by a new generated random.
		String designator;
		String previousDesignator;
		String newDesignator = "";
		String invoiceProfileId;
		String newInvoiceProfileId;
		for (Element element : elementList) {
			invoiceProfileId = XmlUtils.getTextValue(element, TAG_INVOICE_PROFILE_ID);
			newInvoiceProfileId = invoiceProfileIdMap.get(invoiceProfileId);
			XmlUtils.setTextValue(element, TAG_INVOICE_PROFILE_ID, newInvoiceProfileId);
			
			designator = XmlUtils.getTextValue(element, TAG_DESIGNATOR);
			if (designator != null) {
				newDesignator = generateNewDesignator(designator);
				XmlUtils.setTextValue(element, TAG_DESIGNATOR, newDesignator);
			}
			
			previousDesignator = XmlUtils.getTextValue(element, TAG_PREVIOUS_DESIGNATOR);
			if (previousDesignator != null) {
				if (previousDesignator.equalsIgnoreCase(designator)) {
					XmlUtils.setTextValue(element, TAG_PREVIOUS_DESIGNATOR, newDesignator);
				}
				else {
					XmlUtils.setTextValue(element, TAG_PREVIOUS_DESIGNATOR, generateNewDesignator(previousDesignator));
				}
			}
		}
	}
	
	
	
	
	private static void generateProduct(Document document, Map<String, String> invoiceProfileIdMap) {
		// Get product list.
		NodeList nodeListProduct = document.getElementsByTagName(TAG_PRODUCT);
		generateProductAndActive(document, invoiceProfileIdMap, nodeListProduct);
	}
	
	
	
	
	private static void generateActive(Document document, Map<String, String> invoiceProfileIdMap) {
		// Get active list.
		NodeList nodeListActive = document.getElementsByTagName(TAG_ACTIVE);
		generateProductAndActive(document, invoiceProfileIdMap, nodeListActive);
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		TabPanelBillingInfoPanel infoPanelTransform = _panelTabBilling.getPanelTransform();
		
		// Check for request on the Transform Info panel.
		String request = infoPanelTransform.getTextPaneRequest().getText();
		
		// If the request is empty, there is nothing to do.
		if (request.isEmpty()) {
			JOptionPane.showMessageDialog(_mainFrame,
					Resources.INSTANCE.getString("error_no_request_transform"),
					Resources.INSTANCE.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Confirm new request generation.
		int option = JOptionPane.showConfirmDialog(
				_mainFrame,
				Resources.INSTANCE.getString("confirm_simulation.message"),
				Resources.INSTANCE.getString("confirm_simulation.title"),
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
				);
		
		// Get confirmation.
		if (option == JOptionPane.YES_OPTION) {
			
			try {
				// Clear text panes.
				Swing.cleanTabbedPaneTextPanes((JTabbedPane) infoPanelTransform.getComponent(0));
				
				// Focus on the request tab.
				((JTabbedPane) infoPanelTransform.getComponent(0)).setSelectedIndex(0);
				
				_logger.debug("Simulationg new transform request.");
				
				// Parse request.
				Document document = XmlUtils.parseXmlString(request);
				
				// Generate new account.
				Map<String, String> accountIdMap = generateAccount(document);
				
				// Generate new invoice profile.
				Map<String, String> invoiceProfileIdMap = generateInvoiceProfile(document, accountIdMap);
				
				// Generate new product.
				generateProduct(document, invoiceProfileIdMap);
				
				// Generate new active.
				generateActive(document, invoiceProfileIdMap);
				
				_logger.debug("Showing simulated request.");
				
				// Show the new generated request.
				infoPanelTransform.getTextPaneRequest().setText(XmlUtils.documentToString(document));
				
				// Show success message.
				JOptionPane.showMessageDialog(_mainFrame,
						Resources.INSTANCE.getString("transform_generated_success"),
						Resources.INSTANCE.getString("InfoDialog.title"),
						JOptionPane.INFORMATION_MESSAGE);
				
				_logger.info("Success!");
			}
			catch (Exception e) {
				_logger.error(e.getMessage());
				
				// Check if the cause of the exception is a SAX parse.
				if (e.getCause() instanceof SAXParseException) {
					
					// Get sax parse exception.
					SAXParseException saxParseException = (SAXParseException) e.getCause();
					
					// Inform.
					JOptionPane.showMessageDialog(_mainFrame,
							Resources.INSTANCE.getString("error_no_request_transform") +
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
					
					JOptionPane.showMessageDialog(_mainFrame,
							Resources.INSTANCE.getString("error_invalid_xml") + e.getCause().getMessage(),
							Resources.INSTANCE.getString("error"),
							JOptionPane.ERROR_MESSAGE);
				}
			}
			finally {
				infoPanelTransform.getInfoPanelBottom().getButtonGenerate().setEnabled(true);
			}
		}
	}
}
