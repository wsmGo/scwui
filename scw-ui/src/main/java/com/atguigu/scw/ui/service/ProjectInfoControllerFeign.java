package com.atguigu.scw.ui.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.scw.common.ProjectDetailsVo;
import com.atguigu.scw.common.ProjectResponseVo;
import com.atguigu.scw.common.TReturn;
import com.atguigu.scw.common.bean.ResponseVo;

@FeignClient(value="SCW-PROJECT")
public interface ProjectInfoControllerFeign {
	//获取所有的项目
	@GetMapping("/getAllProjects")
	public ResponseVo<List<ProjectResponseVo>> getAllProjects();
	//获取项目详情
	@GetMapping("/getProjectDetailsVo")
	public ResponseVo<ProjectDetailsVo> getProjectDetailsVo(@RequestParam("id")Integer id);
	//获取回报信息
	@GetMapping("/getReturn")
	public ResponseVo<TReturn> getReturn(@RequestParam("id")Integer id);
	//更新项目筹款
	@PostMapping("/updateMoney")
	public ResponseVo<String> updateMoney(@RequestParam("pid")Integer pid,@RequestParam("total_amount")long total_amount );
}
