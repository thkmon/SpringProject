package com.thkmon.util.info;

import com.thkmon.util.data.StringMap;

public class PropertiesInfo {
	private StringMap propertiesMap = new StringMap();
	
	
	public void put(String key, String value) {
		if (key == null || key.length() == 0) {
			return;
		}
		
		propertiesMap.put(key, value);
	}
	
	
	public String get(String key) {
		String value = propertiesMap.get(key);
		if (value == null) {
			return "";
		}
		
		return value;
	}
}
