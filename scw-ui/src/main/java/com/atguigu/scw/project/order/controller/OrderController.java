package com.atguigu.scw.project.order.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.scw.common.OrderFormInfoSubmitVo;
import com.atguigu.scw.common.ProjectDetailsVo;
import com.atguigu.scw.common.TMemberAddress;
import com.atguigu.scw.common.TOrder;
import com.atguigu.scw.common.TReturn;
import com.atguigu.scw.common.bean.ResponseVo;
import com.atguigu.scw.common.utils.AppDateUtils;
import com.atguigu.scw.common.vo.response.UserResponseVo;
import com.atguigu.scw.ui.config.AlipayConfig;
import com.atguigu.scw.ui.service.ProjectInfoControllerFeign;
import com.atguigu.scw.ui.service.ProjectOrderControllerFeign;
import com.atguigu.scw.ui.service.ProjectUserControllerFeign;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/order")
public class OrderController {

	@Autowired
	ProjectInfoControllerFeign projectInfoControllerFeign;
	@Autowired
	ProjectUserControllerFeign userControllerFeign;
	@Autowired
	ProjectOrderControllerFeign projectOrderControllerFeign;
	//异步通知方法
	@GetMapping("/notify_url")
	public String notifyUrl(HttpServletRequest request , Model model) throws Throwable {
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
		if(signVerified) {//验证成功
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			//如果验证成功 修改订单状态 远程调用方法 
				projectOrderControllerFeign.updateStatus(out_trade_no, "1");
			if(trade_status.equals("TRADE_FINISHED")){
			}else if (trade_status.equals("TRADE_SUCCESS")){
			}
			//out.println("success");
			return "success";
		}else {//验证失败
			//out.println("fail");
			return "fail";
			//调试用，写文本函数记录程序运行情况是否正常
			//String sWord = AlipaySignature.getSignCheckContentV1(params);
			//AlipayConfig.logResult(sWord);
		}
	}
	
	//用户支付完毕后 用户回调的方法
	//同步通知方法
	@GetMapping("/return_url")
	public String returnUrl(HttpServletRequest request , Model model ) throws Throwable {
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
		//——请在这里编写您的程序（以下代码仅作参考）——
		if(signVerified) {
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
			//跳转到支付成功页面 给用户响应
			model.addAttribute("out_trade_no", out_trade_no);
			model.addAttribute("trade_no", trade_no);
			model.addAttribute("total_amount", total_amount);
			//如果验证成功 修改订单状态 远程调用方法 
			projectOrderControllerFeign.updateStatus(out_trade_no, "1");
			//成功后,修改已众筹到的项目金额
			
			//根据订单编号找到项目id 远程调用
			Integer pid = projectOrderControllerFeign.selectId(out_trade_no);
			log.info("pid: {}",pid);//3
			log.info("total_amount: {}",total_amount);//100.00
			//再通过项目id去更新筹款
			String[] split = total_amount.split("\\.");
			String string = split[0];//100
			long l = Long.parseLong(string);
			projectInfoControllerFeign.updateMoney(pid, l);
			return "order/checkout";
			//out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);
		}else {
			//out.println("验签失败");
			return "order/error";
		}
	}
	
	
	//结算方法
	@ResponseBody
	@PostMapping(value="/checkout" , produces="text/html")
	public String checkout(OrderFormInfoSubmitVo vo , HttpServletRequest request,HttpSession session){
			//log.info("接收的参数,{}",vo);
		//创建封装对象
			TOrder tOrder = new TOrder();
			BeanUtils.copyProperties(vo, tOrder);
			//手动赋值
//			  private Integer memberid; 付款人的id
			UserResponseVo user = (UserResponseVo) session.getAttribute("user");
			Integer memberid = user.getId();
			tOrder.setMemberid(memberid);
//			    private Integer projectid;
			ProjectDetailsVo project = (ProjectDetailsVo) session.getAttribute("prodetils");
			Integer proid = project.getId();
			tOrder.setProjectid(proid);
//			    private Integer returnid;
			TReturn rtn = (TReturn) session.getAttribute("rtnvo");
			Integer rtnid = rtn.getId();
			tOrder.setReturnid(rtnid);
//			    private String ordernum; 服务器直接创建 不能暴露隐私 要唯一 一定识别性 uuid+时间戳+订单编号
				String ordernum =System.currentTimeMillis()+ UUID.randomUUID().toString().replace("-", "")+memberid;
				tOrder.setOrdernum(ordernum);
//			    private String createdate;
				String createdate = AppDateUtils.getFormatTime();
				tOrder.setCreatedate(createdate);
//			    private Integer money;
				Integer money = vo.getRtncount()*rtn.getSupportmoney()+rtn.getFreight();
				tOrder.setMoney(money);
//			    private Integer rtncount; //页面获取
//			    private String status; 0未付款 1已付款
				String status = "0";
				tOrder.setStatus(status);
				//调用远程服务 存入数据库
				ResponseVo<String> createOrder = projectOrderControllerFeign.createOrder(tOrder);
				//判断是否成功
				if("200".equals(createOrder.getCode())) {
					//获得初始化的AlipayClient
					AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
					//设置请求参数
					AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
					alipayRequest.setReturnUrl(AlipayConfig.return_url);
					alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
					//商户订单号，商户网站订单系统中唯一订单号，必填
					String out_trade_no = ordernum;//new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
					//付款金额，必填
					String total_amount = money+"";//new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
					//订单名称，必填
					String subject = project.getName(); //new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
					//商品描述，可空
					String body =vo.getRemark(); //new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
					
					alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
							+ "\"total_amount\":\""+ total_amount +"\"," 
							+ "\"subject\":\""+ subject +"\"," 
							+ "\"body\":\""+ body +"\"," 
							+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
					String result = "";
					try {
						result = alipayClient.pageExecute(alipayRequest).getBody();
					} catch (AlipayApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//输出
					return result;
				}else {
					return "支付失败";
				}
	}
	
	
	
	@ApiOperation("付款第二步")
	@GetMapping("/pay-step-2")
	public String paystep2(Integer count, HttpSession session, Model model,
			@RequestHeader("referer") String referer) {
		// 1.判断是否登录 没登录就登录 登录完返回到这一步
		UserResponseVo user = (UserResponseVo) session.getAttribute("user");
		if (user == null) {
			// 跳转到登录页面
			String errorMsg = "结账前必须先登录";
			session.setAttribute("errorMsg", errorMsg);
			// 登录完成后跳转回来
			session.setAttribute("ref", referer);
			return "user/login";
		} else {
			// 1.根据用户的assesstoken获取收货集合
			String accesstoken = user.getAccesstoken();
			ResponseVo<List<TMemberAddress>> addresses = userControllerFeign
					.getAddress(accesstoken);
			List<TMemberAddress> list = addresses.getData();
			model.addAttribute("addresses", list);
			// count也存入域中
			model.addAttribute("count", count);
			//计算总价 数量*单价+邮费
			//从session中获取回报信息
			TReturn rtnvo = (TReturn) session.getAttribute("rtnvo");
			//从回报信息中 获取单价和邮费
			BigDecimal totalPriceDb;
			BigDecimal singPrice = new BigDecimal(""+rtnvo.getSupportmoney());
			BigDecimal freight = new BigDecimal(""+rtnvo.getFreight());
			BigDecimal bigCount = new BigDecimal(""+count);
			totalPriceDb = singPrice.multiply(bigCount).add(freight);
			//将字符串类型转换成double类型
			double totalPrice = totalPriceDb.doubleValue();
			//将总价放在request域中
			model.addAttribute("totalPrice", totalPrice);
			return "order/pay-step-2";
		}
	}

	@ApiOperation("付款第一步")
	@GetMapping("/support")
	public String support(Integer rtnid, HttpSession session) {
		// 1.远程调用 获取回报信息
		ResponseVo<TReturn> vo = projectInfoControllerFeign.getReturn(rtnid);
		// 存放在session中
		session.setAttribute("rtnvo", vo.getData());
		return "order/pay-step-1";
	}

}
