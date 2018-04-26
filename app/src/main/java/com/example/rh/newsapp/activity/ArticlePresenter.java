package com.example.rh.newsapp.activity;

import com.example.rh.newsapp.base.BasePresenter;
import com.example.rh.newsapp.model.NewsArticleBean;
import com.example.rh.newsapp.network.api.IFApi;
import com.example.rh.newsapp.network.api.IFService;
import com.example.rh.newsapp.network.api.NeiHanService;
import com.example.rh.newsapp.network.retrofit.RetrofitFactory;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author RH
 * @date 2018/4/21
 */
public class ArticlePresenter extends BasePresenter<IArticle.View> implements IArticle.Presenter {
    private CompositeDisposable disposable;

    public ArticlePresenter(CompositeDisposable disposable) {
        this.disposable = disposable;
    }

    @Override
    public void getData(String url) {
        getNewsArticle(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsArticleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(NewsArticleBean newsArticleBean) {
                            getview().loadData(newsArticleBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getview().loadData(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public Observable<NewsArticleBean> getNewsArticle(String aid) {
        if (aid.startsWith("sub")) {
            return RetrofitFactory.getRetrofit().create(IFService.class).getNewsArticleWithSub(aid);
        } else {
            return RetrofitFactory.getRetrofit().create(IFService.class).getNewsArticleWithCmpp(IFApi.sGetNewsArticleCmppApi + IFApi.sGetNewsArticleDocCmppApi, aid);
        }
    }


}
