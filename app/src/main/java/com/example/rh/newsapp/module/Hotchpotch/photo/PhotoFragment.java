package com.example.rh.newsapp.module.Hotchpotch.photo;

import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.rh.newsapp.adapter.PhotoAdapter;
import com.example.rh.newsapp.base.BaseHotchpotchFragment;
import com.example.rh.newsapp.model.PhotoArticleBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


/**
 * @author RH
 * @date 2018/3/5
 */
public class PhotoFragment extends BaseHotchpotchFragment<PhotoPresenter> implements IPhotoArticle.View {
    private static final String TAG = "PhotoFragment";
    public static PhotoFragment photoFragment;
    private PhotoAdapter photoAdapter;
    private List<PhotoArticleBean.Data> dataList = new ArrayList<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static PhotoFragment getInstance() {
        if (photoFragment == null) {
            photoFragment = new PhotoFragment();
        }
        return photoFragment;
    }

    @Override
    protected void setPresenter() {
        presenter = new PhotoPresenter(compositeDisposable);
        //Fragment和Presenter之间使用弱引用关联
        presenter.attachView(this);
    }

    /**
     * 重写父类方法
     */
    @Override
    protected void initView(View view) {
        super.initView(view);
        photoAdapter = new PhotoAdapter(dataList);
        recyclerView.setAdapter(photoAdapter);
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onLazyLoad() {
        Log.e(TAG, "fetchData: ");
        onRefresh();
    }
    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        presenter.loadData();
    }

    @Override
    public void onUpdateUI(List<PhotoArticleBean.Data> list) {
        dataList.clear();
        dataList.addAll(list);
        //dataList.addAll(0, list);
        photoAdapter.notifyDataSetChanged();
    }

    @Override
    public void stopLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //切断所有订阅事件，防止内存泄漏。
        // CompositeDisposable的clear()方法和dispose()方法类似，clear()可以多次被调用来丢弃容器中所有的Disposable，但dispose()被调用一次后就会失效。
        compositeDisposable.clear();
        //取消Fragment和Presenter之间的关联
        if (presenter != null) {
            presenter.detachView();
        }
    }

}
