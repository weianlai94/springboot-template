package com.example.demo.config;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建fastjson消息转换器
        FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
        // 创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 修改配置返回内容的过滤
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
//                SerializerFeature.WriteMapNullValue
                SerializerFeature.WriteNullStringAsEmpty
        );
        fastJsonConverter.setFastJsonConfig(fastJsonConfig);
        // 将fastJsont添加到视图消息转化器列表
        converters.add(fastJsonConverter);
    }

}
