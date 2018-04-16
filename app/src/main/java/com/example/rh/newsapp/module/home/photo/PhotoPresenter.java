package com.example.rh.newsapp.module.home.photo;

import android.accounts.NetworkErrorException;
import android.util.Log;

import com.example.rh.newsapp.MyApplication;
import com.example.rh.newsapp.base.BasePresenter;
import com.example.rh.newsapp.model.PhotoArticleBean;
import com.example.rh.newsapp.network.retrofit.RetrofitFactory;
import com.example.rh.newsapp.network.api.NeiHanService;
import com.example.rh.newsapp.utils.NetWorkUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


/**
 * @author RH
 * @date 2018/3/5
 */
public class PhotoPresenter extends BasePresenter<IPhotoArticle.View> implements IPhotoArticle.Presenter {
    private static final String TAG = "PhotoPresenter";
    private CompositeDisposable compositeDisposable;
    private long startTime;
    private long preTime = 1483929653;

    public PhotoPresenter(CompositeDisposable disposables) {
        compositeDisposable = disposables;
    }

    @Override
    public void loadData() {
        getPhotoByObserve();
        preTime = startTime/1000;
    }


    /**
     * Retrofit + RxJava（观察者模式）
     */
    private void getPhotoByObserve() {
        startTime = System.currentTimeMillis();
        Log.e(TAG, "startTime: "+startTime+"\npreTime:"+preTime);
        Observable<PhotoArticleBean> observable = RetrofitFactory.getRetrofit().create(NeiHanService.class)
                 .getPhoto1();
                /*.getPhoto(1, 1, 1, "-103", "-1", "", "", "116.4121485",
                        "39.937848", "%E5%8C%97%E4%BA%AC%E5%B8%82", startTime + "", 20, preTime+"", 1080,
                        "7164180604", "34822199408", "wifi", "baidu", 7, "joke_essay", "590",
                        "5.9.0", "android", "a", "Nexus%2B5", "google", 25, "7.1",
                        "359250050588035", "12645e537a2f0f25", "590", "1080*1776", 480, "5903");*/
        // Schedulers.io()（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。此外还有Schedulers.computation()(计算所使用的 Scheduler)
        observable.subscribeOn(Schedulers.io())
                /*  if判断  */
                //主线程，进行UI操作
                .observeOn(AndroidSchedulers.mainThread())
                .filter(photoArticleBean -> {
                    if (photoArticleBean.getData().isHas_more()) {
                        if (NetWorkUtil.isNetworkConnected(MyApplication.getContext())) {
                            getview().showToast("照片：" + photoArticleBean.getData().getTip());
                        } else {
                            getview().showToast("请检查当前网路！");
                        }
                        return true;
                    } else {
                        getview().showToast("暂无最新数据");
                        return false;
                    }
                })
                .observeOn(Schedulers.io())
                /*  数据转换 ,可将一个数组转换成多个对象 */
                .flatMap(photoArticleBean -> Observable.fromArray(photoArticleBean.getData().getDataList()))
                .observeOn(AndroidSchedulers.mainThread())
                /*  观察者方法实现 */
                .subscribe(new Observer<List<PhotoArticleBean.Data>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //将Disposable添加进CompositeDisposable容器
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<PhotoArticleBean.Data> data) {
                        //多个对象时会多次调用
                        getview().onUpdateUI(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException || e instanceof TimeoutException) {
                            getview().showToast("请求超时，稍后再试");
                        } else if (e instanceof ConnectException || e instanceof NetworkErrorException || e instanceof UnknownHostException) {
                            getview().showToast("网络异常，稍后再试");
                        } else if (e instanceof HttpException) {
                            getview().showToast("服务器异常，稍后再试");
                        } else if (e instanceof NullPointerException) {
                            //特殊处理
                            e.printStackTrace();
                        } else {
                            getview().showToast("网络数据获取失败\n" + e);
                        }

                        getview().stopLoading();
                    }

                    @Override
                    public void onComplete() {
                        getview().stopLoading();
                    }
                });
    }

}
