package com.thkmon.util.logger;

import com.bb.logger.BBLogger;

public class LoggerUtil {
	
	public static BBLogger logger = null;
	
	
	public static BBLogger getInstance() {
		
		if (logger == null) {
			try {
				logger = new BBLogger("ddoc/log", "common");
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return logger;
	}
}