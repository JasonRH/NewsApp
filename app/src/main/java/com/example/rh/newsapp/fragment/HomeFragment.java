package com.example.rh.newsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rh.newsapp.MyApplication;
import com.example.rh.newsapp.R;
import com.example.rh.newsapp.activity.ChannelActivity;
import com.example.rh.newsapp.adapter.HomeFragmentPagerAdapter;
import com.example.rh.newsapp.base.SupportFragment;
import com.example.rh.newsapp.database.ChannelDao;
import com.example.rh.newsapp.model.Channel;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * @author RH
 */
public class HomeFragment extends SupportFragment {

    public static HomeFragment homeFragment;
    public static boolean isHide = false;
    List<Channel> selectedChannels = new ArrayList<>();
    HomeFragmentPagerAdapter pagerAdapter;
    SlidingTabLayout tabLayout;
    ViewPager viewPager;

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
        tabLayout = view.findViewById(R.id.home_tab_layout);
        viewPager = view.findViewById(R.id.home_viewpager);

        //限定预加载的页面个数
        viewPager.setOffscreenPageLimit(3);

        updateChannel();
        pagerAdapter = new HomeFragmentPagerAdapter(getChildFragmentManager(), selectedChannels);
        viewPager.setAdapter(pagerAdapter);

        //SlidingTabLayout和ViewPager有多种setViewPager（，，，）关联方法，甚至连适配器都不用自己实例化，此处采用最基本的关联
        tabLayout.setViewPager(viewPager);

        view.findViewById(R.id.home_img_button).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChannelActivity.class);
            startActivityForResult(intent, 1);
        });

    }

    /**
     * 更新频道
     */
    public void updateChannel() {
        List<Channel> channelList;
        channelList = ChannelDao.findAll();

        if (channelList.size() < 1) {
            //初次安装，数据库无数据时
            List<String> channelName = Arrays.asList(MyApplication.getContext().getResources()
                    .getStringArray(R.array.news_channel));
            List<String> channelId = Arrays.asList(MyApplication.getContext().getResources()
                    .getStringArray(R.array.news_channel_id));
            for (int i = 0; i < channelName.size(); i++) {
                Channel channel = new Channel();
                channel.setChannelId(channelId.get(i));
                channel.setChannelName(channelName.get(i));
                if (i < 6) {
                    channel.setChannelSelect(true);
                    selectedChannels.add(channel);
                } else {
                    channel.setChannelSelect(false);
                }
                channel.save();
            }
        } else {
            //数据库有数据时
            Iterator<Channel> iterator = channelList.iterator();
            while (iterator.hasNext()) {
                Channel channel = iterator.next();
                if (!channel.isChannelSelect()) {
                    iterator.remove();
                }
            }
            selectedChannels.clear();
            selectedChannels.addAll(channelList);
            if (pagerAdapter != null) {
                tabLayout.notifyDataSetChanged();
                viewPager.setCurrentItem(0, false);
                //将FragmentStatePagerAdapter的getItemPosition默认值改为POSITION_NONE，否则notifyDataSetChanged不生效
                pagerAdapter.notifyDataSetChanged();
            }
        }

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        // 当前Fragment是隐藏还是可视
        isHide = hidden;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            updateChannel();
        }
    }
}
