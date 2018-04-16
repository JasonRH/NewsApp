package com.example.rh.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.test.espresso.core.internal.deps.guava.util.concurrent.ThreadFactoryBuilder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rh.newsapp.R;
import com.example.rh.newsapp.activity.ShowImageActivity;
import com.example.rh.newsapp.model.PhotoArticleBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.jzvd.JZVideoPlayerStandard;


/**
 * @author RH
 * @date 2018/3/5
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    private List<PhotoArticleBean.Data> dataList = new ArrayList<>();
    private final int SINGLE_PICTURE = 1;
    private final int MULTIPLE_PICTURE = 2;
    private Context context;


    class MyViewHolder extends RecyclerView.ViewHolder {
        int viewType;
        TextView content;
        ImageView userImage;
        TextView textView;
        TextView diggCount;
        TextView buryCount;
        TextView commentsCount;
        ImageView diggImage;
        ImageView buryImage;

        ImageView imageView;
        JZVideoPlayerStandard jzVideoPlayerStandard;
        SimpleDraweeView simpleDraweeView;

        RecyclerView recyclerView;

        private MyViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            content = itemView.findViewById(R.id.photo_item_content);
            userImage = itemView.findViewById(R.id.photo_item_user_image);
            textView = itemView.findViewById(R.id.photo_item_user_name);
            diggCount = itemView.findViewById(R.id.photo_item_digg_count);
            buryCount = itemView.findViewById(R.id.photo_item_bury_count);
            commentsCount = itemView.findViewById(R.id.photo_item_comment_count);
            diggImage = itemView.findViewById(R.id.photo_item_digg_image);
            buryImage = itemView.findViewById(R.id.photo_item_bury_image);
            if (viewType == SINGLE_PICTURE) {
                imageView = itemView.findViewById(R.id.photo_item_image);
                jzVideoPlayerStandard = itemView.findViewById(R.id.photo_item_video);
                simpleDraweeView = itemView.findViewById(R.id.photo_item_simpleDraweeView);
            } else if (viewType == MULTIPLE_PICTURE) {
                recyclerView = itemView.findViewById(R.id.photo_item_multiple_recycle_view);
            }

        }

    }


    public PhotoAdapter(List<PhotoArticleBean.Data> list) {
        dataList = list;
    }


    @Override
    public int getItemViewType(int position) {
        PhotoArticleBean.Data data = dataList.get(position);
        if (data.getGroup() != null && data.getGroup().getThumb_image_list() != null) {
            return MULTIPLE_PICTURE;
        } else {
            return SINGLE_PICTURE;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        if (viewType == SINGLE_PICTURE) {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_photo_item, parent, false);
            return new MyViewHolder(view, viewType);
        } else if (viewType == MULTIPLE_PICTURE) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_multiple_photo_item, parent, false), viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final PhotoArticleBean.Data data = dataList.get(position);
        if (data.getGroup() != null) {
 /*公共操作*/
            /*作者信息*/
                Glide.with(context).load(data.getGroup().getUser().getAvatar_url()).into(holder.userImage);
                holder.textView.setText(data.getGroup().getUser().getName());
            /*文本内容*/
                SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
                int length = -2;
                if (data.getGroup().getCategory_name() != null) {
                    stringBuilder.append("#").append(data.getGroup().getCategory_name()).append("#");
                    length = data.getGroup().getCategory_name().length();
                }
                if (data.getGroup().getContent() != null) {
                    stringBuilder.append(data.getGroup().getContent());
                }
                stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#ea8baf")), 0, length + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.content.setText(stringBuilder);

        /*评论点赞*/
                holder.diggCount.setText(String.valueOf(data.getGroup().getDigg_count()));
                holder.buryCount.setText(String.valueOf(data.getGroup().getBury_count()));
                holder.commentsCount.setText(String.valueOf(data.getGroup().getComment_count()));

                holder.diggImage.setOnClickListener(v -> {
                    holder.diggImage.setImageResource(R.mipmap.digg2);
                    holder.diggCount.setText(String.valueOf(data.getGroup().getDigg_count() + 1));

                });
                holder.buryImage.setOnClickListener(v -> {
                    holder.buryImage.setImageResource(R.mipmap.bury2);
                    holder.buryCount.setText(String.valueOf(data.getGroup().getBury_count() + 1));
                });



/*根据不同的布局进行不同的操作*/
            if (holder.viewType == SINGLE_PICTURE) {
                /*单加载视频or图片*/
                /*视频*/
                if (data.getGroup().getGifvideo() != null && data.getGroup().getGifvideo().getP_video().getUrl_list().get(0).getUrl() != null) {
                    holder.jzVideoPlayerStandard.setVisibility(View.VISIBLE);
                    holder.imageView.setVisibility(View.GONE);
                    holder.simpleDraweeView.setVisibility(View.GONE);
                    String url = data.getGroup().getGifvideo().getP_video().getUrl_list().get(0).getUrl();
                    holder.jzVideoPlayerStandard.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_LIST, "");
                    Glide.with(context).load(url).into(holder.jzVideoPlayerStandard.thumbImageView);
                }
                /*单张图片*/
                else {
                    if (data.getGroup().getLarge_image() != null && data.getGroup().getLarge_image().getUrl_list().get(0) != null) {
                        final String url = data.getGroup().getLarge_image().getUrl_list().get(0).getUrl();

                        if ("爆笑GIF".equals(data.getGroup().getCategory_name())) {
                            holder.jzVideoPlayerStandard.setVisibility(View.GONE);
                            holder.imageView.setVisibility(View.GONE);
                            holder.simpleDraweeView.setVisibility(View.VISIBLE);

                            Uri uri = Uri.parse(url);
                            holder.simpleDraweeView.setImageURI(uri);
                            //Glide.with(context).load(url).into(holder.jzVideoPlayerStandard.thumbImageView);
                            /*holder.jzVideoPlayerStandard.setVisibility(View.GONE);
                            holder.imageView.setVisibility(View.VISIBLE);
                            Glide.with(context).load(url).into(holder.imageView);*/
                        } else {
                            holder.simpleDraweeView.setVisibility(View.GONE);
                            holder.jzVideoPlayerStandard.setVisibility(View.GONE);
                            holder.imageView.setVisibility(View.VISIBLE);
                            Glide.with(context).load(url).into(holder.imageView);
                        }

                        holder.imageView.setOnClickListener(v -> {
                            Intent intent = new Intent(context, ShowImageActivity.class);
                            intent.putExtra("imageUrl", url);
                            context.startActivity(intent);
                        });
                    } else {
                        //  holder.imageView.setVisibility(View.GONE);
                    }
                }

            } else if (holder.viewType == MULTIPLE_PICTURE) {
                /*加载多图片*/
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
                holder.recyclerView.setLayoutManager(gridLayoutManager);
                holder.recyclerView.setAdapter(new PhotoMultipleAdapter(data.getGroup().getThumb_image_list(), data.getGroup().getCategory_name()));

            }

        }
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
