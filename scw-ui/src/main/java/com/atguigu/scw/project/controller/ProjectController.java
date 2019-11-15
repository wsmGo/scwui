package com.atguigu.scw.project.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.scw.common.ProjectDetailsVo;
import com.atguigu.scw.common.bean.ResponseVo;
import com.atguigu.scw.ui.service.ProjectInfoControllerFeign;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/project")
@Slf4j
public class ProjectController {
		
		@Autowired
		ProjectInfoControllerFeign projectInfoControllerFeign;
		@ApiOperation("项目详情")
		@GetMapping("/projectDetails")
		public String getProjectDetails(Integer id, HttpSession session) {
			ResponseVo<ProjectDetailsVo> vo = projectInfoControllerFeign.getProjectDetailsVo(id);
			session.setAttribute("prodetils", vo.getData());
			log.info("项目详情: {}", vo);
			return "project/project";
		}
	
}
