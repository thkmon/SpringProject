package com.thkmon.ddoc.controller.doc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thkmon.util.logger.LoggerUtil;

@Controller
public class DocController {

	@RequestMapping("/ddoc/doc_view")
	public String viewDoc(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		return "ddoc/doc/doc_view";
	}
	
	
	@RequestMapping("/write")
	public String write(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		LoggerUtil.getInstance().debug("ddoc/write/write_doc");
		LoggerUtil.getInstance().debug("1111");
		
		return "ddoc/write/write_doc";
	}
	
	
	@RequestMapping("/ddoc/write")
	public String ddocWrite(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		LoggerUtil.getInstance().debug("ddoc/write/index");
		
		return "ddoc/write/index";
	}
	
	
	@RequestMapping("/ddoc/test/upload")
	public String ddocTestUpload(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		LoggerUtil.getInstance().debug("ddoc/test/upload");
		
		return "ddoc/test/upload";
	}
}
