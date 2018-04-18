package com.example.rh.newsapp.module.Hotchpotch.news.toutiao;

import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.example.rh.newsapp.adapter.TouTiaoAdapter;
import com.example.rh.newsapp.base.BaseHotchpotchFragment;
import com.example.rh.newsapp.model.ToutiaoNewsBean;
import com.example.rh.newsapp.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author RH
 * @date 2018/4/11
 */
public class TouTiaoNewsFragment extends BaseHotchpotchFragment<TouTiaoNewsPresenter> implements ITouTiaoNews.View {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static TouTiaoNewsFragment touTiaoNewsFragment;
    private TouTiaoAdapter touTiaoAdapter;
    private List<ToutiaoNewsBean.DataBean> dataBeanList = new ArrayList<>();

    public static TouTiaoNewsFragment getInstance() {
        if (touTiaoNewsFragment == null) {
            touTiaoNewsFragment = new TouTiaoNewsFragment();
        }
        return touTiaoNewsFragment;
    }

    @Override
    protected void setPresenter() {
        presenter = new TouTiaoNewsPresenter(compositeDisposable);
        presenter.attachView(this);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        touTiaoAdapter = new TouTiaoAdapter(dataBeanList);
        recyclerView.setAdapter(touTiaoAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

    }

    @Override
    public void onLazyLoad() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        presenter.loadData();
    }

    @Override
    public void onUpdateUI(List<ToutiaoNewsBean.DataBean> list) {
        for (int i = 0;i<list.size(); i++){
            String label = list.get(i).getLabel();
            if ("置顶".equals(label)||"广告".equals(label)){
                list.remove(i);
            }
        }
        dataBeanList.addAll(0, list);
        touTiaoAdapter.notifyDataSetChanged();
    }

    @Override
    public void stopLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showToast(String s) {
        MyToast.show(s);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //切断所有订阅事件，防止内存泄漏。
        compositeDisposable.clear();
        //取消Fragment和Presenter之间的关联
        if (presenter != null) {
            presenter.detachView();
        }
    }

}
