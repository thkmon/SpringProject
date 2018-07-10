package com.thkmon.controller.init;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;

@Controller
public class InitController {
	
	@PostConstruct
	public void init() {
		System.out.println("Program Start");
	}
}
