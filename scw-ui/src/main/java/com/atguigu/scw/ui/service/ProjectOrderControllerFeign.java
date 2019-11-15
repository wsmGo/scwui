package com.atguigu.scw.ui.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.scw.common.TOrder;
import com.atguigu.scw.common.bean.ResponseVo;

@FeignClient(value="SCW-ORDER")
public interface ProjectOrderControllerFeign {

	@PostMapping("/order/createOrder")
	public ResponseVo<String> createOrder(@RequestBody TOrder order);
	
	@PostMapping("/order/updateStatus")
	public ResponseVo<String> updateStatus(@RequestParam("oredrNum")String oredrNum ,@RequestParam("status") String status);
	//更新筹款
	@PostMapping("/order/selectId")
	public Integer selectId( @RequestParam("orderToken")String orderToken);
}
