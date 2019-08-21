package com.example.demo.common.utils;

import okhttp3.*;
import okhttp3.Headers.Builder;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtil {
    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    /**
     * get请求获取请求数据
     *
     * @param url  路径
     * @param parameters 参数
     */
    public static String httpGetAndParam(String url, Map<String, String> parameters) throws Exception {
        url = getFullUrl(url, parameters);
        String getData;
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            getData = response.body().string();
        } catch (Exception e) {
            throw new RuntimeException("请求失败");
        }
        return getData;
    }

    /**
     * get请求获取请求数据
     * @param url 路径
     * @param headers  header中的参数
     * @param querys  参数
     */
    public static String httpGetAndHearder(String url,
                                           Map<String, String> headers,
                                           Map<String, String> querys) throws Exception {
        Headers header = null;
        Builder headersbuilder = new Builder();
        for (Map.Entry<String, String> e : headers.entrySet()) {
            headersbuilder.add(e.getKey(), e.getValue());
        }
        header = headersbuilder.build();
        url = getFullUrl(url, querys);
        String getData;
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .headers(header)
                .url(url)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            getData = response.body().string();
        } catch (Exception e) {
            throw new RuntimeException("请求失败");
        }
        return getData;
    }


    /**
     * get请求获取请求数据
     *
     * @param url
     */
    public static String httpGet(String url) {
        String getData;
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            getData = response.body().string();
        } catch (Exception e) {
            throw new RuntimeException("请求失败");
        }
        return getData;
    }


    /**
     * post请求获取请求数据
     *
     * @param url
     * @param json json数据的生成方式（可选）；
     *             JSONObject json=new JSONObject();
     *             json.put("name","张三");
     *             json.put("sex","男");等
     *             json.toString()
     * @return
     */
    public static String httpPost(String url, String json) {
        String postData;
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            postData = response.body().string();
        } catch (Exception e) {
            throw new RuntimeException("请求失败");
        }
        return postData;
    }

    public static String httpDelete(String url, String json) {
        String postData;
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .delete(requestBody)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            postData = response.body().string();
        } catch (Exception e) {
            throw new RuntimeException("请求失败");
        }
        return postData;
    }

    /**
     * @param url  请求路径
     * @param json json数据
     * @return
     */

    public static String httpPut(String url, String json) {
        String postData;
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            postData = response.body().string();
        } catch (Exception e) {
            throw new RuntimeException("请求失败");
        }
        return postData;
    }

    public static String getFullUrl(String url, Map<String, String> parameters) throws Exception {
        StringBuffer sb = new StringBuffer();
        String params = "";
        // 编码请求参数
        if (parameters.size() == 1) {
            for (String name : parameters.keySet()) {
                sb.append(name).append("=").append(
                        URLEncoder.encode(parameters.get(name), "UTF-8"));
            }
            params = sb.toString();
        } else {
            for (String name : parameters.keySet()) {
                sb.append(name).append("=").append(
                        URLEncoder.encode(parameters.get(name),
                                "UTF-8")).append("&");
            }
            String temp_params = sb.toString();
            params = temp_params.substring(0, temp_params.length() - 1);
        }
        String full_url = url + "?" + params;
        return full_url;
    }

    /**
     * 从请求中获取真实IP
     *
     * @param request
     * @return
     */
    public static String getRealIp(HttpServletRequest request) {
        // 多级nginx配置时
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        // 单级nginx 代理时
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        // 没有代理时获取真实ip
        return request.getRemoteAddr();
    }


}
