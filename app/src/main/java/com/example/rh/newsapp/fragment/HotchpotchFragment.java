package com.example.rh.newsapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rh.newsapp.R;
import com.example.rh.newsapp.adapter.HomeFragmentPagerAdapter;
import com.example.rh.newsapp.module.Hotchpotch.bing.BingPictureFragment;
import com.example.rh.newsapp.module.Hotchpotch.joke.JokeFragment;
import com.example.rh.newsapp.module.Hotchpotch.photo.PhotoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RH
 */
public class HotchpotchFragment extends Fragment {
    public static HotchpotchFragment hotchpotchFragment;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static HotchpotchFragment getInstance() {
        if (hotchpotchFragment == null) {
            hotchpotchFragment = new HotchpotchFragment();
        }
        return hotchpotchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotchpotch, container, false);
        initView(view);
        return view;
    }


    protected void initView(View view) {
        tabLayout = view.findViewById(R.id.hotchpotch_tab_layout);
        viewPager = view.findViewById(R.id.hotchpotch_fragment_viewpager);
        tabLayout.setupWithViewPager(viewPager);
        //标签全部显示
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //限定预加载的页面个数
        viewPager.setOffscreenPageLimit(5);

        initFragmentPagerAdapter();
    }

    private void initFragmentPagerAdapter() {
        List<Fragment> fragmentList = new ArrayList<>();
        String[] strings = new String[]{"必应", "段子", "图片"};

        fragmentList.add(BingPictureFragment.getInstance());
        fragmentList.add(JokeFragment.getInstance());
        fragmentList.add(PhotoFragment.getInstance());

        HomeFragmentPagerAdapter pagerAdapter = new HomeFragmentPagerAdapter(getChildFragmentManager(), fragmentList, strings);
        viewPager.setAdapter(pagerAdapter);
    }

}
