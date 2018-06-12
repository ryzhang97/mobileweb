package com.ryzhang.library.utils.dependent;

import android.app.Application;
import android.content.Context;


import com.ryzhang.library.utils.dependent.view.ViewInjector;
import com.ryzhang.library.utils.dependent.view.ViewInjectorImpl;

import java.lang.reflect.Method;

/**
 * @author ryzhang
 * @date 2017/11/6
 * @time 18:07
 * Project
 */

public final class x {
    private x() {
    }

    public static boolean isDebug() {
        return x.Ext.debug;
    }

    public static Application app() {
        if (x.Ext.app == null) {
            try {
                Class<?> renderActionClass = Class.forName("com.android.layoutlib.bridge.impl.RenderAction");
                Method method = renderActionClass.getDeclaredMethod("getCurrentContext", new Class[0]);
                Context context = (Context) method.invoke((Object) null, new Object[0]);
                x.Ext.app = new x.MockApplication(context);
            } catch (Throwable var3) {
                throw new RuntimeException("please invoke x.Ext.init(app) on Application#onCreate() and register your Application in manifest.");
            }
        }

        return x.Ext.app;
    }
//
//    public static TaskController task() {
//        return x.Ext.taskController;
//    }
//
//    public static HttpManager http() {
//        if(x.Ext.httpManager == null) {
//            HttpManagerImpl.registerInstance();
//        }
//
//        return x.Ext.httpManager;
//    }
//
//    public static ImageManager image() {
//        if(x.Ext.imageManager == null) {
//            ImageManagerImpl.registerInstance();
//        }
//
//        return x.Ext.imageManager;
//    }

    public static ViewInjector view() {
        if (x.Ext.viewInjector == null) {
            ViewInjectorImpl.registerInstance();
        }
        return x.Ext.viewInjector;
    }
//
//    public static DbManager getDb(DaoConfig daoConfig) {
//        return DbManagerImpl.getInstance(daoConfig);
//    }

    private static class MockApplication extends Application {
        public MockApplication(Context baseContext) {
            this.attachBaseContext(baseContext);
        }
    }

    public static class Ext {
        private static boolean debug;
        private static Application app;
        //        private static TaskController taskController;
//        private static HttpManager httpManager;
//        private static ImageManager imageManager;
        private static ViewInjector viewInjector;

        private Ext() {
        }

        public static void init(Application app) {
            if (app == null) {
                app = app;
            }

        }

        public static void setDebug(boolean debug) {
            debug = debug;
        }

        //        public static void setTaskController(TaskController taskController) {
//            if(taskController == null) {
//                taskController = taskController;
//            }
//
//        }
//
//        public static void setHttpManager(HttpManager httpManager) {
//            httpManager = httpManager;
//        }
//
//        public static void setImageManager(ImageManager imageManager) {
//            imageManager = imageManager;
//        }
//
        public static void setViewInjector(ViewInjector viewInjector) {
            Ext.viewInjector = viewInjector;
        }

        static {
//            TaskControllerImpl.registerInstance();
//            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            });
        }
    }
}