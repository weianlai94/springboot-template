package com.example.demo.common.alipay;

public class AliPayRefundVo {
	// order表内的orderPid字段,注意转成String
	private String outTradeNo;
	// 需要退的钱，注意转成String
	private String refundFee;
	// 售后订单单号
	private String outRequestNo;
	
	private Integer uid;

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getOutRequestNo() {
		return outRequestNo;
	}

	public void setOutRequestNo(String outRequestNo) {
		this.outRequestNo = outRequestNo;
	}
	

}