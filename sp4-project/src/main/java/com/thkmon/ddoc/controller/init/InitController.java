package com.thkmon.ddoc.controller.init;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bb.mapper.util.BBMapperUtil;
import com.thkmon.util.logger.LoggerUtil;

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
		
		LoggerUtil.getInstance().debug("Loading...");
	}
	
	
	@RequestMapping("/")
	public String goHomepage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "common/empty";
	}
}
