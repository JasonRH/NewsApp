package com.example.rh.newsapp.module.Hotchpotch.bing;

import android.accounts.NetworkErrorException;

import com.example.rh.newsapp.base.BasePresenter;
import com.example.rh.newsapp.model.BingBean;
import com.example.rh.newsapp.model.BingDailyBean;
import com.example.rh.newsapp.network.retrofit.RetrofitFactory;
import com.example.rh.newsapp.network.api.BingPictureService;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * @author RH
 * @date 2018/4/9
 */
public class BingPicturePresenter extends BasePresenter<IBing.View> implements IBing.Presenter {
    private CompositeDisposable compositeDisposable;

    public BingPicturePresenter(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void loadData() {
        getBingPicture();
    }

    private void getBingPicture() {
        RetrofitFactory.getRetrofit().create(BingPictureService.class).getPicture()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<BingBean, ObservableSource<List<BingDailyBean>>>() {
                    @Override
                    public ObservableSource<List<BingDailyBean>> apply(BingBean bingBean) throws Exception {
                        return  io.reactivex.Observable.fromArray(bingBean.getBingDailyBeans());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BingDailyBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<BingDailyBean> bingDailyBeans) {
                        getview().onUpdateUI(bingDailyBeans);
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
