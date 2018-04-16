package com.example.rh.newsapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rh.newsapp.R;
import com.example.rh.newsapp.model.ToutiaoNewsBean;

import java.util.List;

/**
 * @author RH
 * @date 2018/4/11
 */
public class TouTiaoAdapter extends RecyclerView.Adapter<TouTiaoAdapter.NewsViewHolder> {
    private static final String TAG = "TouTiaoAdapter";
    private List<ToutiaoNewsBean.DataBean> dataBeanList;
    private Context context;

    public TouTiaoAdapter(List<ToutiaoNewsBean.DataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_toutiao_news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        ToutiaoNewsBean.DataBean dataBean = dataBeanList.get(position);

        if (dataBean.getTitle() != null) {
            holder.content.setText(dataBean.getTitle());
        }
        holder.media_name.setText(dataBean.getMedia_name());
        holder.comment_count.setText(String.valueOf(dataBean.getComment_count()));
        holder.news_datetime.setText(dataBean.getDatetime());

        if (dataBean.getImage_url() != null) {
            holder.news_image.setVisibility(View.VISIBLE);
            Glide.with(context).load(dataBean.getImage_url()).into(holder.news_image);
        }else {
            holder.news_image.setVisibility(View.GONE);
        }
        if (dataBean.getImage_list() != null && dataBean.getImage_list().size() != 0) {
            holder.news_images.setVisibility(View.VISIBLE);
            Glide.with(context).load(dataBean.getImage_list().get(0).getUrl()).into(holder.news_images_0);
            Glide.with(context).load(dataBean.getImage_list().get(1).getUrl()).into(holder.news_images_1);
            Glide.with(context).load(dataBean.getImage_list().get(2).getUrl()).into(holder.news_images_2);
        } else {
            holder.news_images.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        private TextView content;
        private TextView media_name;
        private TextView comment_count;
        private TextView news_datetime;
        private ImageView news_image;
        private LinearLayout news_images;
        private ImageView news_images_0;
        private ImageView news_images_1;
        private ImageView news_images_2;


        public NewsViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.toutiao_news_content);
            media_name = itemView.findViewById(R.id.toutiao_news_media_name);
            comment_count = itemView.findViewById(R.id.toutiao_news_comment_count);
            news_datetime = itemView.findViewById(R.id.toutiao_news_datetime);
            news_image = itemView.findViewById(R.id.toutiao_news_image);
            news_images = itemView.findViewById(R.id.toutiao_news_images);
            news_images_0 = itemView.findViewById(R.id.toutiao_news_images_0);
            news_images_1 = itemView.findViewById(R.id.toutiao_news_images_1);
            news_images_2 = itemView.findViewById(R.id.toutiao_news_images_2);
        }
    }
}
