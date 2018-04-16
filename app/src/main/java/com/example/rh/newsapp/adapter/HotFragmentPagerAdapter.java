package com.example.rh.newsapp.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rh.newsapp.model.Channel;
import com.example.rh.newsapp.module.hot.IFNewsFragment;

import java.util.List;

/**
 * @author RH
 * @date 2018/4/13
 */
public class HotFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Channel> mChannels;


    public HotFragmentPagerAdapter(FragmentManager childFragmentManager, List<Channel> channels) {
        super(childFragmentManager);
        this.mChannels = channels;
    }

    @Override
    public Fragment getItem(int position) {
        return IFNewsFragment.newInstance(mChannels.get(position).getChannelId());
    }

    @Override
    public int getCount() {
        return mChannels != null ? mChannels.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels.get(position).getChannelName();
    }
}
