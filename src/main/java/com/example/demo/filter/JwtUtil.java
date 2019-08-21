package com.example.demo.filter;


import com.example.demo.service.JedisService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtil {
    private  Logger logger= LoggerFactory.getLogger(JwtUtil.class);
    public   long EXPIRATION_TIME =1000*60*60*24*10; //token 失效时间
    public   String SECRET = "xxxx";// 秘钥
	public   String TOKEN_PREFIX = "xxx"; //秘钥前缀
    public   String HEADER_STRING = "xxx";// token 名
    @Autowired
    private JedisService jedisService;
 // 清除token
    public  String removeToken(String uid) {
        jedisService.del(RedisKey.LOGIN_KEY_USER_ID+uid);
        return "true";
    }

	// 客户端生成token
    public  String generateToken(String uid,Date gentTime) {
        HashMap<String, Object> map = new HashMap<>();
        jedisService.set(RedisKey.LOGIN_KEY_USER_ID+uid, gentTime.getTime()+"");
        map.put("uid",uid);
        map.put("type","fingo_client");
        String jwt = Jwts.builder()
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return TOKEN_PREFIX + jwt;
    }
	// 为后台生成token并将权限放入token内
    public  String generateTokenForSeller(String uid,Date gentTime) {
        HashMap<String, Object> map = new HashMap<>();
        jedisService.set(RedisKey.BACK_LOGIN_KEY_USER_ID+uid, gentTime.getTime()+"");
        map.put("uid",uid);
        map.put("type","fingo_server");
        String jwt = Jwts.builder()
                .setClaims(map)
//                .setExpiration(new Date(gentTime.getTime() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return TOKEN_PREFIX + jwt;
    }
	// 验证token
    public  Map<String, Object> validateTokenAndGetClaims(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token == null){
            throw new TokenValidationException(400,"请重新登录");
        }
        // parse the token. exception when token is invalid
        Map<String, Object> body = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
        return body;
    }
    
    public  Map<String, Object> validateByToken(String token) {
    	if(StringUtils.isBlank(token)) {
    		return null;
    	}
        Map<String, Object> body = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
        return body;
    }
}
