package com.example.demo.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ExpressUtil {

    private static String appcode;

    public static void setAppcode(String appcode) {
        ExpressUtil.appcode = appcode;
    }

    /**
     * 物流信息的接口
     *
     * @param expressCode
     * @param type
     * @return
     */
    public static String queryApi(String expressCode, String type) throws Exception {
        String url = "https://wuliu.market.alicloudapi.com/kdi";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("no", expressCode);
        querys.put("type", type);
        String result = HttpUtil.httpGetAndHearder(url, headers, querys);
        JSONObject jsonObject = JSONObject.parseObject(result);
        result = jsonObject.getString("result");
        return result;
    }
}
