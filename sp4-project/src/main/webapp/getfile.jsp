<%@ page language="java"%><%@ 
page import="java.io.File"%><%@ 
page import="java.io.PrintWriter"%><%@ 
page import="java.io.FileInputStream"%><%@ 
page import="java.io.BufferedOutputStream"%><%@ 
page import="java.io.BufferedInputStream"%><%@ 
page import="java.net.URLEncoder"%><%@
page import="com.thkmon.ddoc.entity.BlobInfo"%><%@
page import="com.bb.mapper.BBMapper"%><%@
page import="com.thkmon.database.BBMapperPool"%><%@
page import="java.io.InputStream"%><%@
page import="java.sql.Blob"%><%

	String id = request.getParameter("id");
	if (id == null || id.length() == 0) {
		return;
	}
	
	BlobInfo blobInfo = new BlobInfo();
	blobInfo.setBlobId(id);
	
	BBMapper mapper = BBMapperPool.getInstance();
	Object resultObject = mapper.selectSingleRow(blobInfo);
	if (resultObject == null) {
		return;
	}
	
	try {
		blobInfo = (BlobInfo) resultObject;
	} catch (Exception e) {
		return;
	}
	
	String fileName = blobInfo.getCacheFileName();
	if (fileName == null || fileName.length() == 0) {
		fileName = "temp.bin";
	}
	
	Blob fileBlob = blobInfo.getFileBlob();
	
	request.setCharacterEncoding("UTF-8");

	response.setContentType("application/octet-stream");
	response.setDateHeader("Expires", 0);
	response.setHeader("Accept-Ranges", "bytes");
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setHeader("Content-Length", String.valueOf(fileBlob.length()));

	response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
	
	int count = 0;
	byte[] chunk = null;
	InputStream inputStream = null;
	BufferedOutputStream bufferedOutputStream = null;
	
	try {
		chunk = new byte[1024];
		inputStream = fileBlob.getBinaryStream();
		bufferedOutputStream = new BufferedOutputStream(response.getOutputStream(), 1024);
		
		while ((count = inputStream.read(chunk)) != -1) {
			bufferedOutputStream.write(chunk, 0, count);
		}
		
		bufferedOutputStream.flush();
		
	} catch (Exception e) {
		return;
		
	} finally {
		try {
			if (bufferedOutputStream != null) {
				bufferedOutputStream.close();
			}
			
		} catch (Exception e) {
			bufferedOutputStream = null;
			
		} finally {
			bufferedOutputStream = null;
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
	}%>