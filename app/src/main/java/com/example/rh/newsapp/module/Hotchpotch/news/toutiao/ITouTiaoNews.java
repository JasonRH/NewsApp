package com.example.rh.newsapp.module.Hotchpotch.news.toutiao;

import com.example.rh.newsapp.model.ToutiaoNewsBean;

import java.util.List;

/**
 * @author RH
 * @date 2018/4/11
 */
public interface ITouTiaoNews {
    interface View {

        void onUpdateUI(List<ToutiaoNewsBean.DataBean> list);

        void stopLoading();

        void showToast(String s);

    }

    interface Presenter {

        void loadData();

    }
}
