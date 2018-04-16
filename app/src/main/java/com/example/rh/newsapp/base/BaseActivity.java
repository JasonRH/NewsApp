package com.example.rh.newsapp.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.rh.newsapp.R;
import com.example.rh.newsapp.utils.StatusBarUtil;


/**
 * @author RH
 * @date 2018/3/29
 */
public abstract class BaseActivity extends SupportActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());

        /**根据机型修改状态栏背景为透明，字体为黑色*/
        //StatusBarUtil.getStatusBarLightMode(getWindow());

        initView();
    }

    public abstract int setLayoutId();

    protected abstract void initView();

}
