package com.atguigu.scw.common;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderFormInfoSubmitVo implements Serializable{
	private String address;//收货地址id
	private String invoice;//0代表不要  1-代表要 在页面中将value属性值设置
	private String invoictitle;//发票抬头
	private String remark;//订单的备注
	private Integer  rtncount;
}
