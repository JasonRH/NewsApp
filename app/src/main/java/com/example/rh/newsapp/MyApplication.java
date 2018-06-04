package com.example.rh.newsapp;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.litepal.LitePal;

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
        //数据库
        LitePal.initialize(this);
        initToast(context);
        Fresco.initialize(context);
    }

    public static Context getContext() {
        return context;
    }
}
