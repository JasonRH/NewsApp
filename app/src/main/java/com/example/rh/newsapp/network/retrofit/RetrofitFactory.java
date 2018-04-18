package com.example.rh.newsapp.network.retrofit;

import android.util.Log;

import com.example.rh.newsapp.MyApplication;
import com.example.rh.newsapp.network.api.NeiHanService;
import com.example.rh.newsapp.utils.NetWorkUtil;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author RH
 * @date 2018/3/15
 */
public class RetrofitFactory {

    private static final String TAG = "RetrofitFactory";
    private volatile static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitFactory.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(NeiHanService.BASEURL)
                            .client(myokhttpclient())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }


    private static OkHttpClient myokhttpclient() {
        //设置缓存路径和缓存大小 50M
        Cache cache = new Cache(new File(MyApplication.getContext().getCacheDir(), "okhttpCache"), 50 * 1024 * 1024);
        Log.e(TAG, "MyOkHttpClient:缓存目录 "+MyApplication.getContext().getCacheDir());
        // Cookie 持久化
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApplication.getContext()));

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .cache(cache)
                .connectTimeout(5 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(false)
                // 添加拦截器,可添加多个
                .addInterceptor(httpLoggingInterceptor())
                .addInterceptor(sQueryParameterInterceptor)
                .addInterceptor(cacheControlInterceptor);
                // .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)

        return client.build();

    }

    /*** 自定义日志拦截器*/
    private static  Interceptor MY_LOGGING_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
            okhttp3.MediaType mediaType = response.body().contentType();
            Log.e(TAG, "intercept:返回的类型为： " + mediaType);
            String content = response.body().string();
            Log.e(TAG, "-----LoggingInterceptor----- :\nrequest url:" + request.url() + "\ntime:" + (t2 - t1) / 1e6d + "\nbody:" + content + "\n");
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    };

    /*** HttpLoggingInterceptor日志拦截器*/
    private static  HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (true) {
            //显示日志
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return httpLoggingInterceptor;
    }

    /**
     * 公共参数
     */
    public static final Interceptor sQueryParameterInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request request;
            HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                    .addQueryParameter("uid", "864678036850608")
                    .addQueryParameter("devid", "864678036850608")
                    .addQueryParameter("proid", "ifengnews")
                    .addQueryParameter("vt", "5")
                    .addQueryParameter("publishid", "6103")
                    .addQueryParameter("screen", "1080x1920")
                    .addQueryParameter("df", "androidphone")
                    .addQueryParameter("os", "android_27")
                    .addQueryParameter("nw", "wifi")
                    .build();
            request = originalRequest.newBuilder().url(modifiedUrl).build();
            return chain.proceed(request);
        }
    };

    /**
     * 缓存机制
     * 在响应请求之后在 data/data/<包名>/cache 下建立一个response 文件夹，保持缓存数据。
     * 这样我们就可以在请求的时候，如果判断到没有网络，自动读取缓存的数据。
     * 同样这也可以实现，在我们没有网络的情况下，重新打开App可以浏览的之前显示过的内容。
     * 也就是：判断网络，有网络，则从网络获取，并保存到缓存中，无网络，则从缓存中获取。
     * https://werb.github.io/2016/07/29/%E4%BD%BF%E7%94%A8Retrofit2+OkHttp3%E5%AE%9E%E7%8E%B0%E7%BC%93%E5%AD%98%E5%A4%84%E7%90%86/
     */
    private static  Interceptor cacheControlInterceptor = new Interceptor() {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtil.isNetworkConnected(MyApplication.getContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }

            okhttp3.Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetworkConnected(MyApplication.getContext())) {
                // 有网络时 设置缓存为默认值
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Pragma")
                        .build();
            } else {
                // 无网络时 设置超时为1周
                int maxStale = 60 * 60 * 24 * 7;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

}
