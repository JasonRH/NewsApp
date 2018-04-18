package com.example.rh.newsapp.network.api;


import com.example.rh.newsapp.model.News360Bean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author RH
 * @date 2018/4/11
 */
public interface NewsService {

    /** idata API*/

    @GET("http://120.76.205.241:8000/news/qihoo?kw=%E7%83%AD%E7%82%B9&site=qq.com&apikey=QS6LOhmJ5V7tqpF0eOgPEQnWSanzdJdOiXamXjQ9yHN468xSIvDJPQ5biHuHzwQL")
    Observable<News360Bean> getNews();

}
