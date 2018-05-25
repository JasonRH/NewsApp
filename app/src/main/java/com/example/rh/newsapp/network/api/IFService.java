package com.example.rh.newsapp.network.api;

import com.example.rh.newsapp.model.NewsArticleBean;
import com.example.rh.newsapp.model.NewsDetailBean;
import com.example.rh.newsapp.model.VideoChannelBean;
import com.example.rh.newsapp.model.VideoDetailBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author RH
 * @date 2018/4/13
 */
public interface IFService {

    @GET(IFApi.IFengApi + "ClientNews")
    Observable<List<NewsDetailBean>> getNewsDetail(@Query("id") String id,
                                                   @Query("action") String action,
                                                   @Query("pullNum") int pullNum);


    @GET(IFApi.IFengApi+"api_vampire_article_detail")
    Observable<NewsArticleBean> getNewsArticleWithSub(@Query("aid") String aid);

    @GET
    Observable<NewsArticleBean> getNewsArticleWithCmpp(@Url String url,
                                                       @Query("aid") String aid);

    @GET(IFApi.IFengApi+"ifengvideoList")
    Observable<List<VideoChannelBean>> getVideoChannel(@Query("page") int page);

    @GET(IFApi.IFengApi+"ifengvideoList")
    Observable<List<VideoDetailBean>> getVideoDetail(@Query("page") int page,
                                                     @Query("listtype") String listtype,
                                                     @Query("typeid") String typeid);

}
