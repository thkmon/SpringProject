package com.thkmon.ddoc.entity.member;

import com.bb.mapper.annotation.BBColumn;
import com.bb.mapper.annotation.BBPrimaryKey;
import com.bb.mapper.annotation.BBTable;
import com.bb.mapper.prototype.BBEntity;

@BBTable(name = "DDOC_MEMBER")
public class DdocMember implements BBEntity {
	
	@BBPrimaryKey(name = "USER_ID")
	@BBColumn(name = "USER_ID")
	private String userId = "";
	
	@BBColumn(name = "USER_NAME")
	private String userName = "";
	
	@BBColumn(name = "PASSWORD")
	private String password = "";
	
	@BBColumn(name = "REG_TIME")
	private String regTime = "";
	
	@BBColumn(name = "DEL_TIME")
	private String delTime = "";
	
	@BBColumn(name = "BEGIN_TIME")
	private String beginTime = "";
	
	@BBColumn(name = "END_TIME")
	private String endTime = "";
	
	@BBColumn(name = "IS_VALID")
	private String isValid = "";

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getDelTime() {
		return delTime;
	}

	public void setDelTime(String delTime) {
		this.delTime = delTime;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
}