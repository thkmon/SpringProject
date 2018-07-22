package com.thkmon.ddoc.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bb.mapper.BBMapper;
import com.bb.mapper.prototype.BBEntity;
import com.bb.mapper.prototype.BBEntityList;
import com.bb.mapper.util.BBMapperUtil;
import com.thkmon.ddoc.entity.Member;
import com.thkmon.util.logger.LoggerUtil;

@Controller
public class TestController {

	@RequestMapping("/test")
	public String hello(Model model, @RequestParam(value = "name", required = false) String name) {

		try {
			Member member = new Member();
			member.setId("test_id");
			member.setAge(100);
			member.setUserName("wewewe");
			
			BBMapper mapper = new BBMapper();
			mapper.insert(member);
			LoggerUtil.getInstance().debug(mapper.getSqlText()); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			Member member = new Member();
			member.setId("test_id");
			member.setAge(10000);
			member.setUserName("wewewe123");
			
			BBMapper mapper = new BBMapper();
			mapper.update(member);
			LoggerUtil.getInstance().debug(mapper.getSqlText()); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			Member member = new Member();
			member.setId("test_id");
			
			BBMapper mapper = new BBMapper();
			BBEntity entity = mapper.selectSingleRow(member);
			LoggerUtil.getInstance().debug(mapper.getSqlText()); 
			LoggerUtil.getInstance().debug(BBMapperUtil.convertToString(entity));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			Member member = new Member();
			member.setId("test_id");
			
			BBMapper mapper = new BBMapper();
			int bResult = mapper.delete(member);
			LoggerUtil.getInstance().debug(mapper.getSqlText()); 
			LoggerUtil.getInstance().debug(String.valueOf(bResult));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			Member member = new Member();
			member.setId("test_id");
			
			BBMapper mapper = new BBMapper();
			BBEntity entity = mapper.selectSingleRow(member);
			LoggerUtil.getInstance().debug(mapper.getSqlText()); 
			LoggerUtil.getInstance().debug(BBMapperUtil.convertToString(entity));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Member member = new Member();
			member.setId("test_id");
			
			BBMapper mapper = new BBMapper();
			BBEntityList entityList = mapper.select(member, " SELECT * FROM test_member WHERE age = '100' ");
			LoggerUtil.getInstance().debug(mapper.getSqlText()); 
			LoggerUtil.getInstance().debug(BBMapperUtil.convertToString(entityList));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// model.addAttribute("greeting", "안녕하세요, " + name);
		model.addAttribute("greeting", "");
		return "test";
	}
}
