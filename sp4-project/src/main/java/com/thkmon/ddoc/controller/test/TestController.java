package com.thkmon.ddoc.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thkmon.database.mapper.BBMapper;
import com.thkmon.database.mapper.BBMapperUtil;
import com.thkmon.database.prototype.BBEntity;
import com.thkmon.database.prototype.BBEntityList;
import com.thkmon.ddoc.entity.Member;

@Controller
public class TestController {

	@RequestMapping("/test")
	public String hello(Model model, @RequestParam(value = "name", required = false) String name) {

		try {
			Member member = new Member();
			member.setId("test_id");
			member.setAge(100);
			member.setUserName("wewewe");
			
			BBMapper mapper = BBMapperUtil.getInstance();
			mapper.insert(member);
			System.out.println(mapper.getSqlText()); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			Member member = new Member();
			member.setId("test_id");
			member.setAge(10000);
			member.setUserName("wewewe123");
			
			BBMapper mapper = BBMapperUtil.getInstance();
			mapper.update(member);
			System.out.println(mapper.getSqlText()); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			Member member = new Member();
			member.setId("test_id");
			
			BBMapper mapper = BBMapperUtil.getInstance();
			BBEntity entity = mapper.selectSingleRow(member);
			System.out.println(mapper.getSqlText()); 
			System.out.println(BBMapperUtil.convertToString(entity));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			Member member = new Member();
			member.setId("test_id");
			
			BBMapper mapper = BBMapperUtil.getInstance();
			int bResult = mapper.delete(member);
			System.out.println(mapper.getSqlText()); 
			System.out.println(bResult);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			Member member = new Member();
			member.setId("test_id");
			
			BBMapper mapper = BBMapperUtil.getInstance();
			BBEntity entity = mapper.selectSingleRow(member);
			System.out.println(mapper.getSqlText()); 
			System.out.println(BBMapperUtil.convertToString(entity));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Member member = new Member();
			member.setId("test_id");
			
			BBMapper mapper = BBMapperUtil.getInstance();
			BBEntityList entityList = mapper.select(member, " SELECT * FROM test_member WHERE age = '100' ");
			System.out.println(mapper.getSqlText()); 
			System.out.println(BBMapperUtil.convertToString(entityList));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// model.addAttribute("greeting", "안녕하세요, " + name);
		model.addAttribute("greeting", "");
		return "test";
	}
	
	
	@RequestMapping("/test/upload")
	public String testUpload(Model model, @RequestParam(value = "name", required = false) String name) {
		// model.addAttribute("greeting", "안녕하세요, " + name);
		return "test/upload";
	}
}
