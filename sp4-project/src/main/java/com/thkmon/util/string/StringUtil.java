package com.thkmon.util.string;

import java.util.ArrayList;

public class StringUtil {
	
	public static String revisePath(String path) {
		if (path == null) {
			return "";
		}
		
		path = path.trim();
		
		if (path.length() == 0) {
			return "";
		}
		
		if (path.indexOf("\\") > -1) {
			path = path.replace("\\", "/");
		}
		
		while (path.indexOf("//") > -1) {
			path = path.replace("//", "/");
		}
		
		return path;
	}

	
	public static String join(String str, String delimiter, int count) {
		
		StringBuffer resultBuff = new StringBuffer();
		
		for (int i=0; i<count; i++) {
			if (resultBuff.length() > 0) {
				resultBuff.append(delimiter);
			}
			
			resultBuff.append(str);
		}
		
		return resultBuff.toString();
	}
	
	
	public static String join(ArrayList<String> stringList, String delimiter) {
		if (stringList == null || stringList.size() == 0) {
			return "";
		}
		
		StringBuffer resultBuff = new StringBuffer();
		
		int count = stringList.size();
		for (int i=0; i<count; i++) {
			if (resultBuff.length() > 0) {
				resultBuff.append(delimiter);
			}
			
			String str = stringList.get(i);
			if (str == null) {
				str = "";
			}
			resultBuff.append(str);
		}
		
		return resultBuff.toString();
	}
	
	
	public static String replaceOne(String originStr, String preStr, String postStr) {
		if (originStr == null || originStr.length() == 0) {
			return "";
		}
		
		if (preStr == null || preStr.length() == 0) {
			return originStr;
		}
		
		if (postStr == null) {
			postStr = "";
		}
		
		int index = originStr.indexOf(preStr);
		if (index > -1) {
			return originStr.substring(0, index) + postStr + originStr.substring(index + preStr.length());
		}
		
		return originStr;
	}
}
