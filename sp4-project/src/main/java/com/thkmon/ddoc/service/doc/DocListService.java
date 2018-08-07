package com.thkmon.ddoc.service.doc;

import com.bb.mapper.BBMapper;
import com.bb.mapper.prototype.BBEntityList;
import com.thkmon.database.BBMapperPool;
import com.thkmon.ddoc.entity.doclist.DdocDocList;

public class DocListService {
	
	public BBEntityList getDocList() throws Exception {
		
		BBEntityList entityList = null;
		
//		BBMapper mapper = BBMapperPool.getInstance();
//		BBEntityList entityList = mapper.select(new DdocDocList(), " SELECT * FROM DDOC_DOC_LIST ");
//		if (entityList == null || entityList.size() == 0) {
//			return null;
//		}
		
//		System.out.println(entityList);
//		DdocDocList oneList = null;
//		int count = entityList.size();
//		for (int i=0; i<count; i++) {
//			oneList = (DdocDocList) entityList.get(i);
//			System.out.println(i + " : " + oneList);
//		}

		return entityList;
	}

}