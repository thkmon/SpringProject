package com.thkmon.ddoc.entity;

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
	private int age = 0;
	
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}