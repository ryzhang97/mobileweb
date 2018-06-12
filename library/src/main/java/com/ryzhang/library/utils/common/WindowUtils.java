package com.ryzhang.library.utils.common;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

/**
 * @author ryzhang
 * @date 2017/11/16
 * @time 19:36
 * Project 窗体工具类
 */
public class WindowUtils {
    /**
     * 设置窗体透明度
     *
     * @param mContext
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(Context mContext, float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }
}
