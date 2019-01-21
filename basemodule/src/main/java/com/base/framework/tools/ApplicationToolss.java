package com.base.framework.tools;

import android.app.Application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 41113 on 2018/6/28.
 */

public class ApplicationToolss {

    private ApplicationToolss() {
    }

    private static class Bulid {
        private static final ApplicationToolss single = new ApplicationToolss();
    }

    public static ApplicationToolss getInstance() {
        return Bulid.single;
    }


    public Application application = null;


    public Application getApplication() {
        if (application == null) {
            getNowApplication();

        }
        return application;
    }

    public void getNowApplication() {
        Class<?> activityThreadClass;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            final Method method2 = activityThreadClass.getMethod(
                    "currentActivityThread", new Class[0]);
            // 得到当前的ActivityThread对象
            Object localObject = method2.invoke(null, (Object[]) null);

            final Method method = activityThreadClass
                    .getMethod("getApplication");
            application = (Application) method.invoke(localObject, (Object[]) null);

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
