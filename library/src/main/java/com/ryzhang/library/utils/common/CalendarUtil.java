package com.ryzhang.library.utils.common;

import com.ryzhang.library.utils.Logcat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期相关工具类
 *
 * @author ryzhang
 * @date:2017/8/28 10:48
 */

public class CalendarUtil {
    //求最近时间
    public static final int DAY = Calendar.DAY_OF_YEAR;//日
    public static final int WEEK = Calendar.WEEK_OF_YEAR;//星期
    public static final int MONTH = Calendar.MONTH;//月
    public static final int YEAR = Calendar.YEAR;//年

    //格式化日期 一般时间格式
    public static final String YEAR_TO_SECOND = "yyyy-MM-dd HH:mm:ss";//年月日时分秒
    public static final String YEAR_TO_MINUTE = "yyyy-MM-dd HH:mm";//年月日时分
    public static final String YEAR_TO_DAY = "yyyy-MM-dd";//年月日
    public static final String HOUR_SECOND = "HH:mm:ss";//时分秒
    public static final String HOUR_MINUTE = "HH:mm";//时分
    //中文时间格式
    public static final String YEAR_TO_SECOND_CN = "yyyy年MM月dd日HH时mm分ss秒";
    public static final String YEAR_TO_MINUTE_CN = "yyyy年MM月dd日HH时mm分";
    public static final String YEAR_TO_DAY_CN = "yyyy年MM月dd日";
    public static final String HOUR_SECOND_CN = "HH时mm分ss秒";
    public static final String HOUR_MINUTE_CN = "HH时mm分";

    /**
     * 获取指定格式的日期
     *
     * @param type 日期类型
     * @param date 日期
     * @return
     */
    public static String formatDate(Date date, String type) {
        return new SimpleDateFormat(type).format(date);
    }

    /**
     * 将字符串转换成日期类型
     *
     * @param dateTime 1、不能传精确度高于字符串的类型  2、必须包含有年月日
     * @return
     */
    public static Date parseDate(String dateTime, String type) {
        try {
            return new SimpleDateFormat(type).parse(dateTime);
        } catch (ParseException e) {
            Logcat.e(Logcat.TAG, "转换为日期格式失败");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 获取最近时间
     *
     * @param type  类型
     * @param value 值
     * @return
     */
    public static Date getLatelyDate(Date date, int type, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, value);
        date = calendar.getTime();
        return date;
    }
}
