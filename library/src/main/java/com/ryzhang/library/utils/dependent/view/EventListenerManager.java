package com.ryzhang.library.utils.dependent.view;

import android.text.TextUtils;
import android.view.View;

import com.ryzhang.library.utils.Logcat;
import com.ryzhang.library.utils.dependent.DoubleKeyValueMap;
import com.ryzhang.library.utils.dependent.view.annotation.Event;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * @author ryzhang
 * @date 2017/11/6
 * @time 18:13
 * Project
 */
final class EventListenerManager {
    private static final long QUICK_EVENT_TIME_SPAN = 300L;
    private static final HashSet<String> AVOID_QUICK_EVENT_SET = new HashSet(2);
    private static final DoubleKeyValueMap<ViewInfo, Class<?>, Object> listenerCache;

    private EventListenerManager() {
    }

    public static void addEventMethod(ViewFinder finder, ViewInfo info, Event event, Object handler, Method method) {
        try {
            View view = finder.findViewByInfo(info);
            if (view != null) {
                Class<?> listenerType = event.type();
                String listenerSetter = event.setter();
                if (TextUtils.isEmpty(listenerSetter)) {
                    listenerSetter = "set" + listenerType.getSimpleName();
                }

                String methodName = event.method();
                boolean addNewMethod = false;
                Object listener = listenerCache.get(info, listenerType);
                EventListenerManager.DynamicHandler dynamicHandler = null;
                if (listener != null) {
                    dynamicHandler = (EventListenerManager.DynamicHandler) Proxy.getInvocationHandler(listener);
                    addNewMethod = handler.equals(dynamicHandler.getHandler());
                    if (addNewMethod) {
                        dynamicHandler.addMethod(methodName, method);
                    }
                }

                if (!addNewMethod) {
                    dynamicHandler = new EventListenerManager.DynamicHandler(handler);
                    dynamicHandler.addMethod(methodName, method);
                    listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, dynamicHandler);
                    listenerCache.put(info, listenerType, listener);
                }

                Method setEventListenerMethod = view.getClass().getMethod(listenerSetter, new Class[]{listenerType});
                setEventListenerMethod.invoke(view, new Object[]{listener});
            }
        } catch (Throwable var13) {
            Logcat.e(Logcat.TAG, var13.getMessage(), var13);
        }

    }

    static {
        AVOID_QUICK_EVENT_SET.add("onClick");
        AVOID_QUICK_EVENT_SET.add("onItemClick");
        listenerCache = new DoubleKeyValueMap();
    }

    public static class DynamicHandler implements InvocationHandler {
        private WeakReference<Object> handlerRef;
        private final HashMap<String, Method> methodMap = new HashMap(1);
        private static long lastClickTime = 0L;

        public DynamicHandler(Object handler) {
            this.handlerRef = new WeakReference(handler);
        }

        public void addMethod(String name, Method method) {
            this.methodMap.put(name, method);
        }

        public Object getHandler() {
            return this.handlerRef.get();
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object handler = this.handlerRef.get();
            if (handler != null) {
                String eventMethod = method.getName();
                if ("toString".equals(eventMethod)) {
                    return EventListenerManager.DynamicHandler.class.getSimpleName();
                }

                method = (Method) this.methodMap.get(eventMethod);
                if (method == null && this.methodMap.size() == 1) {
                    Iterator var6 = this.methodMap.entrySet().iterator();
                    if (var6.hasNext()) {
                        Map.Entry<String, Method> entry = (Map.Entry) var6.next();
                        if (TextUtils.isEmpty((CharSequence) entry.getKey())) {
                            method = (Method) entry.getValue();
                        }
                    }
                }

                if (method != null) {
                    if (EventListenerManager.AVOID_QUICK_EVENT_SET.contains(eventMethod)) {
                        long timeSpan = System.currentTimeMillis() - lastClickTime;
                        if (timeSpan < 300L) {
                            Logcat.d("onClick cancelled: " + timeSpan);
                            return null;
                        }

                        lastClickTime = System.currentTimeMillis();
                    }

                    try {
                        return method.invoke(handler, args);
                    } catch (Throwable var8) {
                        throw new RuntimeException("invoke method error:" + handler.getClass().getName() + "#" + method.getName(), var8);
                    }
                }

//                LogUtil.w("method not impl: " + eventMethod + "(" + handler.getClass().getSimpleName() + ")");
            }

            return null;
        }
    }
}
