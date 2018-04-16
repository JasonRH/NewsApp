package com.example.rh.newsapp.model;

/**
 *
 * @author RH
 * @date 2017/12/14
 */

public class DailyArticleBean {
    public String author;
    private String title;
    public String content;

    public DailyArticleBean(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
