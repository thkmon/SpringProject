package com.thkmon.database;

import java.util.ArrayList;

import com.bb.mapper.BBMapper;
import com.thkmon.util.conv.AesUtil;
import com.thkmon.util.info.PropertiesInfo;
import com.thkmon.util.manager.UtilManager;

public class BBMapperPool {
	
	private static String url = "localhost";
	private static int port = 3306;
	private static String dbName = "ddoc";
	private static String dbUser = "ddoc";
	private static String dbPassword = "ddoc";
	
	public static ArrayList<BBMapper> mapperList = null;
	
	public static void init(String propertiesFilePath) throws Exception {
		try {
			PropertiesInfo propInfo = UtilManager.getPropertiesUtilInstance().readProperties(propertiesFilePath);
			BBMapperPool.url = propInfo.get("url");
			BBMapperPool.port = propInfo.getInt("port");
			BBMapperPool.dbName = propInfo.get("dbName");
			BBMapperPool.dbUser = propInfo.get("dbUser");
			
			String dbPassword = propInfo.get("dbPassword");
			if (dbPassword != null && dbPassword.length() > 0) {
				BBMapperPool.dbPassword = new AesUtil().decrypt(dbPassword);
			}

		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public static BBMapper getInstance() {
		return new BBMapper(url, port, dbName, dbUser, dbPassword);
	}
}