package br.com.gvt.integrator.utils;


import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;




/**
 * The class {@code Resources} creates a single point to retrieve the application resources. It implements the enum's
 * singleton design pattern.
 * 
 * Effective Java:
 * "This approach is functionally equivalent to the public field approach, except that it is more concise, provides the
 * serialization machinery for free, and provides an ironclad guarantee against multiple instantiation, even in the face
 * of sophisticated serialization or reflection attacks. While this approach has yet to be widely adopted, a
 * single-element enum type is the best way to implement a singleton."
 * 
 * @author José Júnior
 *         GVT - 2013.07
 */
public enum Resources {
	INSTANCE;
	
	/** Resource bundle for internationalized and accessible text */
	private ResourceBundle _bundle;
	private Locale _locale;
	
	
	private final Logger _logger = Logger.getLogger(Resources.class);
	
	
	
	
	/**
	 * Private constructor to initialize the resource bundle with the default locale.
	 */
	private Resources() {
		setLocale(Locale.getDefault());
	}
	
	
	
	
	/**
	 * Sets the resource bundle's locale.
	 * @param locale
	 *            The resource bundle locale to be set.
	 */
	public void setLocale(Locale locale) {
		if (_bundle == null || !locale.equals(_locale)) {
			_locale = locale;
			// _bundle = ResourceBundle.getBundle("resources.i18n.MessagesBundle", locale);
			_bundle = new ResourceBundleUtils().getResourceBundle("resources.i18n.MessagesBundle", locale);
			
			JOptionPane.setDefaultLocale(_locale);
			
			_logger.info("Got new resource bundle for language-country: " + _locale.getLanguage() + "-" +
					locale.getCountry());
		}
	}
	
	
	
	
	/**
	 * Returns a resource bundle string, given the resource key.
	 * @param key
	 *            The resource key.
	 * @return The string associated with the resource key.
	 */
	public String getString(String key) {
		String value = "empty";
		
		try {
			value = _bundle.getString(key);
		}
		catch (MissingResourceException e) {
			_logger.warn("Couldn't find value for: " + key);
		}
		return value;
	}
	
	
	
	
	/**
	 * Returns a mnemonic from the resource bundle.
	 * @param key
	 *            The resource key.
	 * @return The mnemonic for the key.
	 */
	public char getMnemonic(String key) {
		return (getString(key)).charAt(0);
	}
	
	
}
