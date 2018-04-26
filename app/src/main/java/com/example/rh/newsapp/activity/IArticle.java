package com.example.rh.newsapp.activity;

import com.example.rh.newsapp.model.NewsArticleBean;

/**
 * @author RH
 * @date 2018/4/21
 */
public interface IArticle {
    interface View {
        void loadData(NewsArticleBean articleBean);
    }

    interface Presenter {
        void getData(String url);
    }
}
