package com.example.rh.newsapp.module.home.news.idata;

import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.example.rh.newsapp.adapter.NewsAdapter;
import com.example.rh.newsapp.base.BaseHotchpotchFragment;
import com.example.rh.newsapp.model.News360Bean;
import com.example.rh.newsapp.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author RH
 * @date 2018/4/11
 */
public class IDNewsFragment extends BaseHotchpotchFragment<IDNewsPresenter> implements IDNews.View {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static IDNewsFragment IDNewsFragment;
    private NewsAdapter newsAdapter;
    private List<News360Bean.DataBean> dataBeanList = new ArrayList<>();

    public static IDNewsFragment getInstance() {
        if (IDNewsFragment == null) {
            IDNewsFragment = new IDNewsFragment();
        }
        return IDNewsFragment;
    }

    @Override
    protected void setPresenter() {
        presenter = new IDNewsPresenter(compositeDisposable);
        presenter.attachView(this);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        newsAdapter = new NewsAdapter(dataBeanList);
        recyclerView.setAdapter(newsAdapter);
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
    public void onUpdateUI(List<News360Bean.DataBean> list) {
        dataBeanList.addAll(0, list);
        newsAdapter.notifyDataSetChanged();
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
