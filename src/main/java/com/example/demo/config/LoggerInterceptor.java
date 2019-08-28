package com.example.demo.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.common.entity.LoggerInfos;
import com.example.demo.common.utils.LoggerUtils;
import com.example.demo.dao.mapper.LoggerInfosMapper;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggerInterceptor extends HandlerInterceptorAdapter {

    //请求开始时间标识
    private static final String LOGGER_SEND_TIME = "_send_time";
    //请求日志实体标识
    private static final String LOGGER_ENTITY = "_loggerInfos_entity";

    /**
     * 进入SpringMVC的Controller之前开始记录日志实体
     * @param request 请求对象
     * @param response 响应对象
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //创建日志实体
        LoggerInfos loggerInfos = new LoggerInfos();
        //获取请求sessionId
        String sessionId = request.getRequestedSessionId();
        //请求路径
        String uri = request.getRequestURI();
        //获取请求参数信息
        String paramData = JSON.toJSONString(request.getParameterMap(),
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue);
        //设置客户端ip
        loggerInfos.setClientIp(LoggerUtils.getCliectIp(request));
        //设置请求方法
        loggerInfos.setMethod(request.getMethod());
        //设置请求类型（json|普通请求）
        loggerInfos.setType(LoggerUtils.getRequestType(request));
        //设置请求参数内容json字符串
        loggerInfos.setParamData(paramData);
        //设置请求地址
        loggerInfos.setUri(uri);
        //设置sessionId
        loggerInfos.setSessionId(sessionId);
        //设置请求开始时间
        request.setAttribute(LOGGER_SEND_TIME,System.currentTimeMillis());
        //设置请求实体到request内，方便afterCompletion方法调用
        request.setAttribute(LOGGER_ENTITY,loggerInfos);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        //获取请求错误码
        int status = response.getStatus();
        //当前时间
        long currentTime = System.currentTimeMillis();
        //请求开始时间
        long time = Long.valueOf(request.getAttribute(LOGGER_SEND_TIME).toString());
        //获取本次请求日志实体
        LoggerInfos loggerInfosInfosEntity = (LoggerInfos) request.getAttribute(LOGGER_ENTITY);
        //设置请求时间差
        loggerInfosInfosEntity.setTimeConsuming(Integer.valueOf((currentTime - time)+""));
        //设置返回时间
        loggerInfosInfosEntity.setReturnTime(currentTime + "");
        //设置返回错误码
        loggerInfosInfosEntity.setHttpStatusCode(status+"");
        //设置返回值
        loggerInfosInfosEntity.setReturnData(JSON.toJSONString(request.getAttribute(LoggerUtils.LOGGER_RETURN),
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue));
        //执行将日志写入数据库
        LoggerInfosMapper loggerInfosInfosMapper = getDAO(LoggerInfosMapper.class,request);
        loggerInfosInfosMapper.insert(loggerInfosInfosEntity);
    }

    /**
     * 根据传入的类型获取spring管理的对应dao
     * @param clazz 类型
     * @param request 请求对象
     * @param <T>
     * @return
     */
    private <T> T getDAO(Class<T> clazz,HttpServletRequest request)
    {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }

}
