package com.example.rh.newsapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author RH
 * @date 2018/1/18
 */

public class JokeBean {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private JokeDataEntity data;

    public JokeDataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public class JokeDataEntity {

        @SerializedName("tip")
        private String tip;
        @SerializedName("has_more")
        private boolean has_more;
        @SerializedName("data")
        private List<JokeDataDataEntity> data;

        public String getTip() {
            return tip;
        }

        public boolean isHas_more() {
            return has_more;
        }

        public List<JokeDataDataEntity> getData() {
            return data;
        }

    }

    public class JokeDataDataEntity {
        @SerializedName("group")
        private JokeDataDataGroupEntity group;

        public JokeDataDataGroupEntity getGroup() {
            return group;
        }
    }

    public class JokeDataDataGroupEntity {
        @SerializedName("group_id")
        private String group_id;
        /**
         * 时间
         */
        @SerializedName("create_time")
        private long create_time;
        /**
         * 段子内容
         */
        @SerializedName("content")
        private String content;
        /**
         * 分享链接
         */
        @SerializedName("share_url")
        private String share_url;
        /**
         * 分类名称
         */
        @SerializedName("category_name")
        private String category_name;

        /**
         * 评论数
         */
        @SerializedName("comment_count")
        private String comment_count;

        /**
         * 顶
         */
        @SerializedName("digg_count")
        private String digg_count;

        /**
         * 踩
         */
        @SerializedName("bury_count")
        private String bury_count;
        /**
         * 转发数
         */
        @SerializedName("repin_count")
        private String repin_count;

        @SerializedName("favorite_count")
        private String favorite_count;
        /**
         * 作者信息
         */
        @SerializedName("user")
        JokeDataGroupArrayUser user;

        public class JokeDataGroupArrayUser {
            /**
             * 作者名称
             */
            @SerializedName("name")
            private String name;
            /**
             * 作者头像
             */
            @SerializedName("avatar_url")
            private String avatar_url;

            public String getName() {
                return name;
            }

            public String getAvatar_url() {
                return avatar_url;
            }
        }

        public long getCreate_time() {
            return create_time;
        }

        public String getContent() {
            return content;
        }

        public String getShare_url() {
            return share_url;
        }

        public String getCategory_name() {
            return category_name;
        }

        public String getComment_count() {
            return comment_count;
        }

        public String getDigg_count() {
            return digg_count;
        }

        public String getBury_count() {
            return bury_count;
        }

        public JokeDataGroupArrayUser getUser() {
            return user;
        }

        public String getGroup_id() {
            return group_id;
        }

        @Override
        public String toString() {
            return "JokeDataDataGroupEntity{" +
                    "create_time=" + create_time +
                    ", content='" + content + '\'' +
                    ", share_url='" + share_url + '\'' +
                    ", category_name='" + category_name + '\'' +
                    ", comment_count=" + comment_count +
                    ", digg_count=" + digg_count +
                    ", bury_count=" + bury_count +
                    ", repin_count=" + repin_count +
                    ", favorite_count=" + favorite_count +
                    ", user=" + user +
                    '}';
        }
    }

}


