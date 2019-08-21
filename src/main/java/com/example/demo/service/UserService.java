package com.example.demo.service;

import com.example.demo.common.alipay.AlipayVO;
import com.example.demo.common.dto.UserDTO;
import com.example.demo.common.entity.JsonResultEntity;
import com.example.demo.common.entity.User;
import com.example.demo.common.weixinpay.WXPayVO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author weianlai
 * @date 2019-01-02 15:35
 */
public interface UserService {

    /**
     * 根据id获取用户
     * @return
     */
    public User getUser();

    /**
     * 测试redis
     * @return
     */
    public String testRedis();

    /**
     * 测试json统一返回数据
     * @return
     */
    ResponseEntity<JsonResultEntity> testJsonReturn();

    /**
     * 测试PageHelper
     * @return
     */
    ResponseEntity<JsonResultEntity> testPageHelper(Integer page, Integer pageSize);

    /**
     * 登陆返回token
     * @param mobile
     * @param password
     * @return
     */
    ResponseEntity<JsonResultEntity> login(String mobile, String password);

    /**
     * 微信扫码支付
     * @return
     */
    ResponseEntity<JsonResultEntity> testNATIVEWXPay(WXPayVO wxPayVO, HttpServletRequest request) throws Exception;

    /**
     * 微信支付回调
     * @param request
     * @param response
     * @return
     */
    String wxpayreturn(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 微信支付查询
     */
    ResponseEntity<JsonResultEntity> orderQuery(String orderNo) throws Exception;

    /**
     * 支付宝扫码支付
     * @param alipayVO
     * @param request
     * @return
     */
    ResponseEntity<JsonResultEntity> testNATIVEAlipay(AlipayVO alipayVO, HttpServletRequest request);

    UserDTO getUserInfo(int id);

}
