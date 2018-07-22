package com.thkmon.ddoc.controller.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.util.Iterator;

import javax.persistence.metamodel.SetAttribute;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bb.mapper.BBMapper;
import com.bb.mapper.util.BBMapperUtil;
import com.thkmon.ddoc.entity.BlobInfo;
import com.thkmon.util.date.DateUtil;
import com.thkmon.util.logger.LoggerUtil;
import com.thkmon.util.string.StringUtil;

@Controller
public class FileUploadController {
	
	
	@RequestMapping("/upload/test")
	public String testUpload(Model model, @RequestParam(value = "name", required = false) String name) {
		// model.addAttribute("greeting", "안녕하세요, " + name);
		return "upload/test";
	}

	
	@RequestMapping("/upload/uploadform")
	public String write(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "upload/uploadform";
	}
	
	
	@RequestMapping("/getfile")
	public String getfile(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "getfile";
	}
	
	
	@RequestMapping("/file/upload")
	public String fileUpload(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String failPage = "common/empty";
		
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
				return failPage;
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
				
				BlobInfo ddocBlobInfo = saveCacheFile(mFile);
				req.setAttribute("id", ddocBlobInfo.getBlobId());
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return "upload/uploaded";
	}
	
	
	public BlobInfo saveCacheFile(MultipartFile mFile) throws Exception {
		
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
			LoggerUtil.getInstance().debug("FileUploadController fileUpload (getOriginFilePath) : " + e.getMessage());
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
		String isPost = "0";
		
		LoggerUtil.getInstance().debug(" BLOB_ID           : " + blobId);
		LoggerUtil.getInstance().debug(" FILE_BLOB         : " + "");
		LoggerUtil.getInstance().debug(" FILE_SIZE         : " + fileSize);
		LoggerUtil.getInstance().debug(" REG_TIME          : " + regTime);
		LoggerUtil.getInstance().debug(" ORIGIN_FILE_NAME  : " + originFileName);
		LoggerUtil.getInstance().debug(" ORIGIN_FILE_PATH  : " + originFilePath);
		LoggerUtil.getInstance().debug(" CACHE_FILE_NAME   : " + cacheFileName);
		LoggerUtil.getInstance().debug(" CACHE_FILE_PATH   : " + cacheFilePath);
		LoggerUtil.getInstance().debug(" IS_DELETE         : " + isDelete);
		LoggerUtil.getInstance().debug(" IS_POST           : " + isPost);
		
//		byte[] fileBlob = convertFileToBlob(resultFile);
		
		Blob blob = convertFileToBlob(fileObj);
		if (blob == null) {
			throw new Exception("FileUploadController fileUpload : blob is null");
		}
		
		BlobInfo ddocBlobInfo = new BlobInfo();
		ddocBlobInfo.setBlobId(blobId);
		ddocBlobInfo.setFileBlob(blob);
		ddocBlobInfo.setFileSize(String.valueOf(fileSize));
		ddocBlobInfo.setRegTime(regTime);
		ddocBlobInfo.setOriginFileName(originFileName);
		ddocBlobInfo.setOriginFilePath(originFilePath);
		ddocBlobInfo.setCacheFileName(cacheFileName);
		ddocBlobInfo.setCacheFilePath(cacheFilePath);
		ddocBlobInfo.setIsDelete(isDelete);
		ddocBlobInfo.setIsPost(isPost);
		
		// JPAMapper.getInstance().insert(DdocBlobInfo.class, ddocBlobInfo);
		
		BBMapper mapper = new BBMapper();
		mapper.insert(ddocBlobInfo);
		LoggerUtil.getInstance().debug(mapper.getSqlText());
		
		return ddocBlobInfo;
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
	
	private String convertBlobToString(Blob blob) throws Exception {
		if (blob == null) {
			return "";
		}
		
		String singleLine = null;
		StringBuilder builder = new StringBuilder();
		
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			inputStream = blob.getBinaryStream();
			if (inputStream == null) {
				return "";
			}
			
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			bufferedReader = new BufferedReader(inputStreamReader);
		
			while ((singleLine = bufferedReader.readLine()) != null) {
				builder.append(singleLine);
			}

		} catch (Exception e) {
			
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				
			} catch (Exception e) {
				bufferedReader = null;
				
			} finally {
				bufferedReader = null;
			}
			
			try {
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				
			} catch (Exception e) {
				inputStreamReader = null;
				
			} finally {
				inputStreamReader = null;
			}
			
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				
			} catch (Exception e) {
				inputStream = null;
				
			} finally {
				inputStream = null;
			}
		}
		
		return builder.toString();
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