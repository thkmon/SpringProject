package com.thkmon.util.info;

import java.io.File;

import com.thkmon.util.data.StringList;

public class FileInfo {
	private File file = null;
	private StringList fileContent = null;
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public StringList getFileContent() {
		return fileContent;
	}
	public void setFileContent(StringList fileContent) {
		this.fileContent = fileContent;
	}
}
