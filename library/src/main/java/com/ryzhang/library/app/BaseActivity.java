package com.ryzhang.library.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.ryzhang.library.utils.dependent.x;
import com.ryzhang.library.utils.common.ActivityManager;
import com.ryzhang.library.utils.statusbar.StatusBar;

/**
 * @author ryzhang
 * @date 2017/10/18
 * @time 17:43
 * Project
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context context;
    protected ActivityManager activityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        initVariable();
        loadData();
        initListener();
    }

    /**
     * 初始化变量
     */
    protected void initVariable() {
        context = this;
        activityManager = ActivityManager.getActivityManager();
        activityManager.pushActivity((Activity) context);
    }

    /**
     * 加载数据
     */
    protected abstract void loadData();


    /**
     * 初始化监听
     */
    protected abstract void initListener();


    /**
     * 填充自定义状态栏的textview,颜色
     *
     * @param status_bar
     * @param color
     */
    protected void setStatusBarColor(TextView status_bar, int color) {
        int getStatusBarHeight = StatusBar.getStatusBarHeight(context);
        status_bar.setHeight(getStatusBarHeight);
        status_bar.setBackgroundResource(color);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    protected void setStatusBarColor(int color) {
        StatusBar.setStatusBarColor(this, getResources().getColor(color));
    }

    /**
     * 自定义状态栏
     *
     * @param status_bar
     * @param color
     */
    protected void customStatus(TextView status_bar, int color) {
        StatusBar.translucentStatusBar(this);//状态栏透明
        setStatusBarColor(status_bar, color);//设置自定义状态栏
    }

    /**
     * 改变主题
     */
    protected void changeTheme() {
//        MODE_NIGHT_NO： 使用亮色(light)主题，不使用夜间模式；
//        MODE_NIGHT_YES：使用暗色(dark)主题，使用夜间模式；
//        MODE_NIGHT_AUTO：根据当前时间自动切换 亮色(light)/暗色(dark)主题；
//        MODE_NIGHT_FOLLOW_SYSTEM(默认选项)：设置为跟随系统，通常为 MODE_NIGHT_NO
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        AppCompatDelegate appCompatDelegate = getDelegate();
        int module = currentNightMode == Configuration.UI_MODE_NIGHT_NO ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        appCompatDelegate.setLocalNightMode(module);
        // 需要调用recreate方法使之生效
        recreate();
    }

    /**
     * 点击空白位置 隐藏软键盘
     */
    public void hideInput() {
        if (null != this.getCurrentFocus()) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
