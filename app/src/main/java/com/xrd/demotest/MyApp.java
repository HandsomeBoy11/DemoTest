package com.xrd.demotest;

import android.app.Application;

/**
 * Created by WJ on 2018/12/3.
 */

public class MyApp extends Application {
    public static MyApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
       instance=this;
    }
    public static MyApp getInstace(){
        return instance;
    }
}
