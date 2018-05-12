package com.example.rh.newsapp.module.video;

import com.example.rh.newsapp.model.VideoChannelBean;
import com.example.rh.newsapp.model.VideoDetailBean;

import java.util.List;

/**
 * @author RH
 * @date 2018/5/7
 */
public interface IFVideo {
    interface View {
        /**
         * 加载视频频道列表
         */
        void loadVideoChannel(List<VideoChannelBean> channelBean);

        void loadMoreVideoDetails(List<VideoDetailBean> videoDetailBean);

        void loadVideoDetails(List<VideoDetailBean> videoDetailBean);
    }

    interface Presenter {
        /**
         * 获取视频频道列表
         */
        void getVideoChannel();

        /**
         * 获取视频列表
         *
         * @param page     页码
         * @param listType 默认list
         * @param typeId   频道id
         */
        void getVideoDetails(int page, String listType, String typeId);
    }
}
