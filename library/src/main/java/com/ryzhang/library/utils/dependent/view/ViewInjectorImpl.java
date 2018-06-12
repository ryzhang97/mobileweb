package com.ryzhang.library.utils.dependent.view;

import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ryzhang.library.utils.Logcat;
import com.ryzhang.library.utils.dependent.view.annotation.ContentView;
import com.ryzhang.library.utils.dependent.view.annotation.Event;
import com.ryzhang.library.utils.dependent.view.annotation.ViewInject;
import com.ryzhang.library.utils.dependent.x;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;

/**
 * @author ryzhang
 * @date 2017/11/6
 * @time 18:09
 * Project
 */
public final class ViewInjectorImpl implements ViewInjector {
    private static final HashSet<Class<?>> IGNORED = new HashSet();
    private static final Object lock;
    private static volatile ViewInjectorImpl instance;

    private ViewInjectorImpl() {
    }

    public static void registerInstance() {
        if (instance == null) {
            Object var0 = lock;
            synchronized (lock) {
                if (instance == null) {
                    instance = new ViewInjectorImpl();
                }
            }
        }

        x.Ext.setViewInjector(instance);
    }

    public void inject(View view) {
        injectObject(view, view.getClass(), new ViewFinder(view));
    }

    public void inject(Activity activity) {
        Class handlerType = activity.getClass();

        try {
            ContentView contentView = findContentView(handlerType);
            if (contentView != null) {
                int viewId = contentView.value();
                if (viewId > 0) {
                    Method setContentViewMethod = handlerType.getMethod("setContentView", new Class[]{Integer.TYPE});
                    setContentViewMethod.invoke(activity, new Object[]{Integer.valueOf(viewId)});
                }
            }
        } catch (Throwable var6) {
            Logcat.e(Logcat.TAG, var6.getMessage(), var6);
        }

        injectObject(activity, handlerType, new ViewFinder(activity));
    }

    public void inject(Object handler, View view) {
        injectObject(handler, handler.getClass(), new ViewFinder(view));
    }

    public View inject(Object fragment, LayoutInflater inflater, ViewGroup container) {
        View view = null;
        Class handlerType = fragment.getClass();

        try {
            ContentView contentView = findContentView(handlerType);
            if (contentView != null) {
                int viewId = contentView.value();
                if (viewId > 0) {
                    view = inflater.inflate(viewId, container, false);
                }
            }
        } catch (Throwable var8) {
            Logcat.e(Logcat.TAG, var8.getMessage(), var8);
        }

        injectObject(fragment, handlerType, new ViewFinder(view));
        return view;
    }

    private static ContentView findContentView(Class<?> thisCls) {
        if (thisCls != null && !IGNORED.contains(thisCls)) {
            ContentView contentView = (ContentView) thisCls.getAnnotation(ContentView.class);
            return contentView == null ? findContentView(thisCls.getSuperclass()) : contentView;
        } else {
            return null;
        }
    }

    private static void injectObject(Object handler, Class<?> handlerType, ViewFinder finder) {
        if (handlerType != null && !IGNORED.contains(handlerType)) {
            injectObject(handler, handlerType.getSuperclass(), finder);
            Field[] fields = handlerType.getDeclaredFields();
            int var6;
            if (fields != null && fields.length > 0) {
                Field[] var4 = fields;
                int var5 = fields.length;

                for (var6 = 0; var6 < var5; ++var6) {
                    Field field = var4[var6];
                    Class<?> fieldType = field.getType();
                    if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers()) && !fieldType.isPrimitive() && !fieldType.isArray()) {
                        ViewInject viewInject = (ViewInject) field.getAnnotation(ViewInject.class);
                        if (viewInject != null) {
                            try {
                                View view = finder.findViewById(viewInject.value(), viewInject.parentId());
                                if (view == null) {
                                    throw new RuntimeException("Invalid @ViewInject for " + handlerType.getSimpleName() + "." + field.getName());
                                }

                                field.setAccessible(true);
                                field.set(handler, view);
                            } catch (Throwable var16) {
                                Logcat.e(Logcat.TAG, var16.getMessage(), var16);
                            }
                        }
                    }
                }
            }

            Method[] methods = handlerType.getDeclaredMethods();
            if (methods != null && methods.length > 0) {
                Method[] var19 = methods;
                var6 = methods.length;

                for (int var20 = 0; var20 < var6; ++var20) {
                    Method method = var19[var20];
                    if (!Modifier.isStatic(method.getModifiers()) && Modifier.isPrivate(method.getModifiers())) {
                        Event event = (Event) method.getAnnotation(Event.class);
                        if (event != null) {
                            try {
                                int[] values = event.value();
                                int[] parentIds = event.parentId();
                                int parentIdsLen = parentIds == null ? 0 : parentIds.length;

                                for (int i = 0; i < values.length; ++i) {
                                    int value = values[i];
                                    if (value > 0) {
                                        ViewInfo info = new ViewInfo();
                                        info.value = value;
                                        info.parentId = parentIdsLen > i ? parentIds[i] : 0;
                                        method.setAccessible(true);
                                        EventListenerManager.addEventMethod(finder, info, event, handler, method);
                                    }
                                }
                            } catch (Throwable var17) {
                                Logcat.e(Logcat.TAG, var17.getMessage(), var17);
                            }
                        }
                    }
                }
            }

        }
    }

    static {
        IGNORED.add(Object.class);
        IGNORED.add(Activity.class);
        IGNORED.add(Fragment.class);

        try {
            IGNORED.add(Class.forName("android.support.v4.app.Fragment"));
            IGNORED.add(Class.forName("android.support.v4.app.FragmentActivity"));
        } catch (Throwable var1) {
            ;
        }

        lock = new Object();
    }
}
