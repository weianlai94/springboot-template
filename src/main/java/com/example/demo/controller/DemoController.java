package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.example.demo.common.alipay.AlipayVO;
import com.example.demo.common.dto.UserDTO;
import com.example.demo.common.entity.JsonResultEntity;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
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
    @Autowired
    private JavaMailSender javaMailSender;

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

    @ApiOperation(value = "测试发送邮件", notes = "测试发送邮件")
    @RequestMapping(value = "testSendMsg", method = RequestMethod.POST)
    public ResponseEntity<JsonResultEntity> testSendMsg() {
        ResponseEntity<JsonResultEntity> result = null;
        try {
            // 构造Email消息
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("961935154@qq.com");
            message.setTo("3445083416@qq.com");
            message.setSubject("这是一个简单的文本邮件测试");
            message.setText("20世纪90年代，硬件领域出现了单片式计算机系统，这种价格低廉的系统一出现就立即引起了自动控制领域人员的注意，因为使用它可以大幅度提升消费类电子产品（如电视机顶盒、面包烤箱、移动电话等）的智能化程度。Sun公司为了抢占市场先机，在1991年成立了一个称为Green的项目小组，帕特里克、詹姆斯·高斯林、麦克·舍林丹和其他几个工程师一起组成的工作小组在加利福尼亚州门洛帕克市沙丘路的一个小工作室里面研究开发新技术，专攻计算机在家电产品上的嵌入式应用。\n" +
                    "由于C++所具有的优势，该项目组的研究人员首先考虑采用C++来编写程序。但对于硬件资源极其匮乏的单片式系统来说，C++程序过于复杂和庞大。另外由于消费电子产品所采用的嵌入式处理器芯片的种类繁杂，如何让编写的程序跨平台运行也是个难题。为了解决困难，他们首先着眼于语言的开发，假设了一种结构简单、符合嵌入式应用需要的硬件平台体系结构并为其制定了相应的规范，其中就定义了这种硬件平台的二进制机器码指令系统（即后来成为“字节码”的指令系统），以待语言开发成功后，能有半导体芯片生产商开发和生产这种硬件平台。对于新语言的设计，Sun公司研发人员并没有开发一种全新的语言，而是根据嵌入式软件的要求，对C++进行了改造，去除了留在C++的一些不太实用及影响安全的成分，并结合嵌入式系统的实时性要求，开发了一种称为Oak的面向对象语言。\n" +
                    "由于在开发Oak语言时，尚且不存在运行字节码的硬件平台，所以为了在开发时可以对这种语言进行实验研究，他们就在已有的硬件和软件平台基础上，按照自己所指定的规范，用软件建设了一个运行平台，整个系统除了比C++更加简单之外，没有什么大的区别。1992年的夏天，当Oak语言开发成功后，研究者们向硬件生产商进行演示了Green操作系统、Oak的程序设计语言、类库和其硬件，以说服他们使用Oak语言生产硬件芯片，但是，硬件生产商并未对此产生极大的热情。因为他们认为，在所有人对Oak语言还一无所知的情况下，就生产硬件产品的风险实在太大了，所以Oak语言也就因为缺乏硬件的支持而无法进入市场，从而被搁置了下来。");
            javaMailSender.send(message);
            result = ResponseEntity.ok(JsonResultUtils.success());
        } catch (Exception e) {
            log.error("发送邮件失败", e);
        }
        return result;
    }

    @ApiOperation(value = "测试发送富文本邮件", notes = "测试发送富文本邮件")
    @RequestMapping(value = "testSendFWBMsg", method = RequestMethod.POST)
    public ResponseEntity<JsonResultEntity> testSendFWBMsg() {
        ResponseEntity<JsonResultEntity> result = null;
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("961935154@qq.com");
            mimeMessageHelper.setTo("3445083416@qq.com");
            mimeMessageHelper.setSubject("这是一个富文本邮件测试");
            String html = "<html><body><h4>Hello,SpringBoot</h4><img src='cid:boot' /></body></html>";
            mimeMessageHelper.setText(html, true);
            // 设置内嵌元素 cid，第一个参数表示内联图片的标识符，第二个参数标识资源引用
            mimeMessageHelper.addInline("boot", new ClassPathResource("test111.jpg"));
            javaMailSender.send(mimeMessage);
            result = ResponseEntity.ok(JsonResultUtils.success());
        } catch (Exception e) {
            log.error("发送邮件失败", e);
        }
        return result;
    }

    /**
     * 简单测试请求日志的记录
     * @param request 请求对象
     * @param name 用户名
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public JSONObject login(HttpServletRequest request,String name) throws Exception
    {
        JSONObject obj = new JSONObject();
        obj.put("msg","用户："+name+"，登录成功。");
        //将返回值写入到请求对象中
        request.setAttribute(LoggerUtils.LOGGER_RETURN,obj);
        return obj;
    }

}