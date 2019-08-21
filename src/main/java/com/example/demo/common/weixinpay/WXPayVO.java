package com.example.demo.common.weixinpay;

public class WXPayVO {
        //商品描述
        private String body;
        //商户订单号,订单中的父id
        private String outTradeNo;
        //总金额
        private String totalFee;
        //支付者终端IP
        private String spbillCreateIp;
        //通知地址
        private String notifyUrl;
        //交易类型
        private String tradeType;
        //用户名
        private Integer uid;
        //用户openid
        private String openId;

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public String getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(String totalFee) {
            this.totalFee = totalFee;
        }

        public String getSpbillCreateIp() {
            return spbillCreateIp;
        }

        public void setSpbillCreateIp(String spbillCreateIp) {
            this.spbillCreateIp = spbillCreateIp;
        }

        public String getNotifyUrl() {
            return notifyUrl;
        }

        public void setNotifyUrl(String notifyUrl) {
            this.notifyUrl = notifyUrl;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }

        public Integer getUid() {
            return uid;
        }

        public void setUid(Integer uid) {
            this.uid = uid;
        }
    }