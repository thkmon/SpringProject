package com.thkmon.database.data;

import java.sql.Blob;

import com.thkmon.database.annotation.DataColumn;
import com.thkmon.database.annotation.DataPrimaryKey;
import com.thkmon.database.annotation.DataTable;
import com.thkmon.database.prototype.Data;

@DataTable(name = "DDOC_BLOB_INFO")
public class DdocBlobInfo implements Data {
	
	@DataPrimaryKey(name = "BLOB_ID")
	@DataColumn(name = "BLOB_ID")
	private String blobId = "";
	
	@DataColumn(name = "FILE_BLOB")
	private Blob fileBlob = null;
	
	@DataColumn(name = "FILE_SIZE")
	private String fileSize = "";
	
	@DataColumn(name = "REG_TIME")
	private String regTime = "";
	
	@DataColumn(name = "ORIGIN_FILE_NAME")
	private String originFileName = "";
	
	@DataColumn(name = "ORIGIN_FILE_PATH")
	private String originFilePath = "";
	
	@DataColumn(name = "CACHE_FILE_NAME")
	private String cacheFileName = "";
	
	@DataColumn(name = "CACHE_FILE_PATH")
	private String cacheFilePath = "";
	
	@DataColumn(name = "IS_DELETE")
	private String isDelete = "";

	public String getBlobId() {
		return blobId;
	}

	public void setBlobId(String blobId) {
		this.blobId = blobId;
	}

	public Blob getFileBlob() {
		return fileBlob;
	}

	public void setFileBlob(Blob fileBlob) {
		this.fileBlob = fileBlob;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getOriginFileName() {
		return originFileName;
	}

	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}

	public String getOriginFilePath() {
		return originFilePath;
	}

	public void setOriginFilePath(String originFilePath) {
		this.originFilePath = originFilePath;
	}

	public String getCacheFileName() {
		return cacheFileName;
	}

	public void setCacheFileName(String cacheFileName) {
		this.cacheFileName = cacheFileName;
	}

	public String getCacheFilePath() {
		return cacheFilePath;
	}

	public void setCacheFilePath(String cacheFilePath) {
		this.cacheFilePath = cacheFilePath;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
}
