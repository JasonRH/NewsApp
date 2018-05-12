package com.example.rh.newsapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author RH
 * @date 2018/4/13
 */
public abstract class BaseHomeFragment<T extends BasePresenter> extends SupportFragment {

    protected T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        attachView();
    }

    protected abstract void setPresenter();

    /**
     * 绑定Fragment和Presenter
     */
    private void attachView() {
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view);
        return view;
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    /**
     * 懒加载：方案二
     * 使用fragmentation实现懒加载
     *
     * 先initView()后onLazyInitView()
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        onLazyLoad();
    }

    protected abstract void onLazyLoad();


    @Override
    public void onDestroy() {
        super.onDestroy();
        /**
         * 解绑Fragment和Presenter
         */
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
