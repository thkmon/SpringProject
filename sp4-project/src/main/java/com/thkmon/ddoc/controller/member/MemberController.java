package com.thkmon.ddoc.controller.member;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {

	@RequestMapping("/join")
	public String join(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "ddoc/join/join_view";
	}
	
	
	@ResponseBody
	@RequestMapping("/reqjoin")
	public String reqjoin(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Enumeration<String> paramNames = req.getParameterNames();
		if (paramNames != null) {
			
		}
		
		System.out.println("sdsd");
		return "ddoc/join/join_view";
	}
}
