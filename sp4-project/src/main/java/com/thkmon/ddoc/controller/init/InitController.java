package com.thkmon.ddoc.controller.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.thkmon.database.mapper.BBMapper;
import com.thkmon.database.mapper.BBMapperUtil;
import com.thkmon.ddoc.entity.BlobInfo;

@Controller
public class InitController {
	
	private boolean bInit = false;
	
	@Autowired
	public void init() {
		if (bInit == true) {
			return;
		} else {
			bInit = true;
		}
		
		BBMapperUtil.loadClass();
		System.out.println("Loading...");
		
//		try {
//			
//			// DataMapper.getInstance().select(new DdocBlobInfo(), " SELECT * FROM DDOC_BLOB_INFO ", null, false);
//			
//			BlobInfo blobInfo = new BlobInfo();
//			blobInfo.setBlobId("1");
//			
//			blobInfo = (BlobInfo) BBMapperUtil.getInstance().select(blobInfo);
//			
//			System.out.println("blobInfo : " + blobInfo);
//		
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			ComboPooledDataSource dataSource = new ComboPooledDataSource();
//			dataSource.setDriverClass("com.mysql.jdbc.Driver");
//			dataSource.setJdbcUrl("jdbc:mysql://localhost/ddoc?characterEncoding=utf8");
//			dataSource.setUser("ddoc");
//			dataSource.setUser("1234");
//			
//			DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
//			transactionManager.setDataSource(dataSource);
//		
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("Boot");
//		
//		JPAMapper pa = new JPAMapper();
//		pa.getClass().geta
			
	}
}
