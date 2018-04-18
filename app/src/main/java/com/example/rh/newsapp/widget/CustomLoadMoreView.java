package com.example.rh.newsapp.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.example.rh.newsapp.R;

/**
 * @author RH
 * @date 2018/4/18
 */
public class CustomLoadMoreView extends LoadMoreView {

    @Override public int getLayoutId() {
        return R.layout.view_load_more;
    }

    /**
     * If you return to true, the data will be loaded more after all the data is loaded.
     * If you return to false, the data will be displayed after all the getLoadEndViewId () layout
     */
    @Override public boolean isLoadEndGone() {
        return true;
    }

    @Override protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    /**
     * IsLoadEndGone () for true, you can return 0
     * IsLoadEndGone () for false, can not return 0
     */
    @Override protected int getLoadEndViewId() {
        return 0;
    }
}
