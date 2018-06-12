package com.ryzhang.library.utils.dependent.view;

import android.app.Activity;
import android.view.View;

/**
 * @author ryzhang
 * @date 2017/11/6
 * @time 18:12
 * Project
 */

final class ViewFinder {
    private View view;
    private Activity activity;

    public ViewFinder(View view) {
        this.view = view;
    }

    public ViewFinder(Activity activity) {
        this.activity = activity;
    }

    public View findViewById(int id) {
        return this.view != null ? this.view.findViewById(id) : (this.activity != null ? this.activity.findViewById(id) : null);
    }

    public View findViewByInfo(ViewInfo info) {
        return this.findViewById(info.value, info.parentId);
    }

    public View findViewById(int id, int pid) {
        View pView = null;
        if (pid > 0) {
            pView = this.findViewById(pid);
        }

        View view = null;
        if (pView != null) {
            view = pView.findViewById(id);
        } else {
            view = this.findViewById(id);
        }

        return view;
    }
}