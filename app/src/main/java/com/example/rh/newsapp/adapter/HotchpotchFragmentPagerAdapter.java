package com.example.rh.newsapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author RH
 * @date 2018/3/29
 */
public class HotchpotchFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private String[] strings;

    public HotchpotchFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] strings) {
        super(fm);
        this.fragmentList = fragmentList;
        this.strings = strings;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList != null ? fragmentList.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return strings[position];
    }
}
