package com.ryzhang.library.utils.dependent.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author ryzhang
 * @date 2017/11/6
 * @time 18:06
 * Project
 */
public interface ViewInjector {
    void inject(View var1);

    void inject(Activity var1);

    void inject(Object var1, View var2);

    View inject(Object var1, LayoutInflater var2, ViewGroup var3);
}
