package com.thkmon.util.log;

import com.bb.logger.BBLogger;

public class LogUtil {
	
	
	private static BBLogger logger = null;
	
	
	public LogUtil() {
		logger = new BBLogger("log", "test");
	}
	
	
	public static void info(Object obj) {
		if (logger != null) {
			logger.info(obj);
		}
	}

	
	public static void debug(Object obj) {
		if (logger != null) {
			logger.debug(obj);
		}
	}
	
	
	public static void error(Object obj) {
		if (logger != null) {
			logger.error(obj);
		}
	}
}
