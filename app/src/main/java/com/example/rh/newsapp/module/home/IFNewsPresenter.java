package com.example.rh.newsapp.module.home;

import com.example.rh.newsapp.base.BasePresenter;
import com.example.rh.newsapp.model.NewsDetailBean;
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
                .map(new Function<List<NewsDetailBean>, NewsDetailBean>() {
                    @Override
                    public NewsDetailBean apply(List<NewsDetailBean> newsDetailBeans) throws Exception {
                        for (NewsDetailBean newsDetailBean : newsDetailBeans) {
                            if (IFNewsUtils.isBannerNews(newsDetailBean)) {
                                getview().loadBannerData(newsDetailBean);
                            }
                            if (IFNewsUtils.isTopNews(newsDetailBean)) {
                                getview().loadTopNewsData(newsDetailBean);
                            }
                        }
                        return newsDetailBeans.get(0);
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<NewsDetailBean, List<NewsDetailBean.ItemBean>>() {
                    @Override
                    public List<NewsDetailBean.ItemBean> apply(@NonNull NewsDetailBean newsDetailBean) throws Exception {
                        Iterator<NewsDetailBean.ItemBean> iterator = newsDetailBean.getItem().iterator();
                        while (iterator.hasNext()) {
                            try {
                                NewsDetailBean.ItemBean bean = iterator.next();
                                switch (bean.getType()) {
                                    //文章类型
                                    case IFNewsUtils.TYPE_DOC:
                                        if (bean.getStyle().getView() != null) {
                                            if (bean.getStyle().getView().equals(IFNewsUtils.VIEW_TITLEIMG)) {
                                                //单图、小图
                                                bean.itemType = NewsDetailBean.ItemBean.TYPE_DOC_TITLEIMG;
                                            } else {
                                                //多图
                                                bean.itemType = NewsDetailBean.ItemBean.TYPE_DOC_SLIDEIMG;
                                            }
                                        }
                                        break;
                                    //广告类型
                                    case IFNewsUtils.TYPE_ADVERT:
                                        if (bean.getStyle() != null) {
                                            switch (bean.getStyle().getView()) {
                                                case IFNewsUtils.VIEW_TITLEIMG:
                                                    //单图、小图
                                                    bean.itemType = NewsDetailBean.ItemBean.TYPE_ADVERT_TITLEIMG;
                                                    break;
                                                case IFNewsUtils.VIEW_SLIDEIMG:
                                                    //多图
                                                    bean.itemType = NewsDetailBean.ItemBean.TYPE_ADVERT_SLIDEIMG;
                                                    break;
                                                default:
                                                    //大图
                                                    bean.itemType = NewsDetailBean.ItemBean.TYPE_ADVERT_LONGIMG;
                                                    break;
                                            }
                                        } else {
                                            //bean.itemType = NewsDetailBean.ItemBean.TYPE_ADVERT_TITLEIMG;
                                            iterator.remove();
                                        }
                                        break;
                                    //图片类型
                                    case IFNewsUtils.TYPE_SLIDE:
                                        if ("doc".equals(bean.getLink().getType())) {
                                            if (bean.getStyle().getView().equals(IFNewsUtils.VIEW_SLIDEIMG)) {
                                                //多图
                                                bean.itemType = NewsDetailBean.ItemBean.TYPE_DOC_SLIDEIMG;
                                            } else {
                                                //单图、小图
                                                bean.itemType = NewsDetailBean.ItemBean.TYPE_DOC_TITLEIMG;
                                            }
                                        } else {
                                            //大图
                                            bean.itemType = NewsDetailBean.ItemBean.TYPE_SLIDE;
                                        }
                                        break;
                                    //视频类型
                                    case IFNewsUtils.TYPE_PHVIDEO:
                                        bean.itemType = NewsDetailBean.ItemBean.TYPE_PHVIDEO;
                                        break;
                                    default:
                                        // 凤凰新闻 类型比较多，目前只处理能处理的类型
                                        iterator.remove();
                                        break;
                                }
                            } catch (Exception e) {
                                iterator.remove();
                                e.printStackTrace();
                            }
                        }
                        return newsDetailBean.getItem();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsDetailBean.ItemBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<NewsDetailBean.ItemBean> itemBeans) {
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
