package com.example.demo.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
	protected static Logger logger = LoggerFactory.getLogger(CommonUtils.class);
	
	/**
	 * 判断String 是否为空 
	 * @param date
	 * @param format
	 * @return
	 */
    public static boolean stringIsEmpty(String str) {
    	 if (str == null||"".equals(str)||"".equals(str.trim())) {
    		 return false;
    	 }else {
    		 return true;
    	 }
    }
	
	/**
	 * String 转 Date
	 * @param date
	 * @param format
	 * @return
	 */
    public static Date stringToDate(String str, SimpleDateFormat format) {
    	try {
	        if (str == null||"".equals(str)) {
	            return null;
	        }else {
		        if (format == null) {
		            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        }
	        	return format.parse(str);
	        }
        } catch (Exception e) {
        	logger.info("日期格转换失败::::::::");
        }
        return null;
    }
	
	
	/**
	 * date 转String
	 * @param date
	 * @param format
	 * @return
	 */
    public static String dateToString(Date date, SimpleDateFormat format) {
    	try {
	        if (format == null) {
	            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        }
            String strDate = format.format(date);
            return strDate;
        } catch (Exception e) {
        	logger.info("日期格转换失败::::::::");
        }
        return null;
    }
	
	/**
	 * 校验时间参数(String 版本)
	 * @param data
	 * @return
	 */
    public static boolean timeParamCheck(String startTime,String endTime, SimpleDateFormat format) {
    	if(format==null) {
    		format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	}
    	if(startTime==null||"".equals(startTime)||endTime==null||"".equals(endTime)||stringToDate(startTime, format).getTime()>stringToDate(endTime, format).getTime()) {
    		return false;
    	}
        return true;
    }
	
	/**
	 * 校验分页参数
	 * @param data
	 * @return
	 */
    public static boolean pageParamCheck(Integer pageNo,Integer pageSize) {
    	if(pageNo==null||pageSize==null||pageNo<0||pageSize<0) {
    		return false;
    	}
        return true;
    }
    
	/**
	 * 校验传入的数据是否在传入的列表中
	 * @param data
	 * @return
	 */
    public  static <T> boolean checkParamInList(T origin,T... targetList) {
    	boolean flag = false;
    	if(origin==null||targetList==null||targetList.length==0){
    		flag = false;
    	}else {
    		for (T t : targetList) {//如果传入对象 请记得重新equals 和hashcode方法
				if(origin.equals(t)) {
					flag = true;
					break;
				}
			}
    	}
        return flag;
    }
    
    /**
     * 下划线转驼峰法
     * @param line 源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line,boolean smallCamel){
        if(line==null||"".equals(line)){
            return "";
        }
        StringBuffer sb=new StringBuffer();
        Pattern pattern=Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(smallCamel&&matcher.start()==0?Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
            int index=word.lastIndexOf('_');
            if(index>0){
                sb.append(word.substring(1, index).toLowerCase());
            }else{
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
    
    
    /**
     * 驼峰法转下划线
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line){
        if(line==null||"".equals(line)){
            return "";
        }
        line=String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb=new StringBuffer();
        Pattern pattern=Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end()==line.length()?"":"_");
        }
        return sb.toString();
    }
    
    /**
     * 匹配 n到m个(包含m-n) 中英文+数字字符串
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static boolean chineseEnglishNum(String line,int n ,int m){
        if(line==null||"".equals(line)||m<n){
            return false;
        }
        Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5a-zA-Z0-9]{"+n+","+m+"}");
        Matcher matcher=pattern.matcher(line);
        if(matcher.matches()){
        	return true;
        }else {
        	return false;
        }
    }
    
    /**
     * 匹配 n到m个(包含m-n) 英文+数字字符串
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static boolean englishNum(String line,int n ,int m){
        if(line==null||"".equals(line)||m<n){
            return false;
        }
        Pattern pattern=Pattern.compile("[a-zA-Z0-9]{"+n+","+m+"}");
        Matcher matcher=pattern.matcher(line);
        if(matcher.matches()){
        	return true;
        }else {
        	return false;
        }
    }
    public static void main(String[] args) {
    	Map<String, String> map = new HashMap<>();
    	map.put("aaa", "bbb");
    	map.put("ccc", "bbb");
    	map.put("ddd", "bbb");
    }
    /**
     * 根据传入的年月获取下一个月份
     * @param date
     * @return
     */
    public static Date nextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }
    
    public static void printDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        System.out.println(sdf.format(date));
    }

}
