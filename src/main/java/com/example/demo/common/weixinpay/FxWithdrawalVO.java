package com.example.demo.common.weixinpay;

public class FxWithdrawalVO {
        private String partnerTradeNo;
        private String openid;
        private String reUserName;
        private String amount;
        private String spbillCreateIp;

        public String getPartnerTradeNo() {
            return partnerTradeNo;
        }

        public void setPartnerTradeNo(String partnerTradeNo) {
            this.partnerTradeNo = partnerTradeNo;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getReUserName() {
            return reUserName;
        }

        public void setReUserName(String reUserName) {
            this.reUserName = reUserName;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getSpbillCreateIp() {
            return spbillCreateIp;
        }

        public void setSpbillCreateIp(String spbillCreateIp) {
            this.spbillCreateIp = spbillCreateIp;
        }
    }