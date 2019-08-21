package com.example.demo.common.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;


public class SmsUtil {

    private static final String product = "Dysmsapi";
    private static final String domain = "dysmsapi.aliyuncs.com";
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String signName;
    private static String templateParam;


    public static SendSmsResponse sendSms(String mobile, String vecode) throws ClientException {
        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers(mobile);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateParam);
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\"" + vecode + "\"}");
        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者messaage
        request.setOutId(vecode);
        // hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        // 从session中比对发送的验证码
        return sendSmsResponse;
    }

    public static void setAccessKeyId(String accessKeyId) {
        SmsUtil.accessKeyId = accessKeyId;
    }

    public static void setAccessKeySecret(String accessKeySecret) {
        SmsUtil.accessKeySecret = accessKeySecret;
    }

    public static void setSignName(String signName) {
        SmsUtil.signName = signName;
    }

    public static void setTemplateParam(String templateParam) {
        SmsUtil.templateParam = templateParam;
    }

    public static String getAccessKeyId() {
        return accessKeyId;
    }

    public static String getAccessKeySecret() {
        return accessKeySecret;
    }

    public static String getSignName() {
        return signName;
    }

    public static String getTemplateParam() {
        return templateParam;
    }
}
