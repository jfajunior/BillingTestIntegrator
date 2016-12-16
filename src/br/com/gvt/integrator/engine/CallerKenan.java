package br.com.gvt.integrator.engine;


import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.action.LookAndFeelAction;
import br.com.gvt.stubs.BillingAdapter;
import br.com.gvt.stubs.BillingAdapterService;
import br.com.gvt.stubs.BillingOrder;
import br.com.gvt.stubs.GvtCheckRunningBipIn;




/**
 * @author José Júnior
 * @author Fabio Bertini
 *         GVT - 2013.07
 */
public class CallerKenan {
	private BillingAdapter _adapterService;
	
	final static Logger _logger = Logger.getLogger(LookAndFeelAction.class);
	
	
	
	
	public CallerKenan(String urlKenan) throws JAXBException, MalformedURLException {
		_adapterService = new BillingAdapterService(new URL(urlKenan),
				new QName("http://adapter.billing.gvt.com/", "BillingAdapterService")).getBillingAdapterPort();
	}
	
	
	
	
	/**
	 * Call Kenan with the given billing order.
	 * @param billingOrder
	 *            The billing order to send to Kenan.
	 * @return The billing order returned by Kenan.
	 */
	public BillingOrder callKenan(BillingOrder billingOrder) {
		try {
			billingOrder = _adapterService.execute(billingOrder);
			if (billingOrder.isErro()) {
				_logger.error(billingOrder.getException());
			}
		}
		catch (Exception e) {
			_logger.error(e.getMessage());
		}
		return billingOrder;
	}
	
	
	
	
	/**
	 * Call Kenan Service Transfer with the given billing order.
	 * @param billingOrder
	 *            The billing order to send to Kenan.
	 * @return The billing order returned by Kenan.
	 */
	public BillingOrder callKenanServiceTransfer(BillingOrder billingOrder) {
		try {
			billingOrder = _adapterService.executeServiceTransfer(billingOrder);
			if (billingOrder.isErro()) {
				_logger.error(billingOrder.getException());
			}
		}
		catch (Exception e) {
			_logger.error(e.getMessage());
		}
		return billingOrder;
	}
	
	
	
	
	/**
	 * Call Kenan Service By Service with the given billing order.
	 * @param billingOrder
	 *            The billing order to send to Kenan.
	 * @return The billing order returned by Kenan.
	 */
	public BillingOrder callKenanServiceByService(BillingOrder billingOrder) {
		try {
			billingOrder = _adapterService.executeServiceByService(billingOrder);
			if (billingOrder.isErro()) {
				_logger.error(billingOrder.getException());
			}
		}
		catch (Exception e) {
			_logger.error(e.getMessage());
		}
		return billingOrder;
	}
	
	
	
	
	/**
	 * Call Kenan to validate billing cycle of the client account.
	 * @param in
	 *            The request to send to Kenan.
	 * @return
	 *         Return true if the client account is billing cycle
	 *         Return false if the client isn't billing cycle.
	 */
	public Boolean callKenanCheckBipRunning(GvtCheckRunningBipIn in) {
		try {
			return _adapterService.checkRunningBip(in);
		}
		catch (Exception e) {
			_logger.error(e.getMessage());
		}
		return false;
	}
}
