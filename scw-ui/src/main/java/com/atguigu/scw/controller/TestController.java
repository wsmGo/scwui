package com.atguigu.scw.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class TestController {

	@RequestMapping("/test")
	public String test(Model model,HttpSession session) {
		model.addAttribute("request", "requestval");
		session.setAttribute("session", "sessionval");
		session.getServletContext().setAttribute("application", "applicationval");
		return "a/hello";
	}
}
