package com.example.rh.newsapp.model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * @author RH
 * @date 2018/4/13
 */
public class Channel extends DataSupport implements Serializable {
    private String channelId;
    private String channelName;
    private boolean isChannelSelect;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }


    public boolean isChannelSelect() {
        return isChannelSelect;
    }

    public void setChannelSelect(boolean channelSelect) {
        isChannelSelect = channelSelect;
    }

}
