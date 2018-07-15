package com.thkmon.database.data;

import com.thkmon.database.annotation.BBColumn;
import com.thkmon.database.annotation.BBPrimaryKey;
import com.thkmon.database.annotation.BBTable;
import com.thkmon.database.prototype.BBEntity;

@BBTable(name = "TEST_MEMBER")
public class Member implements BBEntity {
	
	@BBPrimaryKey(name = "ID")
	@BBColumn(name = "ID")
	private String id = "";
	
	@BBColumn(name = "NAME")
	private String userName = "";
	
	@BBColumn(name = "AGE")
	private Integer age = 0;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
}
