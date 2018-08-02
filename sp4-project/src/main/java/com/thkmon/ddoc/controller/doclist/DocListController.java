package com.thkmon.ddoc.controller.doclist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

public class DocListController {
	@RequestMapping("/imsi")
	public String viewDoc(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		return "ddoc/doc/doc_view";
	}
}
