package com.thkmon.database.prototype;

import java.util.ArrayList;

import com.thkmon.database.mapper.BBMapperUtil;

public class BBColumnNameList extends ArrayList<String> {
	
	public BBColumnValueList convertToColumnValueList(BBEntity bbEntity) throws Exception {
		if (this.size() == 0) {
			return null;
		}
		
		BBColumnValueList columnValueList = new BBColumnValueList();
		
		int columnCount = this.size();
		for (int i=0; i<columnCount; i++) {
			String columnName = this.get(i);
			Object bindObj = BBMapperUtil.getColumnValue(bbEntity, columnName);
			columnValueList.add(bindObj);
		}
		
		return columnValueList;
	}
}
