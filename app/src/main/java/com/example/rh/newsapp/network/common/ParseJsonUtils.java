package com.example.rh.newsapp.network.common;

import com.example.rh.newsapp.model.DailyArticleBean;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author RH
 * @date 2017/11/9
 */

public class ParseJsonUtils {

    /**
     * 使用GSON开源库来解析json
     */

    public static DailyArticleBean handleDailyArticle(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        String data = jsonObject.getString("data");
        Gson gson = new Gson();
        DailyArticleBean dailyArticleBean = gson.fromJson(data, DailyArticleBean.class);
        return dailyArticleBean;
    }

}