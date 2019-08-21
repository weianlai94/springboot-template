package com.example.demo.common.utils;



import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static java.util.regex.Pattern.matches;


public class ValidateUtils {
    public static boolean checkPageParam(Integer pageNo,Integer pageSize) {
    	if(pageNo==null||pageNo<1||pageSize==null||pageSize<1) {
    		return false;
    	}
    	return true;
    }

    /**
     * 验证字符串是否为空
     */
    public static boolean checkStr(String str) {
        if (StringUtils.isNotBlank(str)) {
            return true;
        }
        return false;
    }

    /**
     * 验证用户账号是否为空
     */
    public static boolean checkLong(Integer id) {
        String idStr = String.valueOf(id);
        return checkStr(idStr);
    }


    /**
     * 验证账号只能是数字和字母
     */
    public static boolean checkAccount(String str) {
        //只能是数字和字母
        boolean matches = Pattern.matches("[A-Za-z0-9]{1,11}", str);
        if (!matches) {
            return false;
        }
        return true;
    }


    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        if(email==null||"".equals(email)) {
        	return flag;
        }
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    //6到10位 字母或者数字
    public static boolean checkPassward(String str) {
        if(str==null||"".equals(str)) {
        	return false;
        }
    	Matcher m = null;
	    Pattern p = Pattern.compile("^[a-zA-Z0-9]{8,20}$"); 
	    m = p.matcher(str);
	    return m.matches();
    }

    /**
     * 验证邮编
     *
     * @param postCode
     * @return
     */
    public static boolean checkPostCode(String postCode) {
        String reg = "[1-9]\\d{5}";
        return matches(reg, postCode);
    }

    /**
     * 验证手机号码
     *
     * @param mobile
     * @return
     */
//    public static boolean checkMobile(String mobile) {
//        boolean flag = false;
//        try {
//            Pattern regx = compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[35678]|18[0-9]|19[89])\\d{8}$");
//            Matcher matcher = regx.matcher(mobile);
//            flag = matcher.matches();
//        } catch (Exception e) {
//            flag = false;
//        }
//        return flag;
//    }
    public static boolean checkMobile(String mobile) {
        boolean flag = false;
        try {
            Pattern regx = compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[35678]|18[0-9]|19[89])\\d{8}$");
            Matcher matcher = regx.matcher(mobile);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
    public static boolean isMobile(String mobile) {
    	boolean flag = false;
    	try {
    		Pattern regx = compile("^\\d{11}$");
    		Matcher matcher = regx.matcher(mobile);
    		flag = matcher.matches();
    	} catch (Exception e) {
    		flag = false;
    	}
    	return flag;
    }

    /**
     * 验证密码
     * @param password
     * @return
     */
    public static boolean isPassword(String password) {
        if (StringUtils.isBlank(password)) {
            return false;
        }

		/*boolean matches = Pattern.matches("[a-zA-Z0-9_]{6,16}", password);
		if (matches) {
			return true;
		}*/

        boolean matches = password.length()<6 || password.length()>12;
        if (matches) {
            return false;
        }
        //全是数字
        matches = Pattern.matches("[0-9]*", password);
        if (matches) {
            return false;
        }
        //是否只包含数字，不包含字符串
        matches = Pattern.matches("(.*?)\\d(.*?)", password);
        if (!matches) {
            return false;
        }

       /* //全是字母
		matches = Pattern.matches("[A-Za-z]*", password);
		if (matches) {
			return false;
		}
		//全是数字
		matches = Pattern.matches("[0-9]*", password);
		if (matches) {
			return false;
		}
		//只能是数字和字母
		 matches = Pattern.matches("[A-Za-z0-9]*", password);
		if (!matches) {
			return false;
		}*/

        return true;
    }
    /**
     * 从html中的src属性中获取图片路径
     */
    public static Set<String> getImgStr(String htmlStr) {
        Set<String> pics = new HashSet<String>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

    public static boolean checkPhone(String str) {
    	Matcher m = null;
	    Pattern p = Pattern.compile("^[0][0-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的,中间有"-"
	    m = p.matcher(str);
	    return m.matches();
    }
    public static void main(String[] args) {
		System.out.println(checkPhone("400-6679460"));
	}


    /**
     * 验证工号，只能是数字和字母
     *
     * @param id
     * @return
     */
    public static boolean checkEmployeeId(String id) {
        boolean flag = false;
        try {
            String check = "^[a-z0-9A-Z_]+$";
            Pattern regex = compile(check);
            Matcher matcher = regex.matcher(id);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证昵称，只能是汉字和字母数字
     *
     * @param nickname
     * @return
     */
    public static boolean checkNickname(String nickname) {
        boolean flag = false;
        try {
            String check = "^[0-9A-Za-z_\\u4e00-\\u9fa5]+$";
            Pattern regex = compile(check);
            Matcher matcher = regex.matcher(nickname);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
