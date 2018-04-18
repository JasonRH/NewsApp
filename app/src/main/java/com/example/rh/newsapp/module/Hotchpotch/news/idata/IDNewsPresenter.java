package com.example.rh.newsapp.module.Hotchpotch.news.idata;

import com.example.rh.newsapp.base.BasePresenter;
import com.example.rh.newsapp.model.News360Bean;
import com.example.rh.newsapp.network.api.NewsService;
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
public class IDNewsPresenter extends BasePresenter<IDNews.View> implements IDNews.Presenter {
    private static final String TAG = "IDNewsPresenter";
    private CompositeDisposable compositeDisposable;

    public IDNewsPresenter(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void loadData() {
        getNews();
    }


    private void getNews() {
        RetrofitFactory.getRetrofit().create(NewsService.class).getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<News360Bean, ObservableSource<List<News360Bean.DataBean>>>() {
                    @Override
                    public ObservableSource<List<News360Bean.DataBean>> apply(News360Bean news360Bean) throws Exception {
                        return io.reactivex.Observable.fromArray(news360Bean.getData());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<News360Bean.DataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<News360Bean.DataBean> dataBeans) {
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
