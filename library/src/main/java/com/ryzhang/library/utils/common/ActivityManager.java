package com.ryzhang.library.utils.common;

import android.app.Activity;

import java.util.Stack;

/**
 * @author ryzhang
 * @date 2017/9/19
 * @time 16:05
 * Activity管理类
 */
public class ActivityManager {
    private static Stack<Activity> activityStack;
    private static ActivityManager instance;
    private Activity currActivity;

    private ActivityManager() {
    }

    public static ActivityManager getActivityManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }


    /**
     * 退出栈顶Activity
     */
    public void popActivity(Activity activity) {
        if (activity == null || activityStack == null) {
            return;
        }
        if (activityStack.contains(activity)) {
            activityStack.remove(activity);
        }
        currActivity = activity;
    }

    /**
     * 销毁Activity
     *
     * @param activity
     */
    public void destoryActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        activity.finish();
        if (activityStack.contains(activity)) {
            activityStack.remove(activity);
        }
        activity = null;
    }

    /**
     * 获得当前栈顶Activity
     */
    public Activity currentActivity() {
        if (activityStack == null || activityStack.empty()) {
            return null;
        }
        return activityStack.lastElement();
    }

    /**
     * 将当前Activity推入栈中
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 退出栈中除指定的Activity外的所有Activity
     */
    public void popAllActivityExceptOne(Class cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            destoryActivity(activity);
        }
    }

    /**
     * 退出栈中所有Activity
     */
    public void popAllActivity() {
        popAllActivityExceptOne(null);
    }

    /**
     * 获取当前Activity
     *
     * @return
     */
    public Activity getCurrentActivity() {
        return currActivity;
    }

    /**
     * 获取栈内Activity数量
     *
     * @return
     */
    public int getActivityStackSize() {
        int size = 0;
        if (activityStack != null) {
            size = activityStack.size();
        }
        return size;
    }
}
