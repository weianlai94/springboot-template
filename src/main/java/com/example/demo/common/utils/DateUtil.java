package com.example.demo.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Slf4j
public class DateUtil {
    /**
     * 时间戳转化时间
     *
     * @param timeStamp 时间戳
     * @return 返回值
     * @title
     * @date:2017年6月28日
     */
    public static Date parseDate(Long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = sdf.format(timeStamp);
        return parseDates(d);
    }

    /**
     * 年-月-日字符串转日期
     *
     * @param dateStr
     * @return
     */
    public static Date parseDates(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * String 转 Date
     *
     * @param str
     * @param pattern
     * @return
     */
    public static Date stringToDate(String str, String pattern) {

        try {
            if (str == null || "".equals(str)) {
                return null;
            } else {
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                return format.parse(str);
            }
        } catch (Exception e) {
            //log.info("日期格转换失败::::::::");
        }
        return null;
    }

    /**
     * 根据传入的年月获取下一个月份
     *
     * @param date
     * @return
     */
    public static Date nextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 根据传入的时间 获取每月的第一天
     *
     * @param
     * @return
     */
    public static Date getFirstDayDateOfMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }

    /**
     * 根据传入的时间 获取最近的周一
     *
     * @param curDate
     * @return
     */
    public static Date getMondayDayStr(Date curDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        // 得到今天是周几
        int a = cal.get(Calendar.DAY_OF_WEEK) - 1;
        // 周几,如果是周日 变为7
        if (a == 0) {
            a = 7;
        }
        // 当前时间减去 几天得到周一的时间
        long resDateTime = curDate.getTime() - (a * 86400000);
        // 再加上1天的时间
        resDateTime += 86400000;
        return new Date(resDateTime);
    }


    /**
     * date 转String
     *
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
            //log.info("日期格转换失败::::::::");
        }
        return null;
    }

    /**
     * 日期加减天数获取新的时间
     *
     * @param date
     * @param day
     * @return
     */
    public static Date dateAddDays(Date date, int day) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.DAY_OF_MONTH, day);
        return cl.getTime();
    }

    /**
     * 日期加减秒数获取新的时间
     *
     * @param date
     * @param second
     * @return
     */
    public static Date dateAddSeconds(Date date, int second) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.SECOND, second);
        return cl.getTime();
    }

    /**
     * 日期加减小时获取新的时间
     *
     * @param date
     * @param hour
     * @return
     */
    public static Date dateAddHours(Date date, int hour) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.HOUR, hour);
        return cl.getTime();
    }

    /**
     * 日期加减几个月获取新的时间
     *
     * @param date
     * @param month
     * @return
     */
    public static Date addMonths(final Date date, int month) {
        Calendar fromCal = Calendar.getInstance();
        fromCal.setTime(date);
        fromCal.add(Calendar.MONTH, month);
        return fromCal.getTime();
    }

    /**
     * 日期加减几年获取新的时间
     *
     * @param date
     * @param year
     * @return
     */
    public static Date addYears(final Date date, int year) {
        Calendar fromCal = Calendar.getInstance();
        fromCal.setTime(date);
        fromCal.add(Calendar.YEAR, year);
        return fromCal.getTime();
    }

    /**
     * 获得8位数字的日期，年月日 格式 yyyyMMdd
     *
     * @param date
     * @return
     * @Description 获取时间的前8位字符串
     */
    public static String getNumberDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    /**
     * 获得时间 格式 20:06:24
     *
     * @param date
     * @return
     */
    public static String getHmsTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 通过日期获取星期数
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        String week = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK);
        switch (w) {
            case 1:
                week = "星期天";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }

    /**
     * @param date 要修改的时间
     * @param day  天数
     * @param hour 小时
     * @param min  分钟
     * @param sec  秒
     * @return
     * @Description: 设置指定时间的天时分秒 正数+负数-
     * @Date: 2016年9月2日上午10:11:18
     */
    public static Date setDayTime(Date date, int day, int hour, int min, int sec) {
        Calendar cal = Calendar.getInstance();
        date = dateAddDays(date, day);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, sec);
        return cal.getTime();
    }

    /**
     * 日期按照指定格式转化为String类型
     * @param  date
     * @param format
     */
    public static String DateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 获取一个月的天数
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 统计两个日期之间的时间集合
     */
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(beginDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        lDate.add(endDate);
        return lDate;
    }


}
