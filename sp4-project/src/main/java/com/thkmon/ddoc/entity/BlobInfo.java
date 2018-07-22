package com.thkmon.ddoc.entity;

import java.sql.Blob;

import com.bb.mapper.annotation.BBColumn;
import com.bb.mapper.annotation.BBPrimaryKey;
import com.bb.mapper.annotation.BBTable;
import com.bb.mapper.prototype.BBEntity;

@BBTable(name = "DDOC_BLOB_INFO")
public class BlobInfo implements BBEntity {
	
	@BBPrimaryKey(name = "BLOB_ID")
	@BBColumn(name = "BLOB_ID")
	private String blobId = "";
	
	@BBColumn(name = "FILE_BLOB")
	private Blob fileBlob = null;
	
	@BBColumn(name = "FILE_SIZE")
	private String fileSize = "";
	
	@BBColumn(name = "REG_TIME")
	private String regTime = "";
	
	@BBColumn(name = "ORIGIN_FILE_NAME")
	private String originFileName = "";
	
	@BBColumn(name = "ORIGIN_FILE_PATH")
	private String originFilePath = "";
	
	@BBColumn(name = "CACHE_FILE_NAME")
	private String cacheFileName = "";
	
	@BBColumn(name = "CACHE_FILE_PATH")
	private String cacheFilePath = "";
	
	@BBColumn(name = "IS_DELETE")
	private String isDelete = "";
	
	@BBColumn(name = "IS_POST")
	private String isPost = "";
	
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

	public String getIsPost() {
		return isPost;
	}

	public void setIsPost(String isPost) {
		this.isPost = isPost;
	}
}