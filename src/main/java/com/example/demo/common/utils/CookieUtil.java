package com.example.demo.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtil {

    public static final String JDBT_MOBILE = "JdbtMobile";
    /**
     * 设置cookie
     *
     * @param response
     * @param name     cookie名字
     * @param value    cookie值
     * @param maxAge   cookie生命周期  以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }


    public static void addJdbtCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setDomain("xxxx.com");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }
    /**
     * 静默授权获取userId
     *
     * @return
     */
    public static String baseUserId(HttpServletRequest request) {
        Cookie userId = getCookieByName(request, "baseUserId");
        if (userId == null) {
            return null;
        } else {
            return userId.getValue();
        }
    }

    /**
     * 生活号，花呗获取ookie中的userId
     *
     * @param request
     * @return
     */
    public static String getUserId(HttpServletRequest request) {
        Cookie userId = getCookieByName(request, "userId");
        if (userId == null) {
            return null;
        } else {
            return userId.getValue();
        }
    }

    /**
     * 获取app uid
     *
     * @param request
     * @return
     */
    public static Integer getAPPUserId(HttpServletRequest request) {
        Cookie userId = getCookieByName(request, "APPUserId");
        if (userId == null) {
            return null;
        } else {
            return Integer.parseInt(userId.getValue());
        }
    }

    /**
     * 获取uid
     *
     * @param request
     * @return
     */
    public static Integer getUid(HttpServletRequest request) {
        Cookie uid = getCookieByName(request, "uid");
        if (uid == null) {
            return null;
        } else {
            return Integer.parseInt(uid.getValue());
        }
    }

    /**
     * 获取京东白条 手机
     *
     * @param request
     * @return
     */
    public static String getJdbtMobile(HttpServletRequest request) {
        Cookie cookie = getCookieByName(request, JDBT_MOBILE);
        if (cookie == null) {
            return "";
        } else {
            return cookie.getValue();
        }
    }

    /**
     * 根据名字获取cookie
     *
     * @param request
     * @param name    cookie名字
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }


    /**
     * 将cookie封装到Map里面
     *
     * @param request
     * @return
     */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

}
