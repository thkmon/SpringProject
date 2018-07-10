package com.thkmon.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.thkmon.util.data.StringList;
import com.thkmon.util.info.FileInfo;

public class FileUtil {
	
	public FileInfo readFile(String filePath) throws Exception {
		
		if (filePath == null || filePath.length() == 0) {
			throw new Exception("FileUtil readFile : filePath is null or empty");
		}
		
		FileInfo fileInfo = null;
		
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;
		
		File file = null;
		
		try {
			file = new File(filePath);
			if (!file.exists() || !file.isFile()) {
				throw new Exception("FileUtil readFile : !file.exists() || !file.isFile() [" + file.getAbsolutePath() + "]");
			}
			
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "UTF-8");
			reader = new BufferedReader(isr);
			
			StringList fileContent = null;
			String singleLine = null;
			while((singleLine = reader.readLine()) != null) {
				if (fileContent == null) {
					fileContent = new StringList();
				}
				fileContent.add(singleLine);
			}
			
			if (fileContent != null && fileContent.size() > 0) {
				fileInfo = new FileInfo();
				fileInfo.setFile(file);
				fileInfo.setFileContent(fileContent);
			}
			
		} catch (Exception e) {
			close(reader);
			close(isr);
			close(fis);
		}
		
		return fileInfo;
	}
	
	
	private void close(FileInputStream obj) {
		try {
			if (obj != null) {
				obj.close();
			}
			
		} catch (Exception e) {
			obj = null;
			
		} finally {
			obj = null;
		}
	}
	
	
	private void close(InputStreamReader obj) {
		try {
			if (obj != null) {
				obj.close();
			}
			
		} catch (Exception e) {
			obj = null;
			
		} finally {
			obj = null;
		}
	}
	
	
	private void close(BufferedReader obj) {
		try {
			if (obj != null) {
				obj.close();
			}
			
		} catch (Exception e) {
			obj = null;
			
		} finally {
			obj = null;
		}
	}
}
