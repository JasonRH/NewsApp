package com.example.rh.newsapp.network.api;

import com.example.rh.newsapp.model.ToutiaoNewsBean;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * @author RH
 * @date 2018/4/11
 */
public interface TouTiaoService {

    /**今日头条API*/
    @Headers({
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
            "Accept-Encoding: gzip, deflate",
            "Accept-Language: zh-CN,zh;q=0.9",
            "Cache-Control: max-age=0",
            "Connection: keep-alive",
            //"Cookie: UM_distinctid=16107e3fe1e128-0faf98763e9b5f-3c604504-140000-16107e3fe1f883; tt_webid=6512268961428637197",
            "Host: m.toutiao.com",
            "Upgrade-Insecure-Requests: 1",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36"
    })
    @GET("http://m.toutiao.com/list/?tag=__all__&ac=wap&count=20&format=json_raw&as=A17538D54D106FF&cp=585DF0A65F0F1E1&min_behot_time=1482491618")
    Observable<ToutiaoNewsBean> getNews();

}
