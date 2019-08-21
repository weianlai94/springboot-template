package com.example.demo.common.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 验证码生成器
 * 可生成数字、大写、小写字母及三者混合类型的验证码
 * 支持自定义验证码字符数量,支持自定义验证码图片的大小,支持自定义需排除的特殊字符,支持自定义干扰线的数量,支持自定义验证码图文颜色
 */
public class DynamicCodeUtil {

    public final static Log logger = LogFactory.getLog(DynamicCodeUtil.class);
    /**
     * 验证码类型为仅数字,即0~9
     */
    public static final int TYPE_NUM_ONLY = 0;
    /**
     * 验证码类型为仅字母,即大小写字母混合
     */
    public static final int TYPE_LETTER_ONLY = 1;
    /**
     * 验证码类型为数字和大小写字母混合
     */
    public static final int TYPE_ALL_MIXED = 2;
    /**
     * 验证码类型为数字和大写字母混合
     */
    public static final int TYPE_NUM_UPPER = 3;
    /**
     * 验证码类型为数字和小写字母混合
     */
    public static final int TYPE_NUM_LOWER = 4;
    /**
     * 验证码类型为仅大写字母
     */
    public static final int TYPE_UPPER_ONLY = 5;
    /**
     * 验证码类型为仅小写字母
     */
    public static final int TYPE_LOWER_ONLY = 6;
    private static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static SecureRandom random = new SecureRandom();

    private DynamicCodeUtil() {
    }

    /**
     * 生成验证码字符串
     *
     * @param type          验证码类型,参见本类的静态属性
     * @param length        验证码长度,要求大于0的整数
     * @param excludeString 需排除的特殊字符（无需排除则为null）
     * @return 验证码字符串
     */
    public static String generateCode(int type, int length, String excludeString) {
        if (length <= 0) {
            return "";
        }
        StringBuffer verifyCode = new StringBuffer();
        int i = 0;
        Random random = new Random();
        switch (type) {
            case TYPE_NUM_ONLY:
                while (i < length) {
                    int t = random.nextInt(10);
                    // 排除特殊字符
                    if (null == excludeString || excludeString.indexOf(t + "") < 0) {
                        verifyCode.append(t);
                        i++;
                    }
                }
                break;
            case TYPE_LETTER_ONLY:
                while (i < length) {
                    int t = random.nextInt(123);
                    boolean right = t >= 97 || (t >= 65 && t <= 90);
                    boolean left = null == excludeString || excludeString.indexOf((char) t) < 0;
                    if (right && left) {
                        verifyCode.append((char) t);
                        i++;
                    }
                }
                break;
            case TYPE_ALL_MIXED:
                length = length > 17 ? length : 17;
                verifyCode.append(randomBase62(3) + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + randomBase62(length - 17));
                break;
            case TYPE_NUM_UPPER:
                while (i < length) {
                    int t = random.nextInt(91);
                    boolean right = t >= 65 || (t >= 48 && t <= 57);
                    boolean left = null == excludeString || excludeString.indexOf((char) t) < 0;
                    if (right && left) {
                        verifyCode.append((char) t);
                        i++;
                    }
                }
                break;
            case TYPE_NUM_LOWER:
                while (i < length) {
                    int t = random.nextInt(123);
                    boolean right = t >= 97 || (t >= 48 && t <= 57);
                    boolean left = null == excludeString || excludeString.indexOf((char) t) < 0;
                    if (right && left) {
                        verifyCode.append((char) t);
                        i++;
                    }
                }
                break;
            case TYPE_UPPER_ONLY:
                while (i < length) {
                    int t = random.nextInt(91);
                    boolean left = null == excludeString || excludeString.indexOf((char) t) < 0;
                    if ((t >= 65) && left) {
                        verifyCode.append((char) t);
                        i++;
                    }
                }
                break;
            case TYPE_LOWER_ONLY:
                while (i < length) {
                    int t = random.nextInt(123);
                    boolean left = null == excludeString || excludeString.indexOf((char) t) < 0;
                    if ((t >= 97) && left) {
                        verifyCode.append((char) t);
                        i++;
                    }
                }
                break;
            default:
                verifyCode.append("");
        }
        return verifyCode.toString();
    }

    /**
     *生成随机数
     * @param length
     */

    public static String randomBase62(int length) {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return encodeBase62(randomBytes);
    }

    public static String encodeBase62(byte[] input) {
        char[] chars = new char[input.length];
        for (int i = 0; i < input.length; i++) {
            chars[i] = BASE62[(input[i] & 0xFF) % BASE62.length];
        }
        return new String(chars);
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

    public static void main(String[] args) {
    }
}