package com.example.rh.newsapp.module.video;

import com.example.rh.newsapp.base.BasePresenter;
import com.example.rh.newsapp.model.VideoChannelBean;
import com.example.rh.newsapp.model.VideoDetailBean;
import com.example.rh.newsapp.network.api.IFService;
import com.example.rh.newsapp.network.retrofit.RetrofitFactory;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author RH
 * @date 2018/5/7
 */
public class IFVideoPresenter extends BasePresenter<IFVideo.View> implements IFVideo.Presenter {
    private CompositeDisposable disposable = new CompositeDisposable();

    public IFVideoPresenter(CompositeDisposable disposable) {
        this.disposable = disposable;
    }


    @Override
    public void getVideoChannel() {
        RetrofitFactory.getRetrofit().create(IFService.class).getVideoChannel(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VideoChannelBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<VideoChannelBean> channelBean) {
                        getview().loadVideoChannel(channelBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getVideoDetails(int page, String listType, String typeId) {
        RetrofitFactory.getRetrofit().create(IFService.class).getVideoDetail(page, listType, typeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VideoDetailBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<VideoDetailBean> videoDetailBean) {
                        if (page > 1) {
                            getview().loadMoreVideoDetails(videoDetailBean);
                        } else {
                            getview().loadVideoDetails(videoDetailBean);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getview().loadVideoDetails(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
