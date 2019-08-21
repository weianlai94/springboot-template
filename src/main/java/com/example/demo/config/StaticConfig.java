package com.example.demo.config;

import com.example.demo.common.alipay.AlipayEntrance;
import com.example.demo.common.utils.ExpressUtil;
import com.example.demo.common.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class StaticConfig {
    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.sms.signName}")
    private String signName;
    @Value("${aliyun.sms.templateParam}")
    private String templateParam;
    @Value("${aliyun.oss.url}")
    private String url;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;
    @Value("${aliyun.oss.endPoint}")
    private String endPoint;
    @Value("${aliyun.oss.accessKeyId}")
    private String ossAccessKeyId;
    @Value("${aliyun.oss.secretAccessKey}")
    private String ossSecretAccessKey;
    @Value("${alipay.pid}")
    public String pid;
    @Value("${alipay.appId}")
    public String appId;
    @Value("${alipay.privateKey}")
    public String appPrivateKey;
    @Value("${alipay.publicKey}")
    public String appPublicKey;

    @Bean
    public void initStatic() {
        SmsUtil.setAccessKeyId(accessKeyId);
        SmsUtil.setAccessKeySecret(accessKeySecret);
        SmsUtil.setSignName(signName);
        SmsUtil.setTemplateParam(templateParam);
//        AbstractOssClientUtil.setUrl(url);
//        AbstractOssClientUtil.setBucketName(bucketName);
//        AbstractOssClientUtil.setEndPoint(endPoint);
//        AbstractOssClientUtil.setOssAccessKeyId(ossAccessKeyId);
//        AbstractOssClientUtil.setOssSecretAccessKey(ossSecretAccessKey);
        AlipayEntrance.setAppId(appId);
        AlipayEntrance.setPid(pid);
        AlipayEntrance.setAppPrivateKey(appPrivateKey);
        AlipayEntrance.setAppPublicKey(appPublicKey);
    }

}
