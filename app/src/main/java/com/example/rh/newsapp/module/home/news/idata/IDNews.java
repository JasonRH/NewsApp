package com.example.rh.newsapp.module.home.news.idata;

import com.example.rh.newsapp.model.News360Bean;

import java.util.List;

/**
 * @author RH
 * @date 2018/4/11
 */
public interface IDNews {
    interface View {

        void onUpdateUI(List<News360Bean.DataBean> list);

        void stopLoading();

        void showToast(String s);

    }

    interface Presenter {

        void loadData();

    }
}
