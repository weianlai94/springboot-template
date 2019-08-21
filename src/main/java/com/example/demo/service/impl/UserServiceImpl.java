package com.example.demo.service.impl;

import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.example.demo.common.alipay.AlipayEntrance;
import com.example.demo.common.alipay.AlipayVO;
import com.example.demo.common.dto.UserDTO;
import com.example.demo.common.entity.JsonResultEntity;
import com.example.demo.common.entity.User;
import com.example.demo.common.enums.MessageEnum;
import com.example.demo.common.utils.JsonResultUtils;
import com.example.demo.common.utils.QrCodeUtil;
import com.example.demo.common.utils.ResponseUtils;
import com.example.demo.common.weixinpay.*;
import com.example.demo.dao.mapper.UserMapper;
import com.example.demo.filter.JwtUtil;
import com.example.demo.service.JedisService;
import com.example.demo.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author weianlai
 * @date 2019-01-02 15:35
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisService jedisService;
    @Autowired
    private JwtUtil jwtUtil;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public Map getUser(Integer id) {
        System.out.println(userMapper);
//        return userMapper.selectByPrimaryKey(id);
        return userMapper.getUser(id);
    }

    @Override
    public String testRedis() {
        String key = "demo:testRedis";
        jedisService.setKeyAndExpire(key, "this is test11111", 300);
        String val = null;
        if (jedisService.get(key) != null) {
            val = jedisService.get(key);
        }
        return val;
    }

    @Override
    public ResponseEntity<JsonResultEntity> testJsonReturn() {
        Map<String, String> map = new HashMap<>();
        map.put("test", "test is success");
        return ResponseEntity.ok(JsonResultUtils.success(map));
    }

    @Override
    public ResponseEntity<JsonResultEntity> testPageHelper(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<User> usersList = userMapper.selectAll();
        PageInfo<User> pageInfo = new PageInfo<>(usersList);
        return ResponseEntity.ok(JsonResultUtils.success(pageInfo));
    }

    @Override
    public ResponseEntity<JsonResultEntity> login(String mobile, String password) {
        //此处省去校验
        String token = jwtUtil.generateToken("1", new Date());
        Map<String, Object> map = new HashMap<>();
        map.put("uid", 1);
        map.put("token", token);
        return ResponseEntity.ok(JsonResultUtils.success(map));
    }

    @Override
    public ResponseEntity<JsonResultEntity> testNATIVEWXPay(WXPayVO wxPayVO, HttpServletRequest request) throws Exception{
        WXPayEntrance wxPayEntrance = new WXPayEntrance();
        wxPayVO.setTradeType("NATIVE");
        //wxPayVO.setTradeType("APP");
        String spbillCreateIp = ResponseUtils.getRealIp(request);
        wxPayVO.setSpbillCreateIp(spbillCreateIp);
        Map<String, String> resultMap = wxPayEntrance.doUnifiedOrder(wxPayVO);
        System.out.println(resultMap);
        String resultCode = resultMap.get("result_code");
        if (resultCode.equals("SUCCESS")) {
            String codeUrl = resultMap.get("code_url");
            String payUrl = QrCodeUtil.createQrCode(codeUrl);
            Map<String,String> map=new HashMap<>();
            map.put("url",payUrl);
            return   ResponseEntity.ok(JsonResultUtils.success(map, MessageEnum.STATUS_OK.getCode(),
                    MessageEnum.STATUS_OK.getMessage()));
        } else {
            String error_msg = resultMap.get("return_msg");
            System.out.println(error_msg);
            return ResponseEntity.ok(JsonResultUtils.error(MessageEnum.WEIXIN_CREATE_QRCODE_ERROR.getCode(), MessageEnum.WEIXIN_CREATE_QRCODE_ERROR.getMessage()));
        }
    }

    @Override
    public String wxpayreturn(HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println("=====================");
        String result = null;
        String inputLine;
        String notityXml = "";
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 微信返回
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = setXml("fail", "xml获取失败");
        } finally {
            request.getReader().close();
        }
        if (StringUtils.isEmpty(notityXml)) {
            result = setXml("fail", "xml获取失败");
        }
        Map<String, String> map = WXPayUtil.xmlToMap(notityXml);
        System.out.println(map);
        String out_trade_no = map.get("out_trade_no");
        String result_code = map.get("result_code");
        String total_fee = map.get("total_fee");
        String tradeNo= map.get("transaction_id");
        if ("SUCCESS".equals(map.get("result_code"))) {
            //成功
            System.out.println("SUCCESS");
            // 逻辑代码...

            //返回微信成功，避免重复回调
        } else {
            //失败
            System.out.println("FAIL");
            // 逻辑代码...
        }
        return setXml("SUCCESS", "OK");
    }

    // 通过xml 发给微信消息
    public static String setXml(String return_code, String return_msg) {
        SortedMap<String, String> parameters = new TreeMap<String, String>();
        parameters.put("return_code", return_code);
        parameters.put("return_msg", return_msg);
        return "<xml><return_code><![CDATA[" + return_code + "]]>" + "</return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }

    @Override
    public ResponseEntity<JsonResultEntity> orderQuery(String orderNo) throws Exception{
        WXPayEntrance wxPayEntrance = new WXPayEntrance();
        wxPayEntrance.doOrderQuery(orderNo);
        return ResponseEntity.ok(JsonResultUtils.success());
    }

    @Override
    public ResponseEntity<JsonResultEntity> testNATIVEAlipay(AlipayVO alipayVO, HttpServletRequest request) {
        ResponseEntity<JsonResultEntity> result = null;
        AlipayEntrance instance = AlipayEntrance.getInstance();
        alipayVO.setOutTradeNo("a12432563457457");
        alipayVO.setSubject("test");
        alipayVO.setTotalAmount("1");
        AlipayTradePrecreateResponse response=instance.generateQR(alipayVO);
        if(response.isSuccess()){
            logger.error(response.getQrCode());
            String url=QrCodeUtil.createQrCode(response.getQrCode());
            Map<String,String> map=new HashMap<>();
            System.out.println(url);
            map.put("url",url);
            result = ResponseEntity.ok(JsonResultUtils.success(map, MessageEnum.STATUS_OK.getCode(),
                    MessageEnum.STATUS_OK.getMessage()));
        }else{
            result = ResponseEntity.ok(JsonResultUtils.error(MessageEnum.COMMON_UNKNOW_ERROR.getCode(), MessageEnum.COMMON_UNKNOW_ERROR.getMessage()));
        }
        return result;
    }

    @Override
    public UserDTO getUserInfo(int id) {
        return userMapper.getUserInfo(id);
    }


}
