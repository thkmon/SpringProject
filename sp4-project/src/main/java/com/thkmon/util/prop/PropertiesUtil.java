package com.thkmon.util.prop;

import com.thkmon.util.data.StringList;
import com.thkmon.util.info.FileInfo;
import com.thkmon.util.info.PropertiesInfo;
import com.thkmon.util.manager.UtilManager;

public class PropertiesUtil {

	public PropertiesInfo readProperties(String filePath) throws Exception {
		
		PropertiesInfo propInfo = null;
		
		try {
			FileInfo fileInfo = UtilManager.getFileUtilInstance().readFile(filePath);
			
			if (fileInfo.getFileContent() != null && fileInfo.getFileContent().size() > 0) {
				StringList fileContent = fileInfo.getFileContent();
				int lineCount = fileContent.size();
				
				String singleLine = null;
				for (int i=0; i<lineCount; i++) {
					singleLine = fileContent.get(i);
					if (singleLine == null || singleLine.length() == 0) {
						continue;
					}
					
					singleLine = singleLine.trim();
					if (singleLine.startsWith("#")) {
						continue;
					}
					
					int equalIndex = singleLine.indexOf("=");
					if (equalIndex < 0) {
						continue;
					}
					
					String leftText = singleLine.substring(0, equalIndex).trim();
					String rightText = singleLine.substring(equalIndex + 1).trim();
					
					if (leftText.length() > 0) {
						if (propInfo == null) {
							propInfo = new PropertiesInfo();
						}
						
						propInfo.put(leftText, rightText);
					}
				}
			}
			
		
		} catch (Exception e) {
			throw e;
		}
		
		return propInfo;
	}
}
