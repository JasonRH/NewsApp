package com.example.rh.newsapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.rh.newsapp.MyApplication;
import com.example.rh.newsapp.R;
import com.example.rh.newsapp.adapter.HotFragmentPagerAdapter;
import com.example.rh.newsapp.base.SupportFragment;
import com.example.rh.newsapp.model.Channel;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author RH
 */
public class HomeFragment extends SupportFragment {

    public static HomeFragment homeFragment;


    public static HomeFragment getInstance() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        SlidingTabLayout tabLayout = view.findViewById(R.id.home_tab_layout);
        ImageButton imageButton = view.findViewById(R.id.home_img_button);
        ViewPager viewPager = view.findViewById(R.id.home_viewpager);

        //限定预加载的页面个数
        viewPager.setOffscreenPageLimit(3);

        List<Channel> channels = getChannel();
        //initFragment();
        HotFragmentPagerAdapter pagerAdapter = new HotFragmentPagerAdapter(getChildFragmentManager(), channels);
        viewPager.setAdapter(pagerAdapter);

        //SlidingTabLayout和ViewPager有多种setViewPager（，，，）关联方法，甚至连适配器都不用自己实例化，此处采用最基本的关联
        tabLayout.setViewPager(viewPager);
    }


    public List<Channel> getChannel() {
        List<Channel> myChannels = new ArrayList<>();
        List<Channel> otherChannels = new ArrayList<>();
        List<String> channelName = Arrays.asList(MyApplication.getContext().getResources()
                .getStringArray(R.array.news_channel));
        List<String> channelId = Arrays.asList(MyApplication.getContext().getResources()
                .getStringArray(R.array.news_channel_id));
        List<Channel> channels = new ArrayList<>();

        for (int i = 0; i < channelName.size(); i++) {
            Channel channel = new Channel();
            channel.setChannelId(channelId.get(i));
            channel.setChannelName(channelName.get(i));
            channel.setChannelType(i < 1 ? 1 : 0);
            channel.setChannelSelect(i < channelId.size() - 3);
            if (i < channelId.size() - 3) {
                myChannels.add(channel);
            } else {
                otherChannels.add(channel);
            }
            channels.add(channel);
        }

        return myChannels;
    }

}