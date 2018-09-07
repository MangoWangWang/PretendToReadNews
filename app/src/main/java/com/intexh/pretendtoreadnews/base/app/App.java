package com.intexh.pretendtoreadnews.base.app;

import android.app.Application;
import android.content.Context;


import com.intexh.pretendtoreadnews.BuildConfig;

import org.litepal.LitePal;

/**
 * Created by AndroidIntexh1 on 2018/8/21.
 */

public class App extends Application {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LitePal.initialize(this);
        if (!BuildConfig.DEBUG) {
            AppExceptionHandler.getInstance().setCrashHanler(this);
        }
    }
}
