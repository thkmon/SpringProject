package com.thkmon.ddoc.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thkmon.database.data.Member;
import com.thkmon.database.mapper.BBMapperUtil;

@Controller
public class TestController {

	@RequestMapping("/test")
	public String hello(Model model, @RequestParam(value = "name", required = false) String name) {
		
//		try {
//			Member member = new Member();
//			member.setId("id2");
//			member.setAge(100);
//			member.setUserName("wewewe");
//			
//			BBMapperUtil.getInstance().insert(member);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		try {
			Member member = new Member();
			member.setId("id2");
			member.setAge(10000);
			member.setUserName("wewewe123");
			
			BBMapperUtil.getInstance().update(member);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("greeting", "안녕하세요, " + name);
		return "test";
	}
	
	
	@RequestMapping("/test/upload")
	public String testUpload(Model model, @RequestParam(value = "name", required = false) String name) {
		// model.addAttribute("greeting", "안녕하세요, " + name);
		return "test/upload";
	}
}
