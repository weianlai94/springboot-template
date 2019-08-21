package com.example.demo.filter;

import com.example.demo.common.enums.MessageEnum;
import com.example.demo.service.JedisService;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private PathMatcher PATH_MATCHER = new AntPathMatcher();
	private JwtUtil jwtUtil = null;
//	private UserLogMapper userLogMapper = null;
	private JedisService jedisService = null;
	/** 需要token认证的数据的前台路径 */
	private String[] protectUrlPatterns = {
			//地址相关
			"/address/addAddress","/address/editAddress","/address/getAddressDetail","/address/getUserDefaultAddress","/address/getAddressListByPage",
			"/address/setAddressToDefault","/address/deleteAddress","/address/wxinsert",
	};
	/** 需要token认证的后台管理数据的路径 */
	private String[] sellerProtectUrlPatterns = {
			//bannner 模块
			"/banner_manage/listBanner","/banner_manage/listSplashScreen","/banner_manage/insertBanner","/banner_manage/insertSplashScreen","/banner_manage/viewBannerByid",
			"/banner_manage/updateBanner","/banner_manage/changeBannerSort","/banner_manage/deleteBanner","/banner_manage/batchDeleteBanner",
	};
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(isProtectedUrl(request)||isSellerProtectedUrl(request)) {
			jedisService = (JedisService) SpringContextUtil.getBean("jedisServerImpl");
			jwtUtil = (JwtUtil) SpringContextUtil.getBean("jwtUtil");
//			userLogMapper = (UserLogMapper) SpringContextUtil.getBean("userLogMapper");
			Map<String, Object> claims = jwtUtil.validateTokenAndGetClaims(request);
			String type = (String) claims.get("type");
			String uid = (String) claims.get("uid");
			if(uid==null) {
				response.sendError(MessageEnum.TOKEN_NOT_EXIST.getCode(), MessageEnum.TOKEN_NOT_EXIST.getMessage());
				return;
			}
			if(type==null||"".equals(type)||(!"fingo_client".equals(type)&&!"fingo_server".equals(type))) {//token 无效，或者类型不对。让用户去登录
				response.sendError(MessageEnum.PLEASE_LOGIN.getCode(), MessageEnum.PLEASE_LOGIN.getMessage());
				return;
			}else if(type.equals("fingo_client")){//前台类型token
				if (isProtectedUrl(request)) {
					Date cuurrentTime = new Date();
					String gentTime = jedisService.get(RedisKey.LOGIN_KEY_USER_ID+uid);
					if (gentTime == null||"".equals(gentTime)) {
						response.sendError(MessageEnum.PLEASE_LOGIN.getCode(), MessageEnum.PLEASE_LOGIN.getMessage());
						return;
					}else {
						long expireTime = new Long(gentTime)+jwtUtil.EXPIRATION_TIME;
						if( expireTime<cuurrentTime.getTime()) {
							response.sendError(MessageEnum.TOKEN_IS_INVALID.getCode(), MessageEnum.TOKEN_IS_INVALID.getMessage());
							return;
						}
						jedisService.set(RedisKey.LOGIN_KEY_USER_ID+uid,  new Date().getTime()+"");
					}
				}
			}else if(type.equals("fingo_server")) {//后台类型token
				if(isSellerProtectedUrl(request)) {
					Date cuurrentTime = new Date();
					String gentTime = jedisService.get(RedisKey.BACK_LOGIN_KEY_USER_ID+uid);
					if (gentTime == null||"".equals(gentTime)) {
						response.sendError(MessageEnum.PLEASE_LOGIN.getCode(), MessageEnum.PLEASE_LOGIN.getMessage());
						return;
					}else {
						long expireTime = new Long(gentTime)+jwtUtil.EXPIRATION_TIME;
						if( expireTime<cuurrentTime.getTime()) {
							response.sendError(MessageEnum.TOKEN_IS_INVALID.getCode(), MessageEnum.TOKEN_IS_INVALID.getMessage());
							return;
						}
					}
//					String resource = (String) claims.get(UsersEnum.REDIS_KEY.getMessage()+uid);
//					List<String> list = (List<String>) JSON.parse(resource);
//					JsonResultEntity JsonResultEntity = new JsonResultEntity();
//					JsonResultEntity.setCode(UsersEnum.NO_PERMISSION.getCode());ßß
//					JsonResultEntity.setMessage(UsersEnum.NO_PERMISSION.getMessage());
//					if(list!=null && list.size()>0) {
//						if (!list.contains(url)) {
//							response.setCharacterEncoding("UTF-8");
//							response.getWriter().print(JSON.toJSONString(JsonResultEntity));
//							return;
//						}
//						//验证通过 则记录操作
//						UserLogEntity entity = new UserLogEntity();
//						entity.setGmtCreate(cuurrentTime);
//						entity.setOperation(url);
//						entity.setStatus(0);
//						entity.setUserId(Integer.parseInt(uid));
//						userLogMapper.insert(entity);
//					}else if(list==null){
//						response.setCharacterEncoding("UTF-8");
//						response.getWriter().print(JSON.toJSONString(JsonResultEntity));
//						return;
//					}
					jedisService.set(RedisKey.BACK_LOGIN_KEY_USER_ID+uid,  new Date().getTime()+"");
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	private boolean isProtectedUrl(HttpServletRequest request) {
		for (String str : protectUrlPatterns) {
			if (PATH_MATCHER.match(str, request.getServletPath())) {
				return true;
			}
		}
		return false;
	}
	private boolean isSellerProtectedUrl(HttpServletRequest request) {
		for (String str : sellerProtectUrlPatterns) {
			if (PATH_MATCHER.match(str, request.getServletPath())) {
				return true;
			}
		}
		return false;
	}

}