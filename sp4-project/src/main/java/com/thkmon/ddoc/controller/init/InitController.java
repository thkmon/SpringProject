package com.thkmon.ddoc.controller.init;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
	}
	
	
	@RequestMapping("/")
	public String goHomepage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "common/empty";
	}
}
