package com.example.rh.newsapp.module.home.bing;

import com.example.rh.newsapp.model.BingDailyBean;

import java.util.List;

/**
 * @author RH
 * @date 2018/4/9
 */
public interface IBing {
    interface View {

        void onUpdateUI(List<BingDailyBean> list);

        void stopLoading();

        void showToast(String s);

    }

    interface Presenter {

        void loadData();

    }
}
