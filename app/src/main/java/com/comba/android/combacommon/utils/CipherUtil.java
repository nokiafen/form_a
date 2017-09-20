package com.comba.android.combacommon.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CipherUtil {

	/**
	 * md5加密
	 * 
	 * @param bytes
	 * @return String
	 */
	public static String toMd5(byte[] bytes) {
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(bytes);
			return toHexString(algorithm.digest(), "");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String toHexString(byte[] bytes, String separator) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : bytes) {
			String a = Integer.toHexString(0xFF & b);
			if (a.length() == 1)
				a = "0" + a;
			hexString.append(a).append(separator);
		}
		return hexString.toString();
	}
}
