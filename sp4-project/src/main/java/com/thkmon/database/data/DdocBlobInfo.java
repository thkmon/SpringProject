package com.thkmon.database.data;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DDOC_BLOB_INFO")
public class DdocBlobInfo {

	@Id
	@Column(name = "BLOB_ID")
	private String blobId = "";
	
	@Column(name = "FILE_BLOB")
	private Blob fileBlob = null;
	
	@Column(name = "FILE_SIZE")
	private String fileSize = "";
	
	@Column(name = "REG_TIME")
	private String regTime = "";
	
	@Column(name = "ORIGIN_FILE_NAME")
	private String originFileName = "";
	
	@Column(name = "ORIGIN_FILE_PATH")
	private String originFilePath = "";
	
	@Column(name = "CACHE_FILE_NAME")
	private String cacheFileName = "";
	
	@Column(name = "CACHE_FILE_PATH")
	private String cacheFilePath = "";
	
	@Column(name = "IS_DELETE")
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
