package com.example.rh.newsapp.module.home;

import com.example.rh.newsapp.model.NewsDetailBean;

import java.util.List;

/**
 * @author RH
 * @date 2018/4/12
 */
public interface IFNews {
    interface View {
        /**
         * 加载新闻数据
         */
        void loadData(List<NewsDetailBean.ItemBean> itemBeanList);

        /**
         * 加载顶部banner数据
         *
         * @param newsDetailBean
         */
        void loadBannerData(NewsDetailBean newsDetailBean);

        /**
         * 加载置顶新闻数据
         */
        void loadTopNewsData(NewsDetailBean newsDetailBean);


        /**
         * 加载更多新闻数据
         */
        void loadMoreData(List<NewsDetailBean.ItemBean> itemBeanList);


        void showToast(String s);

    }

    interface Presenter {
        void getData( String id,  String action, int pullNum);
    }
}
