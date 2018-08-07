package com.thkmon.ddoc.controller.doc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thkmon.ddoc.service.doc.DocListService;

@Controller
public class DocListController {
	
	@RequestMapping("/")
	public String docList(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		DocListService docListService = new DocListService();
		docListService.getDocList();
		
		return "ddoc/home/home_view";
	}
}
