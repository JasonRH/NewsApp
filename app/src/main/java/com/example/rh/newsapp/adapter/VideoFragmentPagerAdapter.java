package com.example.rh.newsapp.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import com.example.rh.newsapp.model.VideoChannelBean;
import com.example.rh.newsapp.module.video.IFVideoFragment;

/**
 * @author RH
 * @date 2018/5/7
 */
public class VideoFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "VideoFragmentPagerAdapt";
    private VideoChannelBean videoChannelBean;

    public VideoFragmentPagerAdapter(FragmentManager fm ,VideoChannelBean videoChannelBean) {
        super(fm);
        this.videoChannelBean = videoChannelBean;
    }

    @Override
    public Fragment getItem(int position) {
        return IFVideoFragment.newInstance("clientvideo_" + videoChannelBean.getTypes().get(position).getId());
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return videoChannelBean.getTypes().get(position).getName();
    }

    @Override
    public int getCount() {
        return videoChannelBean != null ? videoChannelBean.getTypes().size() : 0;
    }

}
