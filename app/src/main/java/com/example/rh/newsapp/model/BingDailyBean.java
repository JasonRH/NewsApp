package com.example.rh.newsapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 *
 * @author RH
 * @date 2017/12/13
 */

public class BingDailyBean implements Serializable {
    @SerializedName("startdate")
    public String date;
    private String url;
    private String copyright;

    public BingDailyBean(String date, String url) {
        this.date = date;
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return "https://www.bing.com"+url;
    }

    public String getCopyright() {
        return copyright;
    }
}
