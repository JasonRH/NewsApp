package com.example.rh.newsapp.module.Hotchpotch.joke;

import android.accounts.NetworkErrorException;

import com.example.rh.newsapp.MyApplication;
import com.example.rh.newsapp.base.BasePresenter;
import com.example.rh.newsapp.model.JokeBean;
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
 * @date 2018/3/31
 */
public class JokePresenter extends BasePresenter<IJokeArticle.View> implements IJokeArticle.Presenter {
    private CompositeDisposable compositeDisposable;

    public JokePresenter(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void loadData() {
        getJokeByObserve();
    }

    /**
     * Retrofit + RxJava（观察者模式）
     */
    private void getJokeByObserve() {
        Observable<JokeBean> observable = RetrofitFactory.getRetrofit().create(NeiHanService.class).getJoke();
        // Schedulers.io()（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。此外还有Schedulers.computation()(计算所使用的 Scheduler)
        observable.subscribeOn(Schedulers.io())
                /*  if判断  */
                //主线程，进行UI操作
                .observeOn(AndroidSchedulers.mainThread())
                .filter(jokeBean -> {
                    if (jokeBean.getData().isHas_more()) {
                        if (NetWorkUtil.isNetworkConnected(MyApplication.getContext())) {
                            getview().showToast("段子："+jokeBean.getData().getTip());
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
                .flatMap(jokeBean -> Observable.fromArray(jokeBean.getData().getData()))
                .observeOn(AndroidSchedulers.mainThread())
                /*  观察者方法实现 */
                .subscribe(new Observer<List<JokeBean.JokeDataDataEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //将Disposable添加进CompositeDisposable容器
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<JokeBean.JokeDataDataEntity> jokeDataDataEntities) {
                        getview().onUpdateUI(jokeDataDataEntities);
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
                            //_onError("请求失败，稍后再试");
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
