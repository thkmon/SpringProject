package com.thkmon.ddoc.entity.doclist;

import com.bb.mapper.annotation.BBColumn;
import com.bb.mapper.annotation.BBPrimaryKey;
import com.bb.mapper.annotation.BBTable;
import com.bb.mapper.prototype.BBEntity;

@BBTable(name = "DDOC_DOC_LIST")
public class DdocDocList implements BBEntity {
	
	@BBColumn(name = "SEQ_NUM")
	int seqNum = 0;
	
	@BBPrimaryKey(name = "DOC_ID")
	@BBColumn(name = "DOC_ID")
	String docId = null;
	
	@BBColumn(name = "USER_ID")
	String userId = null;
	
	@BBColumn(name = "USER_NAME")
	String userName = null;
	
	@BBColumn(name = "TITLE")
	String title = null;
	
	@BBColumn(name = "CONTENT")
	String content = null;
	
	@BBColumn(name = "CONTENT_SRC")
	String contentSrc = null;
	
	@BBColumn(name = "IP_ADDRESS")
	String ipAddress = null;
	
	@BBColumn(name = "VALID")
	String valid = null;
	
	@BBColumn(name = "REGIST_TIME")
	String registTime = null;
	
	@BBColumn(name = "UPDATE_TIME")
	String updateTime = null;
	
	@BBColumn(name = "BOOK_ID")
	String bookId = null;
	
	@BBColumn(name = "SECRET")
	String secret = null;
}