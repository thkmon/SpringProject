package com.thkmon.ddoc.controller.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.test.TestConnectionCustomizer;
import com.thkmon.database.data.DdocBlobInfo;
import com.thkmon.database.mapper.DataAnnotationUtil;
import com.thkmon.database.mapper.DataMapper;
import com.thkmon.database.mapper.JPAMapper;

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
		
		DataAnnotationUtil.loadClass();
		System.out.println("Loading...");
		
		try {
			
			// DataMapper.getInstance().select(new DdocBlobInfo(), " SELECT * FROM DDOC_BLOB_INFO ", null, false);
			
			DdocBlobInfo blobInfo = new DdocBlobInfo();
			blobInfo.setBlobId("1");
			
			blobInfo = (DdocBlobInfo) DataMapper.getInstance().select(blobInfo);
			
			System.out.println("blobInfo : " + blobInfo);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
