package com.example.demo.common.utils;


import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MD5Utils {
	
//	生成密码随机盐
    public static String createSolt(int n){
        String str="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb=new StringBuilder(n);
        for(int i=0;i<n;i++){
        //public int nextInt(int n) 该方法的作用是生成一个随机的int值，该值介于[0,n)的区间，也就是0到n之间的随机int值，包含0而不包含n。
        	char ch=str.charAt(new Random().nextInt(str.length()));
        	sb.append(ch);
        }
        return sb.toString();
    }
    /**
     * 利用MD5进行加密
     *
     * @param str 待加密的字符串
     * @return 加密后的字符串
     * @throws NoSuchAlgorithmException     没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException
     */
    public static String EncoderByMd5(String str) {
        //确定计算方法
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
            return newstr;
        } catch (Exception e) {
            return str;
        }

    }

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	System.out.println(MD5Utils.createSolt(6));
    }

}
