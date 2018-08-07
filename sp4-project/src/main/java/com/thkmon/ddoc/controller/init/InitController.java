package com.thkmon.ddoc.controller.init;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thkmon.database.BBMapperPool;
import com.thkmon.util.log.LogUtil;

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
		
		try {
			LogUtil.init();
			BBMapperPool.init("ddoc/props/pool.properties");
		
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		LogUtil.debug("Loading...");
	}
	
	
	@RequestMapping("/")
	public String goHomepage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "ddoc/home/home_view";
	}
}
