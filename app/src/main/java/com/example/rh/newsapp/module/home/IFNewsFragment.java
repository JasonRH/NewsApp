package com.example.rh.newsapp.module.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.rh.newsapp.R;
import com.example.rh.newsapp.activity.ArticleActivity;
import com.example.rh.newsapp.activity.WebActivity;
import com.example.rh.newsapp.adapter.IFNewsAdapter;
import com.example.rh.newsapp.base.BaseHomeFragment;
import com.example.rh.newsapp.model.NewsDetailBean;
import com.example.rh.newsapp.network.api.IFApi;
import com.example.rh.newsapp.utils.IFNewsUtils;
import com.example.rh.newsapp.utils.ImageLoaderUtil;
import com.example.rh.newsapp.utils.MyToast;
import com.example.rh.newsapp.widget.CustomLoadMoreView;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.kennyc.view.MultiStateView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author RH
 * @date 2018/4/12
 */
public class IFNewsFragment extends BaseHomeFragment<IFNewsPresenter> implements IFNews.View {
    private static final String TAG = "IFNewsFragment";
    /**
     * 下拉刷新控件
     */
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private RecyclerView recyclerView;
    private MultiStateView multiStateView;
    private RelativeLayout topToastRelativeLayout;
    private TextView topToastTextView;
    private int upPullNum = 1;
    private int downPullNum = 1;
    private IFNewsAdapter ifNewsAdapter;
    private List<NewsDetailBean.ItemBean> beanList = new ArrayList<>();

    private List<NewsDetailBean.ItemBean> mBannerList = new ArrayList<>();
    /**
     * 顶部banner
     */
    private View viewFocus;
    private Banner mBanner;
    private boolean isRemoveHeaderView = false;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static IFNewsFragment newInstance(String newsId) {
        IFNewsFragment fragment = new IFNewsFragment();
        Bundle args = new Bundle();
        args.putString("id", newsId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override

    protected int getLayoutId() {
        return R.layout.base_fragment_if;
    }

    @Override
    protected void setPresenter() {
        presenter = new IFNewsPresenter(compositeDisposable);
    }


    @Override
    protected void initView(View view) {
        topToastRelativeLayout = view.findViewById(R.id.if_news_top_toast_relative);
        topToastTextView = view.findViewById(R.id.if_news_top_toast_tv);

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
        //下拉加载更过
        ifNewsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (getArguments() != null) {
                    presenter.getData(getArguments().getString("id"), IFApi.ACTION_UP, upPullNum);
                }
            }
        }, recyclerView);

        //item的点击事件
        ifNewsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewsDetailBean.ItemBean itemBean = (NewsDetailBean.ItemBean) adapter.getItem(position);
                toStartActivity(itemBean);
            }
        });
        //Item子控件的点击事件
        ifNewsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        //初始化轮播图
        initBanner();
    }

    @Override
    public void onLazyLoad() {
        if (getArguments() != null) {
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
                //移除RecycleView的头部轮播图
                isRemoveHeaderView = true;
                //加载数据
                if (getArguments() != null) {
                    presenter.getData(getArguments().getString("id"), IFApi.ACTION_DOWN, downPullNum);
                }
            }
        });
    }

    /**
     * 轮播图
     */
    private void initBanner() {
        viewFocus = LayoutInflater.from(getActivity()).inflate(R.layout.news_detail_headerview, null);
        mBanner = viewFocus.findViewById(R.id.banner);
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        //Glide 加载图片简单用法
                        ImageLoaderUtil.LoadImage(getActivity(), path, imageView);
                    }
                })
                .setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (mBannerList.size() < 1) {
                    return;
                }
                toTopStartActivity(mBannerList.get(position));
            }
        });
    }


    @Override
    public void loadData(List<NewsDetailBean.ItemBean> itemBeanList) {
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
            if (isRemoveHeaderView) {
                //移除顶部轮播图
                ifNewsAdapter.removeAllHeaderView();
            }
            ifNewsAdapter.setNewData(itemBeanList);
            showTopToast(itemBeanList.size());
            ptrClassicFrameLayout.refreshComplete();
        }
    }


    @Override
    public void loadBannerData(NewsDetailBean newsDetailBean) {
        List<String> mTitleList = new ArrayList<>();
        List<String> mUrlList = new ArrayList<>();
        mBannerList.clear();
        for (NewsDetailBean.ItemBean bean : newsDetailBean.getItem()) {
            if (!TextUtils.isEmpty(bean.getThumbnail())) {
                mTitleList.add(bean.getTitle());
                mBannerList.add(bean);
                mUrlList.add(bean.getThumbnail());
            }
        }
        if (mUrlList.size() > 0) {
            mBanner.setImages(mUrlList);
            mBanner.setBannerTitles(mTitleList);
            mBanner.start();
            if (ifNewsAdapter.getHeaderLayoutCount() < 1) {
                ifNewsAdapter.addHeaderView(viewFocus);
            }
        }
    }

    @Override
    public void loadTopNewsData(NewsDetailBean newsDetailBean) {
        //Log.e(TAG, "loadTopNewsData: ");
    }


    @Override
    public void loadMoreData(List<NewsDetailBean.ItemBean> itemBeanList) {
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

    private void showTopToast(int size) {
        topToastTextView.setText(String.format("已为您推荐了%1$s条新资讯", size));
        topToastRelativeLayout.setVisibility(View.VISIBLE);
        ViewAnimator.animate(topToastRelativeLayout)
                .newsPaper()
                .duration(1000)
                .start()
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        ViewAnimator.animate(topToastRelativeLayout)
                                .bounceOut()
                                .duration(1000)
                                .start();
                    }
                });
    }

    /**
     * 打开新闻详情页
     */
    private void toStartActivity(NewsDetailBean.ItemBean itemBean) {
        if (itemBean != null) {
            switch (itemBean.getItemType()) {
                case NewsDetailBean.ItemBean.TYPE_DOC_TITLEIMG:
                case NewsDetailBean.ItemBean.TYPE_DOC_SLIDEIMG:
                    Intent intent = new Intent(getActivity(), ArticleActivity.class);
                    intent.putExtra("url", itemBean.getDocumentId());
                    startActivity(intent);
                    break;
                case NewsDetailBean.ItemBean.TYPE_SLIDE:
                    Log.e(TAG, "toStartActivity:TYPE_SLIDE ");
                    break;
                case NewsDetailBean.ItemBean.TYPE_ADVERT_TITLEIMG:
                case NewsDetailBean.ItemBean.TYPE_ADVERT_SLIDEIMG:
                case NewsDetailBean.ItemBean.TYPE_ADVERT_LONGIMG:
                    String url = itemBean.getLink().getWeburl();
                    if (!TextUtils.isEmpty(url)) {
                        Intent adIntent = new Intent(getActivity(), WebActivity.class);
                        adIntent.putExtra("url", itemBean.getLink().getWeburl());
                        startActivity(adIntent);
                    }
                    break;
                case NewsDetailBean.ItemBean.TYPE_PHVIDEO:
                    MyToast.show("TYPE_PHVIDEO");
                    break;
                default:
            }
        }
    }

    /**
     * 打开轮播图详情页
     */
    private void toTopStartActivity(NewsDetailBean.ItemBean itemBean) {
        if (itemBean != null) {
            switch (itemBean.getType()) {
                case IFNewsUtils.TYPE_DOC:
                    Intent intent = new Intent(getActivity(), ArticleActivity.class);
                    intent.putExtra("url", itemBean.getDocumentId());
                    startActivity(intent);
                    break;
                case IFNewsUtils.TYPE_SLIDE:
                    Log.i(TAG, "toTopStartActivity:TYPE_SLIDE ");
                    break;
                case IFNewsUtils.TYPE_ADVERT:
                    String url = itemBean.getLink().getWeburl();
                    if (!TextUtils.isEmpty(url)) {
                        Intent adIntent = new Intent(getActivity(), WebActivity.class);
                        adIntent.putExtra("url", itemBean.getLink().getWeburl());
                        startActivity(adIntent);
                    }
                    break;
                case IFNewsUtils.TYPE_PHVIDEO:
                    MyToast.show("TYPE_PHVIDEO");
                    break;
                default:
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
