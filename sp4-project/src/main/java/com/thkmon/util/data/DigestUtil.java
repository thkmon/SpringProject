package com.thkmon.util.data;

import java.security.MessageDigest;

public class DigestUtil {

	
	public String convertToMD5(String str) throws Exception {
		return convertToType(str, "MD5");
	}
	
	
	public String convertToSHA256(String str) throws Exception {
		return convertToType(str, "SHA-256");
	}
	
	
	private String convertToType(String str, String type) throws Exception {
		if (str == null || str.length() == 0) {
			new Exception("DigestUtil convertToType : str is null or empty.");
		}
		
		if (type == null || type.length() == 0) {
			type = "SHA-256";
		}
		
		String result = "";
		StringBuffer buff = new StringBuffer(); 
		MessageDigest digest = null;
		
		try {
			digest = MessageDigest.getInstance(type); 
			byte[] hash = digest.digest(str.getBytes("UTF-8"));
			int len = hash.length;

			for (int i=0 ; i<len; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1) {
					buff.append("0");
				}

				buff.append(hex);
			}

			result = buff.toString();

		} catch(Exception e) {
			throw e;
		}

		return result;
	}
}