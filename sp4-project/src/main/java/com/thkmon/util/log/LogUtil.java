package com.thkmon.util.log;

import com.bb.logger.BBLogger;

public class LogUtil {
	
	public static BBLogger logger = null;
	
	public static void init() throws Exception {
		if (logger == null) {
			try {
				logger = new BBLogger("ddoc/log", "common");
				
			} catch (Exception e) {
				throw e;
			}
		}
	}
	
	/*
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
	*/
	
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