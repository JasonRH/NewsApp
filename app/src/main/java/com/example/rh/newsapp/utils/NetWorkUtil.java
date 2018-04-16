package com.example.rh.newsapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


/**
 * @author RH
 */
public class NetWorkUtil {

    /**
     * 判断是否有网络连接
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = manager != null ? manager.getActiveNetworkInfo() : null;
            //判断NetworkInfo对象是否为空
            return null != networkInfo && networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 判断WIFI网络是否可用
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = manager != null ? manager.getActiveNetworkInfo() : null;
            //判断NetworkInfo对象是否为空 并且类型是否为WIFI
            if (null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            //获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取NetworkInfo对象
            NetworkInfo networkInfo = manager != null ? manager.getActiveNetworkInfo() : null;
            //判断NetworkInfo对象是否为空 并且类型是否为MOBILE
            if (null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 测试当前网络连接后是否能上网
     * 注意：该方法不能直接在UI线程中执行
     */
    public static synchronized boolean isConnect() {
        boolean isConnect = false;
        int count = 0;
        while (count < 3) {
            try {
                URL url = new URL("https://www.baidu.com");
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                int state = connection.getResponseCode();
                System.out.println(count + "=" + state);
                if (connection.getResponseCode() == 200) {
                    System.out.println("网络可用");
                    isConnect = true;
                    break;
                }
            } catch (Exception e) {
                System.out.println("网络不可用，连接第" + count + "次 ：" + e);
                count++;
            }
        }
        return isConnect;
    }

}
