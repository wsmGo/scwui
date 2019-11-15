package com.atguigu.scw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.scw.common.ProjectResponseVo;
import com.atguigu.scw.common.bean.ResponseVo;
import com.atguigu.scw.ui.service.ProjectInfoControllerFeign;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class DispatcherController {
	@Autowired
	ProjectInfoControllerFeign projectInfoControllerFeign;
	
	@RequestMapping(value= {"/","/index","/index.html"})
	public String toIndexPage(Model model) {
		//1.远程调用 获取项目列表
		ResponseVo<List<ProjectResponseVo>> vo = projectInfoControllerFeign.getAllProjects();
		//2.将项目列表放在request中
		log.info("查询到的项目列表,{}", vo);
		model.addAttribute("projects", vo.getData());
		//3.返回到页面显示
		return "index";
	}
}
