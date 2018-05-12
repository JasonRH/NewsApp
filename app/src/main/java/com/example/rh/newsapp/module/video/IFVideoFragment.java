package com.example.rh.newsapp.module.video;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.rh.newsapp.R;
import com.example.rh.newsapp.adapter.VideoDetailAdapter;
import com.example.rh.newsapp.base.BaseHomeFragment;
import com.example.rh.newsapp.model.VideoChannelBean;
import com.example.rh.newsapp.model.VideoDetailBean;
import com.example.rh.newsapp.widget.CustomLoadMoreView;
import com.kennyc.view.MultiStateView;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import io.reactivex.disposables.CompositeDisposable;


/**
 * @author RH
 * @date 2018/5/7
 */
public class IFVideoFragment extends BaseHomeFragment<IFVideoPresenter> implements IFVideo.View {
    private static final String TAG = "IFVideoFragment";
    public static final String TYPEID = "typeId";
    private CompositeDisposable disposable = new CompositeDisposable();
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private RecyclerView recyclerView;
    private MultiStateView multiStateView;
    private VideoDetailAdapter detailAdapter;
    private int pageNum = 1;
    String typeId;

    public static IFVideoFragment newInstance(String typeId) {
        IFVideoFragment fragment = new IFVideoFragment();
        Bundle args = new Bundle();
        args.putCharSequence(TYPEID, typeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setPresenter() {
        presenter = new IFVideoPresenter(disposable);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_fragment_if;
    }

    @Override
    protected void initView(View view) {
        ptrClassicFrameLayout = view.findViewById(R.id.if_news_ptrClassicFrameLayout);
        multiStateView = view.findViewById(R.id.if_news_multiStateView);
        recyclerView = view.findViewById(R.id.if_news_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(detailAdapter = new VideoDetailAdapter(getActivity(), R.layout.item_detail_video, new VideoDetailBean().getItem()));
        detailAdapter.setEnableLoadMore(true);
        detailAdapter.setLoadMoreView(new CustomLoadMoreView());
        detailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        detailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.getVideoDetails(pageNum, "list", typeId);
            }
        }, recyclerView);
        //初始化下拉刷新控件
        initPtrClassicFrameLayout();
    }


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
                if (presenter != null) {
                    pageNum = 1;
                    presenter.getVideoDetails(pageNum, "list", typeId);
                }
            }
        });
    }


    @Override
    protected void onLazyLoad() {
        Log.e(TAG, "onLazyLoad: ");
        typeId = getArguments().getString(TYPEID);
        if (presenter != null) {
            presenter.getVideoDetails(pageNum, "list", typeId);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @Override
    public void loadVideoChannel(List<VideoChannelBean> channelBean) {

    }

    @Override
    public void loadMoreVideoDetails(List<VideoDetailBean> videoDetailBean) {
        if (videoDetailBean == null) {
            detailAdapter.loadMoreEnd();
            return;
        }
        pageNum++;
        detailAdapter.addData(videoDetailBean.get(0).getItem());
        detailAdapter.loadMoreComplete();
    }

    @Override
    public void loadVideoDetails(List<VideoDetailBean> videoDetailBean) {
        if (videoDetailBean == null) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            return;
        }
        pageNum++;
        detailAdapter.setNewData(videoDetailBean.get(0).getItem());
        ptrClassicFrameLayout.refreshComplete();
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }
}
