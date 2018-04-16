package com.example.rh.newsapp;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import static com.example.rh.newsapp.utils.MyToast.initToast;

/**
 * @author RH
 * @date 2018/3/6
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initToast(context);
        Fresco.initialize(this);
    }

    public static Context getContext() {
        return context;
    }
}
