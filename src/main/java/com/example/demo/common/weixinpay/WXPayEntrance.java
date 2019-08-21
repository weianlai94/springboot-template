package com.example.demo.common.weixinpay;

import com.example.demo.common.utils.DateUtil;
import com.example.demo.common.weixinpay.WXPayConstants.SignType;

import java.util.*;

public class WXPayEntrance {

    private WXPay wxpay;
    private WXPayConfigImpl config;
    private String out_trade_no;

    private static WXPayEntrance alconfig = null;


    public WXPayEntrance() throws Exception {
        config = WXPayConfigImpl.getInstance();
        wxpay = new WXPay(config);
        out_trade_no = "201613091059590000003433-asd002";
    }


    /**
     * 生成预支付订单 （扫码支付，app支付）
     */
    public Map<String, String> doUnifiedOrder (WXPayVO wxPayVO) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("body", wxPayVO.getBody());
        data.put("out_trade_no", wxPayVO.getOutTradeNo());
        data.put("fee_type", "CNY");
        data.put("total_fee", wxPayVO.getTotalFee());
        Date date = DateUtil.dateAddDays(new Date(), 1);
        data.put("time_expire", DateUtil.DateToString(date, "yyyyMMddHHmmss"));
        data.put("spbill_create_ip", wxPayVO.getSpbillCreateIp());
        //正式环境用的回调地址
        data.put("notify_url", "http://5qa2tp.natappfree.cc/demo/wxpayreturn");
        data.put("trade_type", wxPayVO.getTradeType());
        String openId = wxPayVO.getOpenId() == null ? "null" : wxPayVO.getOpenId();
        if (!openId.equals("null")) {
            data.put("openid", wxPayVO.getOpenId());
        }
        if (wxPayVO.getTradeType().equals("MWEB")) {
            data.put("scene_info", "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"http://test.xxx.com/payorder/paySuccessAli/\",\"wap_name\": \"xxx\"}}");
        }
        Map<String, String> result = null;
        try {
            result = wxpay.unifiedOrder(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 小程序前台支付需要返回参数
     *
     * @param result
     * @return
     */
    public SortedMap<String, String> jsapiPayParam(Map<String, String> result) {
        //签名参数列表
        SortedMap<String, String> map = new TreeMap<String, String>();
        map.put("appId", result.get("appid"));
        map.put("nonceStr", WXPayUtil.generateUUID());
        map.put("package", "prepay_id=" + result.get("prepay_id"));
        map.put("timeStamp", System.currentTimeMillis() / 1000 + "");
        map.put("signType", "MD5");
        //生成签名
        String sign = null;
        try {
            sign = WXPayUtil.generateSignature(map, config.getKey(), SignType.MD5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("sign", sign);
        return map;
    }

    /**
     * app前台支付的时候要的参数
     *
     * @param result
     * @return
     */
    public SortedMap<String, String> appPayParam(Map<String, String> result) {
        //签名参数列表
        SortedMap<String, String> map = new TreeMap<String, String>();
        map.put("appid", result.get("appid"));
        map.put("noncestr", WXPayUtil.generateUUID());
        map.put("package", "Sign=WXPay");
        map.put("partnerid", config.getMchID());
        map.put("prepayid", result.get("prepay_id"));
        map.put("timestamp", System.currentTimeMillis() / 1000 + "");
	/* 统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。
	 参与签名的字段名为appId，partnerId，prepayId，nonceStr，timeStamp，package。
	 注意：package的值格式为prepay_id=wx20141009175351724b348a500087751557*/
        //生成签名
        String sign = null;
        try {
            sign = WXPayUtil.generateSignature(map, config.getKey(), SignType.MD5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("sign", sign);
        return map;
    }

    /**
     * 关闭订单
     *
     * @param orderPid
     * @param payType
     * @return
     * @throws Exception
     */
    public Map<String, String> doOrderClose(String orderPid, String payType) throws Exception {
        System.out.println("关闭订单");
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", orderPid);
        Map<String, String> r = wxpay.closeOrder(data, payType);
        return r;
    }

    /**
     * 查询订单
     *
     * @param orderNo
     */
    public void doOrderQuery(String orderNo) {
        System.out.println("查询订单");
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", orderNo);
//        data.put("transaction_id", "4008852001201608221962061594");
        try {
            Map<String, String> r = wxpay.orderQuery(data);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doOrderReverse(String orderPid) {
        System.out.println("撤销");
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", out_trade_no);
//        data.put("transaction_id", "4008852001201608221962061594");
        try {
            Map<String, String> r = wxpay.reverse(data);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 长链接转短链接
     * 测试成功
     */
    public void doShortUrl() {
        String long_url = "weixin://wxpay/bizpayurl?pr=etxB4DY";
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("long_url", long_url);
        try {
            Map<String, String> r = wxpay.shortUrl(data);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退款
     * 已测试
     */
    public Map<String, String> doRefund(PayRefundVo payRefundVo) {
        System.out.println(payRefundVo);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", payRefundVo.getOutTradeNo());
        data.put("out_refund_no", payRefundVo.getOutRefundNo());
        data.put("total_fee", payRefundVo.getTotalFee());
        data.put("refund_fee", payRefundVo.getRefundFee());
        data.put("refund_fee_type", "CNY");
        data.put("op_user_id", config.getMchID());
        data.put("refund_desc", payRefundVo.getRefundDesc());
        Map<String, String> r = null;
        try {
            r = wxpay.refund(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    /**
     * 查询退款
     * 已经测试
     */
    public void doRefundQuery() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_refund_no", out_trade_no);
        try {
            Map<String, String> r = wxpay.refundQuery(data);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对账单下载
     * 已测试
     */
    public void doDownloadBill() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("bill_date", "20161102");
        data.put("bill_type", "ALL");
        try {
            Map<String, String> r = wxpay.downloadBill(data);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 分销用户提现
     *
     * @throws Exception
     */

    public Map<String, String> doWithdrawalForUser(FxWithdrawalVO fxWithdrawalVO) throws Exception {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("mch_appid", config.getAppID());
        data.put("mchid", config.getMchID());
        data.put("partner_trade_no", fxWithdrawalVO.getPartnerTradeNo());
        data.put("openid", fxWithdrawalVO.getOpenid());
        data.put("check_name", "NO_CHECK");
        data.put("re_user_name", fxWithdrawalVO.getReUserName());
        data.put("amount", fxWithdrawalVO.getAmount());
        data.put("desc", "分销小程序用户提现");
        data.put("spbill_create_ip", fxWithdrawalVO.getSpbillCreateIp());
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        String sign = WXPayUtil.generateSignature(data, config.getKey());
        data.put("sign", sign);
        WXPay wxPay = new WXPay(config);
        String result = wxPay.requestWithCert("/mmpaymkttransfers/promotion/transfers", data, 10000, 10000);
        Map<String, String> map = WXPayUtil.xmlToMap(result);
        map.put("respXml", result);
        return map;
    }

}

