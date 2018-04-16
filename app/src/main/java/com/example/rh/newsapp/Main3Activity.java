package com.example.rh.newsapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.example.rh.newsapp.base.BaseActivity;
import com.example.rh.newsapp.fragment.HotchpotchFragment;
import com.example.rh.newsapp.fragment.HomeFragment;
import com.example.rh.newsapp.fragment.VideoFragment;
import com.example.rh.newsapp.widget.BottomNavigationViewHelper;

/**
 * @author RH
 */
public class Main3Activity extends BaseActivity {
    private HotchpotchFragment hotchpotchFragment;
    private VideoFragment videoFragment;
    private HomeFragment homeFragment;

    @Override
    public int setLayoutId() {
        return R.layout.activity_main3;
    }

    @Override
    protected void initView() {
        BottomNavigationView navigation = findViewById(R.id.home_navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        showFragment(0);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home_refresh:
                    showFragment(0);
                    return true;
                case R.id.navigation_home_video:
                    showFragment(1);
                    return true;
                case R.id.navigation_home_hot:
                    showFragment(2);
                    return true;
                case R.id.navigation_home_setting:
                    showFragment(3);
                    return true;
                default:
            }
            return false;
        }
    };

    private void showFragment(int index) {
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentManager);
        switch (index) {
            case 0:
                if(homeFragment == null){
                    homeFragment = HomeFragment.getInstance();
                    fragmentManager.add(R.id.main_activity_container , homeFragment);
                }else {
                    fragmentManager.show(homeFragment);
                }
                break;
            case 1:
                if (videoFragment == null){
                    videoFragment = VideoFragment.getInstance();
                    fragmentManager.add(R.id.main_activity_container , videoFragment);
                }else {
                    fragmentManager.show(videoFragment);
                }
                break;
            case 2:
                if (hotchpotchFragment == null) {
                    hotchpotchFragment = HotchpotchFragment.getInstance();
                    fragmentManager.add(R.id.main_activity_container, hotchpotchFragment);
                } else {
                    fragmentManager.show(hotchpotchFragment);
                }
                break;
            case 3:
                break;
            default:
                break;
        }
        fragmentManager.commit();
    }

    private void hideFragment(FragmentTransaction fragmentManager) {
        if (hotchpotchFragment != null) {
            fragmentManager.hide(hotchpotchFragment);
        }
        if (videoFragment != null) {
            fragmentManager.hide(videoFragment);
        }
        if (homeFragment != null){
            fragmentManager.hide(homeFragment);
        }
    }
}
