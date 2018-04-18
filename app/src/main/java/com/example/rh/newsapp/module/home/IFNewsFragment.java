package com.example.rh.newsapp.module.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.rh.newsapp.R;
import com.example.rh.newsapp.adapter.IFNewsAdapter;
import com.example.rh.newsapp.base.BaseHomeFragment;
import com.example.rh.newsapp.model.NewsDetail;
import com.example.rh.newsapp.network.api.IFApi;
import com.example.rh.newsapp.utils.MyToast;
import com.example.rh.newsapp.widget.CustomLoadMoreView;
import com.kennyc.view.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @author RH
 * @date 2018/4/12
 */
public class IFNewsFragment extends BaseHomeFragment<IFNewsPresenter> implements IFNews.View {
    private static final String TAG = "IFNewsFragment";
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private RecyclerView recyclerView;
    private MultiStateView multiStateView;
    private int upPullNum = 1;
    private int downPullNum = 1;
    private IFNewsAdapter ifNewsAdapter;
    private List<NewsDetail.ItemBean> beanList = new ArrayList<>();

    public static IFNewsFragment newInstance(String newsId) {
        IFNewsFragment fragment = new IFNewsFragment();
        Bundle args = new Bundle();
        args.putString("id", newsId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override

    protected int getLayoutId() {
        return R.layout.base_fragment_if_news;
    }

    @Override
    protected void setPresenter() {
        presenter = new IFNewsPresenter();
    }


    @Override
    protected void initView(View view) {
        ptrClassicFrameLayout = view.findViewById(R.id.if_news_ptrClassicFrameLayout);
        multiStateView = view.findViewById(R.id.if_news_multiStateView);
        recyclerView = view.findViewById(R.id.if_news_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //初始化下拉刷新控件
        initPtrClassicFrameLayout();

        ifNewsAdapter = new IFNewsAdapter(beanList);

        recyclerView.setAdapter(ifNewsAdapter);

        ifNewsAdapter.setEnableLoadMore(true);
        //开源控件，自定义下拉动画
        ifNewsAdapter.setLoadMoreView(new CustomLoadMoreView());
        ifNewsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        ifNewsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.getData(getArguments().getString("id"), IFApi.ACTION_UP, upPullNum);
            }
        }, recyclerView);

    }


    @Override
    public void onLazyLoad() {
        if (getArguments() == null) {
            return;
        } else {
            presenter.getData(getArguments().getString("id"), IFApi.ACTION_DEFAULT, 1);
        }
    }

    /**
     * 下拉刷新
     */
    private void initPtrClassicFrameLayout() {
        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                //判断recyclerView是否可以下拉
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, recyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //加载数据
                presenter.getData(getArguments().getString("id"), IFApi.ACTION_DOWN, downPullNum);
            }
        });
    }

    @Override
    public void loadData(List<NewsDetail.ItemBean> itemBeanList) {
        if (itemBeanList == null || itemBeanList.size() == 0) {
            //下拉刷新完成
            ptrClassicFrameLayout.refreshComplete();
            //根据状态更改布局
            if (multiStateView.getViewState() != MultiStateView.VIEW_STATE_CONTENT) {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        } else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            downPullNum++;
            ifNewsAdapter.setNewData(itemBeanList);
            ptrClassicFrameLayout.refreshComplete();
        }
    }

    @Override
    public void loadBannerData(NewsDetail newsDetail) {
        Log.e(TAG, "loadBannerData: ");
    }

    @Override
    public void loadTopNewsData(NewsDetail newsDetail) {
        Log.e(TAG, "loadTopNewsData: ");
    }


    @Override
    public void loadMoreData(List<NewsDetail.ItemBean> itemBeanList) {
        if (itemBeanList == null || itemBeanList.size() == 0) {
            ifNewsAdapter.loadMoreFail();
        } else {
            upPullNum++;
            ifNewsAdapter.addData(itemBeanList);
            ifNewsAdapter.loadMoreComplete();
        }
    }

    @Override
    public void showToast(String s) {
        MyToast.show(s);
    }
}
