package com.example.demo.common.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.*;
import com.alipay.api.response.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class AlipayEntrance {

	private static AlipayEntrance alconfig = null;

	public AlipayEntrance() {

	}

	public static AlipayEntrance getInstance() {
		if (alconfig == null) {
			alconfig = new AlipayEntrance();
		}
		return alconfig;
	}

	public static String it_b_pay = "1h";
	public static String pid;
	// 签名方式 不需修改
	public static String SIGN_TYPE = "RSA2";
	public static String appId;
	public static String appPrivateKey;
	public static String appPublicKey;
	public static String service = "create_direct_pay_by_user";
	// notify_url 交易过程中服务器通知的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数
//	public static String notify_url = "https://api.maison-huis.com/api/alireturn";
//	public static String return_url = "https://www.maison-huis.com/person/MyOrder";
	public static String notify_url = "https://api.xxx.com/api/alireturn";
	public static String html_return_url = "https://www.xxx.com/payorder/paySuccessAli";
	public static String wap_return_url = "https://www.xxx.com/payStatus?orderPid={orderPid}&orderId={orderId}&status=1";
	public static String wap_quit_url = "https://www.xxx.com/order?type=0";
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String CHARSET = "UTF-8";
	// 正式环境支付宝网关，如果是沙箱环境需更改成https://openapi.alipaydev.com/gateway.do
	public static String URL = "https://openapi.alipay.com/gateway.do";

	public static void setPid(String pid) {
		AlipayEntrance.pid = pid;
	}

	public static void setAppId(String appId) {
		AlipayEntrance.appId = appId;
	}

	public static void setAppPrivateKey(String appPrivateKey) {
		AlipayEntrance.appPrivateKey = appPrivateKey;
	}

	public static void setAppPublicKey(String appPublicKey) {
		AlipayEntrance.appPublicKey = appPublicKey;
	}

	public AlipayTradeAppPayResponse alipay(AlipayVO alipayVO) {
		// 实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(URL, appId, appPrivateKey, "json", CHARSET,
				appPublicKey, SIGN_TYPE);
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(alipayVO.getSubject());
		model.setSubject(alipayVO.getSubject());
		// 请保证OutTradeNo值每次保证唯一
		model.setOutTradeNo(alipayVO.getOutTradeNo());
		model.setTotalAmount(alipayVO.getTotalAmount());
		request.setBizModel(model);
		request.setNotifyUrl(notify_url);
		request.setReturnUrl(html_return_url);
		AlipayTradeAppPayResponse response = null;
		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			response = alipayClient.sdkExecute(request);
			// 就是orderString 可以直接给客户端请求，无需再做处理。
			System.out.println(response.getBody());
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return response;
	}

	public AlipayTradePagePayResponse alipayForHtml(AlipayVO alipayVO)
			throws ServletException, IOException {
		// 获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId,
				appPrivateKey, "json", CHARSET, appPublicKey, SIGN_TYPE);
		// 创建API对应的request
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		// 在公共参数中设置回跳和通知地址
		alipayRequest.setNotifyUrl(notify_url);
		alipayRequest.setReturnUrl(html_return_url);
		alipayRequest.setBizContent("{"
				+ "    \"out_trade_no\":\""+alipayVO.getOutTradeNo()+"\","
				+ "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\","
				+ "    \"total_amount\":"+alipayVO.getTotalAmount()+","
				//+ "    \"total_amount\":0.01,"
				+ "    \"subject\":\""+alipayVO.getSubject()+"\","
				+ "    \"body\":\""+alipayVO.getSubject()+"\","
				+ "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\","
				+ "    \"extend_params\":{"
				+ "    \"sys_service_provider_id\":\""+pid+"\""
				+ "    }" + "  }");// 填充业务参数
		AlipayTradePagePayResponse result = null;
		try {
			result =  alipayClient.pageExecute(alipayRequest);
			System.out.println(alipayClient.sdkExecute(alipayRequest).getBody());
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * alipay.trade.wap.pay(手机网站支付接口2.0)
	 * 外部商户创建订单并支付
	 *
	 * @return
	 * @throws AlipayApiException
	 */


	public AlipayTradeWapPayResponse alipayTradeWapPay(AlipayVO alipayVO) throws AlipayApiException {
		AlipayClient alipayClient = new DefaultAlipayClient(URL, appId, appPublicKey, "json", CHARSET,appPrivateKey, SIGN_TYPE);
		AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
		//支付宝支付成功后异步回调的接口
		request.setNotifyUrl(notify_url);
		//支付宝异步支付之后，会要跳转的接口
		String returnUrl = AlipayEntrance.wap_return_url.replace("{orderPid}",alipayVO.getOutTradeNo()).replace("{orderId}",alipayVO.getOrderId());
		request.setReturnUrl(returnUrl);
		request.setBizContent("{" +
				"\"subject\":\"xxx\"," +
				"\"out_trade_no\":\"" + alipayVO.getOutTradeNo() + "\"," +
				"\"total_amount\":" + alipayVO.getTotalAmount() + "," +
				"\"quit_url\":\"" + wap_quit_url + "\"," +
				"\"product_code\":\"QUICK_WAP_WAY\"" +
				"  }");
		AlipayTradeWapPayResponse response = alipayClient.sdkExecute(request);
		return response;
	}

	public static void main(String[] args) {
		Map<String , String > map = new HashMap<>();
		map.put("out_trade_no","1535652391");
		map.put("product_code","QUICK_WAP_WAY");
		map.put("subject","xxx");
		map.put("total_amount","12.01");
		String json = JSON.toJSONString(map);
		String code = URLEncoder.encode(json);
		System.out.println(code);

	}

	/**
	 * 扫码支付
	 * @param alipayVO
	 * @return
	 */
	public AlipayTradePrecreateResponse generateQR(AlipayVO alipayVO) {
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",appId,
				appPrivateKey,"json",CHARSET, appPublicKey, SIGN_TYPE);
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
//		request.setBizContent("{" +
////				"\"out_trade_no\":\"20150320010101001\"," +
////				"\"total_amount\":1," +
////				"\"discountable_amount\":1," +
////				"\"subject\":\"Iphone616G\"," +
////				"\"price\":1," +
////				"\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
////				"}]," +
////				"\"body\":\"Iphone616G\"," +
////				"\"operator_id\":\"yx_001\"," +
////				"\"store_id\":\"NJ_001\"," +
////				"\"disable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
////				"\"enable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
////				"\"terminal_id\":\"NJ_T_001\"," +
////				"\"extend_params\":{" +
////				"\"sys_service_provider_id\":\"2088511833207846\"" +
////				"}," +
////				"\"timeout_express\":\"90m\"," +
////				"}");
		request.setBizContent("{" +
				"\"out_trade_no\":\""+ alipayVO.getOutTradeNo()+"\"," +
				"\"total_amount\":"+alipayVO.getTotalAmount()+"," +
				"\"subject\":\""+alipayVO.getSubject()+"\""+
				"}"   );
		AlipayTradePrecreateResponse response = null;
		request.setNotifyUrl(notify_url);
		try {
			response = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		if(response.isSuccess()){
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}
		return response;
	}

	/**
	 * 阿里退款接口
	 * @return
	 * @throws AlipayApiException
	 */
	public static AlipayTradeRefundResponse tradeRefund(AliPayRefundVo aliPayRefundVo) {
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId,
				appPrivateKey, "json", CHARSET, appPublicKey, SIGN_TYPE);
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		request.setBizContent("{" +
				"\"out_trade_no\":\""+aliPayRefundVo.getOutTradeNo()+"\"," +
				"\"refund_amount\":"+aliPayRefundVo.getRefundFee()+"," +
				"\"out_request_no\":\""+aliPayRefundVo.getOutRequestNo()+"\"," +
				"\"refund_reason\":\"正常退款\"" +
				"  }");
		AlipayTradeRefundResponse response = null;
		try {
			response = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return response;
	}
}