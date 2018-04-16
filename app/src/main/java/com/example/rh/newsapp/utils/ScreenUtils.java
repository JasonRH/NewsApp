package com.example.rh.newsapp.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.rh.newsapp.MyApplication;


/**
 * @author RH
 * @date 2018/3/12
 */
public class ScreenUtils {

    public static int getScreenWidth() {
        //获取屏幕宽高
        WindowManager manager = (WindowManager) MyApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        int sWidth = metrics.widthPixels;
        //int sHeight = metrics.heightPixels;
        return sWidth;
    }
}
