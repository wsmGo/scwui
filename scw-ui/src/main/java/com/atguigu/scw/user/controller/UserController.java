package com.atguigu.scw.user.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.scw.common.bean.ResponseVo;
import com.atguigu.scw.common.vo.response.UserResponseVo;
import com.atguigu.scw.ui.service.ProjectUserControllerFeign;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/user")
public class UserController {
		@Autowired
		ProjectUserControllerFeign projectUserControllerFeign;
		
		
		@ApiOperation("用户登录")
		@PostMapping("/doLogin")
		public String doLogin(String loginacct , String userpswd ,Model model,HttpSession session) {
			//1.远程调用user项目的登入方法 
			ResponseVo<UserResponseVo> vo = projectUserControllerFeign.doLogin(loginacct, userpswd);
			//2.判断获得的用户是否存在
			if("200".equals(vo.getCode())) {//判断状态码是否正确
					UserResponseVo project = vo.getData();
					session.setAttribute("user", project);
					//如果session中有ref 就跳转回去
					String ref = (String) session.getAttribute("ref");
					if(StringUtils.isEmpty(ref)) {
						return "redirect:/index";
					}else {
						session.removeAttribute("ref");
						return "redirect:"+ref;
					}
			}else {//不存在给定错误信息
				String errorMsg = vo.getMessage();
				model.addAttribute("errorMsg", errorMsg);
				return "user/login";
			}
		}
}
