package com.thkmon.ddoc.controller.file;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.thkmon.database.data.DdocBlobInfo;
import com.thkmon.database.mapper.JPAMapper;
import com.thkmon.util.date.DateUtil;
import com.thkmon.util.string.StringUtil;

@Controller
public class FileUploadController {
	
	@RequestMapping("/file/upload")
	public String fileUpload(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String resultPage = "test/upload";
		
		MultipartHttpServletRequest multiReq = null;
		
		try {
			
			try {
				multiReq = (MultipartHttpServletRequest) req;
			} catch (Exception e) {
				// 잘못된 접근
				throw new Exception("잘못된 접근입니다.");
			}
			
			Iterator<String> iter = multiReq.getFileNames();
			if (iter == null) {
				return resultPage;
			}
			
			while (iter.hasNext()) {
				String fileName = iter.next();
				if (fileName == null || fileName.length() == 0) {
					continue;
				}
				
				MultipartFile mFile = multiReq.getFile(fileName);
				if (mFile == null) {
					continue;
				}
				
				saveCacheFile(mFile);
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return resultPage;
	}
	
	
	public void saveCacheFile(MultipartFile mFile) throws Exception {
		
		String dateTimeMilSec = DateUtil.getTodayDateTimeMilSec();
		String dateTime = dateTimeMilSec.substring(0, 14);
		
		String originFileName = mFile.getOriginalFilename();
//		String originFileName = new String(mFile.getOriginalFilename().getBytes("8859_1"), "UTF-8");
		
		String dotAndExt = ".bin";
		int dotIndex = originFileName.lastIndexOf(".");
		if (dotIndex > -1) {
			dotAndExt = originFileName.substring(dotIndex);
		}
		
		if (dotAndExt.trim().length() == 0) {
			dotAndExt = ".bin";
		} else {
			dotAndExt = dotAndExt.trim();
		}
		
		String originFilePath = "";
		
		try {
			Field firstField = mFile.getClass().getDeclaredFields()[1];
			firstField.setAccessible(true);
			FileItem fileItem = (FileItem) firstField.get(mFile);
			firstField.setAccessible(false);
			
			originFilePath = fileItem.getName();
		
		} catch (Exception e) {
			System.out.println("FileUploadController fileUpload (getOriginFilePath) : " + e.getMessage());
		}
		
		String basePath = "ddoc/cache/";
		String destinationPath = dateTime.substring(0, 4) + "/" + dateTime.substring(4, 6) + "/" + dateTime.substring(6, 8) + "/";
		String cacheFileName = dateTimeMilSec + dotAndExt;
		
		File dir = new File(basePath + destinationPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File fileObj = new File(basePath + destinationPath + cacheFileName);
		mFile.transferTo(fileObj);
		
		if (!fileObj.exists()) {
			throw new Exception("FileUploadController fileUpload : file not exists");
		}
		
		String blobId = dateTimeMilSec;
		long fileSize = fileObj.length();
		String regTime = dateTime;
		originFilePath = StringUtil.revisePath(originFilePath);
		String cacheFilePath = StringUtil.revisePath(fileObj.getAbsolutePath());
		String isDelete = "0";
		
		System.out.println(" BLOB_ID           : " + blobId);
		System.out.println(" FILE_BLOB         : " + "");
		System.out.println(" FILE_SIZE         : " + fileSize);
		System.out.println(" REG_TIME          : " + regTime);
		System.out.println(" ORIGIN_FILE_NAME  : " + originFileName);
		System.out.println(" ORIGIN_FILE_PATH  : " + originFilePath);
		System.out.println(" CACHE_FILE_NAME   : " + cacheFileName);
		System.out.println(" CACHE_FILE_PATH   : " + cacheFilePath);
		System.out.println(" IS_DELETE         : " + isDelete);
		
//		byte[] fileBlob = convertFileToBlob(resultFile);
		
		Blob blob = convertFileToBlob(fileObj);
		if (blob == null) {
			throw new Exception("FileUploadController fileUpload : blob is null");
		}
		
		DdocBlobInfo ddocBlobInfo = new DdocBlobInfo();
		ddocBlobInfo.setBlobId(blobId);
		ddocBlobInfo.setFileBlob(blob);
		ddocBlobInfo.setFileSize(String.valueOf(fileSize));
		ddocBlobInfo.setRegTime(regTime);
		ddocBlobInfo.setOriginFileName(originFileName);
		ddocBlobInfo.setOriginFilePath(originFilePath);
		ddocBlobInfo.setCacheFileName(cacheFileName);
		ddocBlobInfo.setCacheFilePath(cacheFilePath);
		ddocBlobInfo.setIsDelete(isDelete);
		
		JPAMapper.getInstance().insert(DdocBlobInfo.class, ddocBlobInfo);
		
	}
	
	
	private Blob convertFileToBlob(File file) throws Exception {
		
		Blob blob = null;
		FileInputStream inputStream = null;
		
		try {
			byte[] byteArray = new byte[(int) file.length()];
			inputStream = new FileInputStream(file);
			inputStream.read(byteArray);
			
			blob = new javax.sql.rowset.serial.SerialBlob(byteArray); 
			
		} catch (Exception e) {
			throw e;
			
		} finally {
			close(inputStream);
		}
		
		return blob;
	}
	
	
	private void close(FileInputStream obj) {
		try {
			if (obj != null) {
				obj.close();
			}
			
		} catch (Exception e) {
			obj = null;
			
		} finally {
			obj = null;
		}
	}

}