package com.example.rh.newsapp.network.api;

import com.example.rh.newsapp.model.NewsDetail;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author RH
 * @date 2018/4/13
 */
public interface IFService {

    @GET(IFApi.IFengApi + "ClientNews")
    Observable<List<NewsDetail>> getNewsDetail(@Query("id") String id,
                                               @Query("action") String action,
                                               @Query("pullNum") int pullNum);
}
