package com.example.rh.newsapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author RH
 * @date 2018/3/1
 */
public class PhotoArticleBean {
    private String message;
    private BaseData data;

    public String getMessage() {
        return message;
    }

    public BaseData getData() {
        return data;
    }

    public class BaseData {
        private boolean has_more;
        private String tip;
        private boolean has_new_message;
        public List<Data> data;

        public boolean isHas_more() {
            return has_more;
        }

        public String getTip() {
            return tip;
        }

        public List<Data> getDataList() {
            return data;
        }

    }

    public class Data {
        /**
         * {
         * "group": {.....},
         * "comments": [],
         * "type": 1,
         * "display_time": 1519974336.99,
         * "online_time": 1519974336.99
         * },
         */
        private Group group;
        private int type;
        /*** 对应的数据类型一定要正确，否则解析出错,数据解析不错来*/
        private List<Object> comments;

        public Group getGroup() {
            return group;
        }
    }

    public class Group {
        private User user;
        private LargeImage large_image;
        private MiddleImage middle_image;
        private List<ThumbImage> thumb_image_list;
        private GifVideo gifvideo;
        private String text;
        private double create_time;
        private int favorite_count;
        private int is_can_share;
        private String share_url;
        private String content;
        private int comment_count;
        private String status_desc;
        private String category_name;
        private int bury_count;
        private int digg_count;
        private int share_count;

        public String getContent() {
            return content;
        }


        public String getText() {
            return text;
        }

        public double getCreate_time() {
            return create_time;
        }

        public int getFavorite_count() {
            return favorite_count;
        }

        public int getIs_can_share() {
            return is_can_share;
        }

        public String getShare_url() {
            return share_url;
        }

        public int getComment_count() {
            return comment_count;
        }

        public String getStatus_desc() {
            return status_desc;
        }

        public String getCategory_name() {
            return category_name;
        }

        public int getBury_count() {
            return bury_count;
        }

        public int getDigg_count() {
            return digg_count;
        }

        public int getShare_count() {
            return share_count;
        }

        public LargeImage getLarge_image() {
            return large_image;
        }

        public User getUser() {
            return user;
        }

        public MiddleImage getMiddle_image() {
            return middle_image;
        }

        public GifVideo getGifvideo() {
            return gifvideo;
        }

        public List<ThumbImage> getThumb_image_list() {
            return thumb_image_list;
        }
    }

    public class ThumbImage {
        private String url;

        public String getUrl() {
            return url;
        }
    }

    public class LargeImage {
        private int width;
        private int r_height;
        private int r_width;
        private List<MiddleImage.Url> url_list;

        public List<MiddleImage.Url> getUrl_list() {
            return url_list;
        }

        public int getR_height() {
            return r_height;
        }

        public int getR_width() {
            return r_width;
        }

        public class Url {
            private String url;

            public String getUrl() {
                return url;
            }
        }
    }

    public class MiddleImage {
        private int width;
        private int r_height;
        private int r_width;
        private List<Url> url_list;

        public List<Url> getUrl_list() {
            return url_list;
        }

        public int getR_height() {
            return r_height;
        }

        public int getR_width() {
            return r_width;
        }

        public class Url {
            private String url;

            public String getUrl() {
                return url;
            }
        }

    }

    public class GifVideo {
        @SerializedName("360p_video")
        private Video p_video;

        public Video getP_video() {
            return p_video;
        }
    }

    public class Video {
        private List<VideoUrl> url_list;

        public List<VideoUrl> getUrl_list() {
            return url_list;
        }
    }

    public class VideoUrl {
        private String url;

        public String getUrl() {
            return url;
        }
    }

    public class User {
        private String name;
        private String avatar_url;

        public String getName() {
            return name;
        }

        public String getAvatar_url() {
            return avatar_url;
        }
    }


}
