package com.example.rh.newsapp.network.api;

import com.example.rh.newsapp.model.BingBean;
import com.example.rh.newsapp.model.DailyArticleBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author RH
 * @date 2018/4/9
 */
public interface BingPictureService {
    /**必应图片API*/
    String BING_PICTURE_URL = "http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=" + 8;
    /**每日一文API*/
    //String DAILY_CONTENT_URL = "https://interface.meiriyiwen.com/article/day?dev=1&date=";
    String DAILY_CONTENT_URL = "https://interface.meiriyiwen.com/article/day?dev=1&";

    @GET(BING_PICTURE_URL)
    Observable<BingBean> getPicture();

    @GET(DAILY_CONTENT_URL)
    Observable<DailyArticleBean> getDailyContent(@Query("date") String date);
}
