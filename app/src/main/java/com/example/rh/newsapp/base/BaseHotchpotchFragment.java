package com.example.rh.newsapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rh.newsapp.R;

/**
 * @author RH
 * @date 2018/3/29
 */
public abstract class BaseHotchpotchFragment<T extends BasePresenter> extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout refreshLayout;
    protected T presenter;

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isLazyLoaded;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
    }

    protected abstract void setPresenter();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view);
        return view;
    }

    protected int getLayoutId() {
        return R.layout.base_fragment_hotchpotch;
    }

    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
    }


    /**
     * 懒加载：方案一
     * 该方案不完善
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }


    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        //当前Fragment可见&&布局已初始化完毕&&没有懒加载过
        if (isVisibleToUser && isViewInitiated && (!isLazyLoaded || forceUpdate)) {
            onLazyLoad();
            isLazyLoaded = true;
            return true;
        }
        return false;
    }

    public abstract void onLazyLoad();


}
