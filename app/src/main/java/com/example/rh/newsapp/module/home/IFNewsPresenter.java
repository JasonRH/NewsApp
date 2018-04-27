package com.example.rh.newsapp.module.home;

import android.util.Log;

import com.example.rh.newsapp.base.BasePresenter;
import com.example.rh.newsapp.model.NewsDetail;
import com.example.rh.newsapp.network.api.IFApi;
import com.example.rh.newsapp.network.api.IFService;
import com.example.rh.newsapp.network.retrofit.RetrofitFactory;
import com.example.rh.newsapp.utils.IFNewsUtils;

import java.util.Iterator;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author RH
 * @date 2018/4/12
 */
public class IFNewsPresenter extends BasePresenter<IFNews.View> implements IFNews.Presenter {
    private CompositeDisposable disposable;

    public IFNewsPresenter(CompositeDisposable compositeDisposable) {
        disposable = compositeDisposable;
    }

    @Override
    public void getData(final String id, final String action, int pullNum) {
        RetrofitFactory.getRetrofit().create(IFService.class).getNewsDetail(id, action, pullNum)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<List<NewsDetail>, NewsDetail>() {
                    @Override
                    public NewsDetail apply(List<NewsDetail> newsDetails) throws Exception {
                        for (NewsDetail newsDetail : newsDetails) {
                            if (IFNewsUtils.isBannerNews(newsDetail)) {
                                getview().loadBannerData(newsDetail);
                            }
                            if (IFNewsUtils.isTopNews(newsDetail)) {
                                getview().loadTopNewsData(newsDetail);
                            }
                        }
                        return newsDetails.get(0);
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<NewsDetail, List<NewsDetail.ItemBean>>() {
                    @Override
                    public List<NewsDetail.ItemBean> apply(@NonNull NewsDetail newsDetail) throws Exception {
                        Iterator<NewsDetail.ItemBean> iterator = newsDetail.getItem().iterator();
                        while (iterator.hasNext()) {
                            try {
                                NewsDetail.ItemBean bean = iterator.next();
                                if (bean.getType().equals(IFNewsUtils.TYPE_DOC)) {
                                    if (bean.getStyle().getView() != null) {
                                        if (bean.getStyle().getView().equals(IFNewsUtils.VIEW_TITLEIMG)) {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_DOC_TITLEIMG;
                                        } else {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_DOC_SLIDEIMG;
                                        }
                                    }
                                } else if (bean.getType().equals(IFNewsUtils.TYPE_ADVERT)) {
                                    if (bean.getStyle() != null) {
                                        if (bean.getStyle().getView().equals(IFNewsUtils.VIEW_TITLEIMG)) {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_ADVERT_TITLEIMG;
                                        } else if (bean.getStyle().getView().equals(IFNewsUtils.VIEW_SLIDEIMG)) {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_ADVERT_SLIDEIMG;
                                        } else {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_ADVERT_LONGIMG;
                                        }
                                    } else {
                                        //bean.itemType = NewsDetail.ItemBean.TYPE_ADVERT_TITLEIMG;
                                        iterator.remove();
                                    }
                                } else if (bean.getType().equals(IFNewsUtils.TYPE_SLIDE)) {
                                    if (bean.getLink().getType().equals("doc")) {
                                        if (bean.getStyle().getView().equals(IFNewsUtils.VIEW_SLIDEIMG)) {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_DOC_SLIDEIMG;
                                        } else {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_DOC_TITLEIMG;
                                        }
                                    } else {
                                        bean.itemType = NewsDetail.ItemBean.TYPE_SLIDE;
                                    }
                                } else if (bean.getType().equals(IFNewsUtils.TYPE_PHVIDEO)) {
                                    bean.itemType = NewsDetail.ItemBean.TYPE_PHVIDEO;
                                } else {
                                    // 凤凰新闻 类型比较多，目前只处理能处理的类型
                                    iterator.remove();
                                }
                            } catch (Exception e) {
                                iterator.remove();
                                e.printStackTrace();
                            }
                        }
                        return newsDetail.getItem();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsDetail.ItemBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<NewsDetail.ItemBean> itemBeans) {
                        if (!action.equals(IFApi.ACTION_UP)) {
                            getview().loadData(itemBeans);
                        } else {
                            getview().loadMoreData(itemBeans);
                        }
                    }


                    @Override
                    public void onError(Throwable e) {
                        if (!action.equals(IFApi.ACTION_UP)) {
                            getview().loadData(null);
                        } else {
                            getview().loadMoreData(null);
                        }
                        getview().showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
