package com.example.rh.newsapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author RH
 * @date 2018/4/9
 */
public class BingBean {
    @SerializedName("images")
    private List<BingDailyBean> bingDailyBeans;

    public List<BingDailyBean> getBingDailyBeans() {
        return bingDailyBeans;
    }
}
