package br.com.gvt.integrator.utils;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.xerces.impl.dv.util.Base64;





public final class MathUtils {
	
	
	public static String getIntRandom(int length) {
		String random = "";
		for (int i = 0; i < length; i++) {
			random += (int) (Math.round(Math.random() * 9));
		}
		return random;
	}
	
	
	
	
	public static String getDigestMD5(String input, int length) {
		String digest = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			digest = Base64.encode(md.digest(input.getBytes())).substring(0, 22).substring(0, length);
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return digest;
	}
	
	
	
	
	public static String getNumericDigestMD5(String input, int length) {
		String digest = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			digest = new BigInteger(1, md.digest(input.getBytes())).toString(10).substring(0, length);
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return digest;
	}
}
