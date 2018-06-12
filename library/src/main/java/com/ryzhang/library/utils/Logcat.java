package com.ryzhang.library.utils;

import android.util.Log;

import com.ryzhang.library.BuildConfig;


/**
 * @author ryzhang
 * @date 2017/9/27
 * @time 14:35
 * Project 打印log信息
 * 1、如果 isOpen 为true 可以打印log 否则 不打印
 * 2、如果 content 为null 或空 不打印log 否则 不打印
 * 否则输出部分log tag 需要传递
 */
public class Logcat {
    public static final String TAG = "ryzhang";
    private static boolean isOpen = BuildConfig.LOG;//是否打开log

    /**
     * 打印默认 tag log
     *
     * @param content
     */
    public static void d(String content) {
        d(content, TAG);
    }

    /**
     * 打印指定tag log
     *
     * @param content
     * @param clazz
     */
    public static void d(Class clazz, String content) {
        d(content, clazz.getSimpleName());
    }

    /**
     * 打印指定tag log
     *
     * @param content
     * @param tag
     */
    public static void d(String content, String tag) {
        if (check(content)) {
            Log.d(content, tag);
        }
    }

    public static void i(String content) {
        i(content, TAG);
    }

    public static void i(Class clazz, String content) {
        i(content, clazz.getSimpleName());
    }

    public static void i(String content, String tag) {
        if (check(content)) {
            Log.i(content, tag);
        }
    }

    public static void w(String content) {
        w(content, TAG);
    }

    public static void w(Class clazz, String content) {
        w(content, clazz.getSimpleName());
    }

    public static void w(String content, String tag) {
        if (check(content)) {
            Log.w(content, tag);
        }
    }

    public static void e(String content) {
        e(content, TAG, new Throwable(""));
    }

    public static void e(String content, String tag) {
        e(content, tag, new Throwable(""));
    }

    public static void e(Class clazz, String content) {
        e(content, clazz.getSimpleName(), new Throwable(""));
    }

    public static void e(String tag, String content, Throwable tr) {
        if (check(content)) {
            Log.e(tag, content, tr);
        }
    }

    /**
     * 校验是否可以输出log
     *
     * @param content
     * @return
     */
    public static boolean check(String content) {
        if (!isOpen) {
            return false;
        }
        if (content == null || content.equals("")) {
            return false;
        }
        return true;
    }
}
