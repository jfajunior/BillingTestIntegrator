package br.com.gvt.integrator.utils;


/**
 * Application constants.
 * 
 * @author José Júnior
 *         GVT - 2013.07
 */
public class Constants {
	public final static String SETTINGS_FILE_PATH_DATABASE = "integrator.database.settings";
	public final static String SETTINGS_FILE_PATH_ENVIRONMENT = "integrator.environment.settings";
	
	public final static String PATH_IMAGE_BTI = "/resources/images/bti.png";
	public final static String PATH_IMAGE_BTI_LOGO = "/resources/images/bti_logo.png";
	public final static String PATH_IMAGE_GVT = "/resources/images/gvt.png";
	public final static String PATH_IMAGE_FIND = "/resources/images/search.png";
	
	public final static String PATH_IMAGE_LOCK = "/resources/images/lock.png";
	public final static String PATH_IMAGE_UNLOCK = "/resources/images/unlock.png";
	
	
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

	
	public static final String CUSTOMER_ORDER_NS_PREFIX = "co";	
	public static final String CUSTOMER_ORDER_IN_NS_PREFIX = "coi";
	public static final String CUSTOMER_ORDER_NS_URI = "http://www.gvt.com.br/siebel/customerorder";
	public static final String CUSTOMER_ORDER_IN_NS_URI = "http://www.gvt.com.br/siebel/customerorderin";
	
	public static final String SOAP_ACTION_FOR_GET_CUSTOMER_ORDER_BILL_INFO_OPERATION =
			"rpc/http://www.gvt.com.br/siebel/customerorder:GetCustomerOrderBillInfo";
	
	public static final String SOAP_ACTION_FOR_GET_CUSTOMER_ORDER_BILL_PROFILE_INFO_OPERATION =
			"rpc/http://www.gvt.com.br/siebel/customerorder:GetBillingProfileInfo";
	
	public static final String CHARSET_ENCODING = "UTF-8";
	
	
}
