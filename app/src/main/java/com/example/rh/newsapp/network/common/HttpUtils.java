package com.example.rh.newsapp.network.common;

import android.support.test.espresso.core.internal.deps.guava.util.concurrent.ThreadFactoryBuilder;

import com.example.rh.newsapp.listener.HttpCallbackListener;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author RH
 * @date 2017/11/9
 */

public class HttpUtils {

    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        singleThreadPool.execute(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(address);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                connection.setDoInput(true);
                //connection.setDoOutput(true);//get请求并不需要设置，设置将导致请求以post方式提交,部分接口可能无法使用，即使设置了connection.setRequestMethod("GET")也无用（如每日一文接口）;
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                if (listener != null) {
                    //回调onFinish()方法
                    listener.onFinish(response.toString());
                }

            } catch (Exception e) {
                //回调onError()fangfa
                if (listener != null) {
                    listener.onError(e);
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
        singleThreadPool.shutdown();

    }






}
