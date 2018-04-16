package com.example.rh.newsapp.module.home.joke;

import com.example.rh.newsapp.model.JokeBean;

import java.util.List;

/**
 * @author RH
 * @date 2018/3/31
 */
public interface IJokeArticle {

    interface View {

        void onUpdateUI(List<JokeBean.JokeDataDataEntity> list);

        void stopLoading();

        void showToast(String s);

    }

    interface Presenter {

        void loadData();

    }
}
