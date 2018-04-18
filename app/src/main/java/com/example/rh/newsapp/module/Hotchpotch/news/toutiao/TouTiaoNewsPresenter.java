package com.example.rh.newsapp.module.Hotchpotch.news.toutiao;

import com.example.rh.newsapp.base.BasePresenter;
import com.example.rh.newsapp.model.ToutiaoNewsBean;
import com.example.rh.newsapp.network.api.TouTiaoService;
import com.example.rh.newsapp.network.retrofit.RetrofitFactory;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author RH
 * @date 2018/4/11
 */
public class TouTiaoNewsPresenter extends BasePresenter<ITouTiaoNews.View> implements ITouTiaoNews.Presenter {
    private static final String TAG = "IDNewsPresenter";
    private CompositeDisposable compositeDisposable;

    public TouTiaoNewsPresenter(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void loadData() {
        getTouTiaoNews();
    }


    private void getTouTiaoNews() {
        RetrofitFactory.getRetrofit().create(TouTiaoService.class).getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<ToutiaoNewsBean, ObservableSource<List<ToutiaoNewsBean.DataBean>>>() {
                    @Override
                    public ObservableSource<List<ToutiaoNewsBean.DataBean>> apply(ToutiaoNewsBean toutiaoNewsBean) throws Exception {
                        return io.reactivex.Observable.fromArray(toutiaoNewsBean.getData());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ToutiaoNewsBean.DataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<ToutiaoNewsBean.DataBean> dataBeans) {
                        getview().onUpdateUI(dataBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getview().showToast(e.getMessage());
                        getview().stopLoading();
                    }

                    @Override
                    public void onComplete() {
                        getview().stopLoading();
                    }
                });
    }
}
