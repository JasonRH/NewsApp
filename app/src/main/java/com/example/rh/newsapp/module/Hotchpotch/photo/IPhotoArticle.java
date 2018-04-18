package com.example.rh.newsapp.module.Hotchpotch.photo;



import com.example.rh.newsapp.model.PhotoArticleBean;

import java.util.List;

/**
 * @author RH
 */
public interface IPhotoArticle {

    interface View {

        void onUpdateUI(List<PhotoArticleBean.Data> list);

        void stopLoading();

        void showToast(String s);

    }

    interface Presenter {

        void loadData();

    }
}
