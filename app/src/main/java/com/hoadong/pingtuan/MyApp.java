package com.hoadong.pingtuan;

import android.app.Application;

import com.hoadong.diy.app.Diy;

/**
 * Created by linghaoDo on 2018/8/13
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Diy.init(this);
    }
}
