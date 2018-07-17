package com.thkmon.ddoc.controller.doc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DocController {

	@RequestMapping("/ddoc/doc_view")
	public String viewDoc(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		return "ddoc/doc/doc_view";
	}
	
	
	@RequestMapping("/write")
	public String write(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		return "ddoc/write/write_doc";
	}
}
