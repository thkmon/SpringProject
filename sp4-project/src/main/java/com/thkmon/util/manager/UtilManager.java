package com.thkmon.util.manager;

import com.thkmon.util.file.FileUtil;
import com.thkmon.util.prop.PropertiesUtil;

public class UtilManager {
	
	private static PropertiesUtil propertiesUtil = null;
	
	public static PropertiesUtil getPropertiesUtilInstance() {
		if (propertiesUtil == null) {
			propertiesUtil = new PropertiesUtil();
		}
		
		return propertiesUtil;
	}
	
	private static FileUtil fileUtil = null;
	
	public static FileUtil getFileUtilInstance() {
		if (fileUtil == null) {
			fileUtil = new FileUtil();
		}
		
		return fileUtil;
	}
}
