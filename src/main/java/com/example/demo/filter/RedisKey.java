package com.example.demo.filter;


//redis 前缀类
public interface RedisKey {
	String LOGIN_KEY_USER_ID = "login_key_user_id";//登陆key id前缀
	String LOGIN_KEY_USER_TOKEN = "login_key_user_token";//登陆key token前缀
	
	String LOGIN_KEY_PHONE = "login_key_user_phone";//登陆key 手机前缀
	String PRE_PHONE_NUM="pre_phone_num";//前一次验证码前缀
	
	
	String BACK_LOGIN_KEY_USER_ID = "back_login_key_user_id";//登陆key id前缀

}
