package com.example.demo.common.alipay;

public class AlipayVO {
	//订单order_pid
	private String outTradeNo;
	//订单含运费总价
	private String totalAmount;
	//订单标题
	private String subject;
	
	private Integer uid;

	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}


	@Override
	public String toString() {
		return "AlipayVO{" +
				"outTradeNo='" + outTradeNo + '\'' +
				", totalAmount='" + totalAmount + '\'' +
				", subject='" + subject + '\'' +
				", uid=" + uid +
				'}';
	}
}
