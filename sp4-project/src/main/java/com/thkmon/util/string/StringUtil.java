package com.thkmon.util.string;

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

}
