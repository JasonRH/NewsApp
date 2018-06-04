package com.example.rh.newsapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.rh.newsapp.model.Channel;
import com.example.rh.newsapp.module.home.IFNewsFragment;

import java.util.List;

/**
 * @author RH
 * @date 2018/4/13
 */
public class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Channel> mChannels;


    public HomeFragmentPagerAdapter(FragmentManager childFragmentManager, List<Channel> channels) {
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

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels.get(position).getChannelName();
    }

    @Override
    public int getItemPosition(Object object) {
        //默认 Item 的位置发生变化，销毁当前页面的fragment并重新生成
        return POSITION_NONE;
    }
}
