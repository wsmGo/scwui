package com.atguigu.scw.ui.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.scw.common.TMemberAddress;
import com.atguigu.scw.common.bean.ResponseVo;
import com.atguigu.scw.common.vo.response.UserResponseVo;

@FeignClient("SCW-USER")
public interface ProjectUserControllerFeign {

	//处理登录的请求
	@PostMapping("/user/doLogin")
	public ResponseVo<UserResponseVo> doLogin(@RequestParam("loginacct") String loginacct , @RequestParam("userpswd")String userpswd);

	//获取地址
	@GetMapping("/user/getAddress")
	public ResponseVo<List<TMemberAddress>> getAddress(@RequestParam("accesstoken") String accesstoken);
}
