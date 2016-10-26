package com.morse.retrofithttpsdemo;

import android.app.Application;

/**
 * author：Morse
 * time：2016/10/26 14:13
 * Descripte：
 */
public class BaseApplication extends Application {

    private BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public BaseApplication getInstance() {
        return mInstance;
    }
}
