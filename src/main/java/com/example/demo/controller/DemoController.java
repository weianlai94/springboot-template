package com.example.demo.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.example.demo.common.alipay.AlipayVO;
import com.example.demo.common.dto.UserDTO;
import com.example.demo.common.entity.JsonResultEntity;
import com.example.demo.common.entity.User;
import com.example.demo.common.enums.MessageEnum;
import com.example.demo.common.utils.*;
import com.example.demo.common.weixinpay.WXPayVO;
import com.example.demo.filter.JwtUtil;
import com.example.demo.service.CountryService;
import com.example.demo.service.JedisService;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author weianlai
 * @date 2018-12-29 15:53
 */
@RestController
@Api(description = "demo")
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CountryService countryService;
    @Autowired
    private JedisService jedisService;

    @ApiOperation(value = "这个一个demo", notes = "notes")
    @RequestMapping(value = "/demo", method = RequestMethod.POST)
    public String demo() {
        return "this is a demo";
    }

    @ApiOperation(value = "test mybatis", notes = "")
    @RequestMapping(value = "getuser", method = RequestMethod.GET)
    public Map getUser(Integer id) {
//        int i = 1 / 0;
        return userService.getUser(id);
    }

    @ApiOperation(value = "test mybatis", notes = "")
    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    public UserDTO getUserInfo(Integer id) {
        if (id == null) {
            return null;
        }
        return userService.getUserInfo(id);
    }

    @ApiOperation(value = "测试redis", notes = "")
    @RequestMapping(value = "testRedis", method = RequestMethod.POST)
    public String testRedis() {
        return userService.testRedis();
    }

    @ApiOperation(value = "测试统一json返回", notes = "")
    @RequestMapping(value = "testJsonReturn", method = RequestMethod.POST)
    public ResponseEntity<JsonResultEntity> testJsonReturn() {
        return userService.testJsonReturn();
    }

    @ApiOperation(value = "测试PageHelper", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", required = false, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "testPageHelper", method = RequestMethod.GET)
    public ResponseEntity<JsonResultEntity> testPageHelper(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return userService.testPageHelper(page, pageSize);
    }

    @ApiOperation(value = "登陆返回token", notes = "测试jwt")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<JsonResultEntity> login(String mobile, String password) {
        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(password)) {
            return ResponseEntity.ok(JsonResultUtils.error(10001, "用户名或密码不正确"));
        }
        //只做演示，不做数据查询校验
        return userService.login(mobile, password);
    }

    @ApiOperation(value = "测试jwt", notes = "测试jwt")
    @ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", paramType = "query")
    @RequestMapping(value = "testJwt", method = RequestMethod.POST)
    public ResponseEntity<JsonResultEntity> testJwt(Integer uid, HttpServletRequest request) {
        Map<String, Object> claims = jwtUtil.validateTokenAndGetClaims(request);
        String userId = (String) claims.get("uid");
        if (userId == null || !uid.equals(Integer.parseInt(userId))) {
            return ResponseEntity.ok(JsonResultUtils.error(MessageEnum.YOU_CANT_DO_THIS.getCode(), MessageEnum.YOU_CANT_DO_THIS.getMessage()));
        }
        return ResponseEntity.ok(JsonResultUtils.success());
    }

    @ApiOperation(value = "测试logback", notes = "测试logback")
    @RequestMapping(value = "testLog", method = RequestMethod.GET)
    public void testLog() {
        log.info("test log");
    }

    // 每5秒执行一次
    @Scheduled(cron = "0/1 * * * * ?")
    public void testTask() {
       // System.out.println("test task");
    }

    @ApiOperation(value = "testMapper", notes = "testMapper")
    @RequestMapping(value = "testMapper", method = RequestMethod.POST)
    public void testMapper() {
        countryService.mapperDemo();
    }

    @ApiOperation(value = "测试微信扫码支付", notes = "测试微信支付")
    @RequestMapping(value = "testNATIVEWXPay", method = RequestMethod.POST)
    public ResponseEntity<JsonResultEntity> testNATIVEWXPay(@RequestBody WXPayVO wxPayVO, HttpServletRequest request) {
        ResponseEntity<JsonResultEntity> result = null;
        try {
            result = userService.testNATIVEWXPay(wxPayVO, request);
        } catch (Exception e) {
            log.error("支付错误", e);
        }
        return result;
    }

    @ApiOperation(value = "微信支付回调", notes = "微信支付回调")
    @RequestMapping(value = "wxpayreturn", method = RequestMethod.POST)
    public String wxpayreturn(HttpServletRequest request, HttpServletResponse response) {
        String result = null;
        try {
            result = userService.wxpayreturn(request, response);
        } catch (Exception e) {
            log.error("微信回调错误", e);
        }
        return result;
    }

    @ApiOperation(value = "微信支付结果查询", notes = "微信支付结果查询")
    @RequestMapping(value = "orderQuery", method = RequestMethod.POST)
    @ApiImplicitParam(name = "orderNo", value = "订单号", required = true, dataType = "string", paramType = "query")
    public ResponseEntity<JsonResultEntity> orderQuery(String orderNo) {
        ResponseEntity<JsonResultEntity> result = null;
        try {
            result = userService.orderQuery(orderNo);
        } catch (Exception e) {
            log.error("微信回调错误", e);
        }
        return result;
    }

    @ApiOperation(value = "测试支付宝扫码支付", notes = "测试支付宝扫码支付")
    @RequestMapping(value = "testNATIVEAlipay", method = RequestMethod.POST)
    public ResponseEntity<JsonResultEntity> testNATIVEAlipay(@RequestBody AlipayVO alipayVO, HttpServletRequest request) {
        ResponseEntity<JsonResultEntity> result = null;
        try {
            result = userService.testNATIVEAlipay(alipayVO, request);
        } catch (Exception e) {
            log.error("支付错误", e);
        }
        return result;
    }

    @ApiOperation(value = "发送短信验证码", notes = "")
    @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = "/sendAuthCode", method = RequestMethod.POST)
    public ResponseEntity<JsonResultEntity> sendAuthCode(String mobile) {
        ResponseEntity<JsonResultEntity> result = null;
        if(!CommonUtils.stringIsEmpty(mobile)||!ValidateUtils.checkMobile(mobile)) {
            result = ResponseEntity.ok(JsonResultUtils.error(MessageEnum.MOBILE_IS_ERROR.getCode(), MessageEnum.MOBILE_IS_ERROR.getMessage()));
        }else {
//    		 String num = jedisServer.get("phone_num"+mobile);
//    		 if(num!=null&&Integer.parseInt(num)>=5) {
//    			 result = ResponseEntity.ok(JsonResultUtils.error(MessageEnum.SEND_CODE_TOO_MANY.getCode(), MessageEnum.SEND_CODE_TOO_MANY.getMessage()));
//    		 }else {
            String codeOrigin = jedisService.get("base_user"+mobile);
            if(codeOrigin!=null) {
                result = ResponseEntity.ok(JsonResultUtils.error(MessageEnum.FREQUENT_SENDING.getCode(), MessageEnum.FREQUENT_SENDING.getMessage()));
            }else {
                String authCode = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_NUM_ONLY, 6, null);
                SendSmsResponse response = MessageUtil.sendCode(mobile, authCode);
                if("OK".equals(response.getCode())) {
//                     Integer sendNum = (num==null?0:Integer.parseInt(num))+1;
//                     jedisService.set("phone_num"+mobile, sendNum+"");
                    jedisService.setKeyAndExpire("base_user"+mobile, authCode, 10*60);
                    result = ResponseEntity.ok(JsonResultUtils.success());
                }else {
                    result = ResponseEntity.ok(JsonResultUtils.error(MessageEnum.COMMON_UNKNOW_ERROR.getCode(), "发送验证码失败"));
                }
            }
//    		 }
        }
        return result;
    }

}