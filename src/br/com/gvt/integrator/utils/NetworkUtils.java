package br.com.gvt.integrator.utils;


import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;




/**
 * NetworkUtils helper class.
 * @author José Júnior
 *         GVT - 2014.08
 * 
 */
public class NetworkUtils {
	private static final Logger _logger = Logger.getLogger(NetworkUtils.class);
	
	
	
	
	public static URL getUrl(String urlString) throws MalformedURLException {
		URL url = null;
		try {
			url = new URL(urlString);
		}
		catch (MalformedURLException e) {
			_logger.error("Failed to create URL for: " + urlString);
			_logger.error("Exception message: " + e.getMessage());
			throw e;
		}
		return url;
	}
	
	
	
	
	public static void validateURL(String urlString) throws MalformedURLException {
		new URL(urlString);
	}
}
