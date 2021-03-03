package com.github.tanhao1410.thesis.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author hushawen
 * @date 2020/5/20 18:24
 */
public class DateUtils {

    /**
     * 获取年
     * @param date
     * @return
     */
    public static int getYear(Date date){

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
//        System.out.println("现在时间："+simdf.format(cal.getTime()));
//分别获取年、月、日
        System.out.println("年："+cal.get(cal.YEAR));
        System.out.println("月："+(cal.get(cal.MONTH)+1));//老外把一月份整成了0，翻译成中国月份要加1
        System.out.println("日："+cal.get(cal.DATE));
        return cal.get(cal.YEAR);
    }

    /**
     * 获取月份
     * @param date
     * @return
     */
    public static int getMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(cal.MONTH)+1;
    }

    /**
     * 获取日
     * @param date
     * @return
     */
    public static int getDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(cal.DATE);
    }

    /**
     * 获取当年第几周
     * @param date
     * @return
     */
    public static int getYearWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        return cal.get(cal.WEEK_OF_YEAR);
    }

    /**
     * 获取当月第几周
     * @param date
     * @return
     */
    public static int getMonthWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(cal.WEEK_OF_MONTH);
    }

    /**
     * 时间加减天数
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date,Integer day){
        if(date==null){
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, day);// 今天+1天
        return c.getTime();
    }

    /**
     * 获取本周的周一
     * @param date
     * @return
     */
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }
    public static void main(String[] args) {
      System.out.println(getYear(new Date()));
      System.out.println(getMonth(new Date()));
      System.out.println(getDay(new Date()));
      System.out.println(getYearWeek(new Date()));
      System.out.println(getMonthWeek(new Date()));
      System.out.println(format(new Date(),"yyyy-MM-dd"));
      System.out.println(getThisWeekMonday(addDay(new Date(),-4)));

    }

    /**
     * 对时间进行格式化
     * @param date
     * @param format  默认是 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String format(Date date,String format){
        if(format==null||format.equals("")||format.equals(" ")){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        DateFormat formatF = new SimpleDateFormat(format);
        return formatF.format(date);
    }
}
