package com.hoadong.diy.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by linghaoDo on 2018/8/12
 */
public final class Diy {
    public static Configurator init(Context context) {
        Configurator.getInstance()
                .getDiyConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Application getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }

    public static void test() {
    }


}
