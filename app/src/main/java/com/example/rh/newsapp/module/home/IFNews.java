package com.example.rh.newsapp.module.home;

import com.example.rh.newsapp.model.NewsDetail;

import java.util.List;

/**
 * @author RH
 * @date 2018/4/12
 */
public interface IFNews {
    interface View {
        /**
         * 加载新闻数据
         *
         * @param itemBeanList
         */
        void loadData(List<NewsDetail.ItemBean> itemBeanList);

        /**
         * 加载顶部banner数据
         *
         * @param newsDetail
         */
        void loadBannerData(NewsDetail newsDetail);

        /**
         * 加载置顶新闻数据
         *
         * @param newsDetail
         */
        void loadTopNewsData(NewsDetail newsDetail);


        /**
         * 加载更多新闻数据
         *
         * @param itemBeanList
         */
        void loadMoreData(List<NewsDetail.ItemBean> itemBeanList);


        void showToast(String s);

    }

    interface Presenter {
        void getData( String id,  String action, int pullNum);
    }
}
