package com.example.rh.newsapp.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.example.rh.newsapp.R;
import com.example.rh.newsapp.adapter.VideoFragmentPagerAdapter;
import com.example.rh.newsapp.base.BaseHomeFragment;
import com.example.rh.newsapp.model.VideoChannelBean;
import com.example.rh.newsapp.model.VideoDetailBean;
import com.example.rh.newsapp.module.video.IFVideo;
import com.example.rh.newsapp.module.video.IFVideoPresenter;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


/**
 * @author RH
 */
public class VideoFragment extends BaseHomeFragment<IFVideoPresenter> implements IFVideo.View {
    private static final String TAG = "VideoFragment";
    public static VideoFragment videoFragment;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CompositeDisposable disposable = new CompositeDisposable();

    public static VideoFragment getInstance() {
        if (videoFragment == null) {
            videoFragment = new VideoFragment();
        }
        return videoFragment;
    }


    @Override
    protected void setPresenter() {
        presenter = new IFVideoPresenter(disposable);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView(View view) {
        tabLayout = view.findViewById(R.id.video_tab_layout);
        viewPager = view.findViewById(R.id.video_viewpager);
        tabLayout.setupWithViewPager(viewPager, true);
        //标签部分显示
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        //限定预加载的页面个数
        viewPager.setOffscreenPageLimit(3);

        presenter.getVideoChannel();
    }

    @Override
    protected void onLazyLoad() {
        //presenter.getVideoChannel();
    }

    @Override
    public void loadVideoChannel(List<VideoChannelBean> channelBean) {
        VideoFragmentPagerAdapter mVideoPagerAdapter = new VideoFragmentPagerAdapter(getChildFragmentManager(), channelBean.get(0));
        viewPager.setAdapter(mVideoPagerAdapter);
    }

    @Override
    public void loadMoreVideoDetails(List<VideoDetailBean> videoDetailBean) {
        //无用
    }

    @Override
    public void loadVideoDetails(List<VideoDetailBean> videoDetailBean) {
        //无用
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.clear();
        }
    }
}
