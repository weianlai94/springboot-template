package com.example.demo.common.weixinpay;

public class PayRefundVo {
        //order表内的orderPid字段
        private String outTradeNo;
        //售后订单编号
        private String outRefundNo;
        //订单的原始价格
        private String totalFee;
        //需要退的钱
        private String refundFee;
        //币种  CNY
        private String refundFeeType;

        private Integer uid;

        private String refundDesc;

        public String getRefundDesc() {
            return refundDesc;
        }

        public void setRefundDesc(String refundDesc) {
            this.refundDesc = refundDesc;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public String getOutRefundNo() {
            return outRefundNo;
        }

        public void setOutRefundNo(String outRefundNo) {
            this.outRefundNo = outRefundNo;
        }

        public String getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(String totalFee) {
            this.totalFee = totalFee;
        }

        public String getRefundFee() {
            return refundFee;
        }

        public void setRefundFee(String refundFee) {
            this.refundFee = refundFee;
        }

        public String getRefundFeeType() {
            return refundFeeType;
        }

        public void setRefundFeeType(String refundFeeType) {
            this.refundFeeType = refundFeeType;
        }

        public Integer getUid() {
            return uid;
        }

        public void setUid(Integer uid) {
            this.uid = uid;
        }

    }