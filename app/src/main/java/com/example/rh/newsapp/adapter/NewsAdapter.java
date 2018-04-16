package com.example.rh.newsapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rh.newsapp.R;
import com.example.rh.newsapp.model.News360Bean;
import com.example.rh.newsapp.model.ToutiaoNewsBean;

import java.util.List;

/**
 * @author RH
 * @date 2018/4/11
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<News360Bean.DataBean> dataBeanList;
    private Context context;

    public NewsAdapter(List<News360Bean.DataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        News360Bean.DataBean dataBean = dataBeanList.get(position);

        if (dataBean.getTitle() != null) {
            holder.content.setText(dataBean.getTitle());
        }
        holder.author_name.setText(dataBean.getPosterScreenName());
        holder.comment_count.setText("");
        holder.news_datetime.setText(dataBean.getPublishDateStr());

        if (dataBean.getImageUrls() != null&&dataBean.getImageUrls().size() != 0) {

            if (dataBean.getImageUrls().size() == 1) {
                holder.news_image.setVisibility(View.VISIBLE);
                holder.news_images.setVisibility(View.GONE);
                Glide.with(context).load(dataBean.getImageUrls()).into(holder.news_image);
            } else {
                holder.news_images.setVisibility(View.VISIBLE);
                holder.news_image.setVisibility(View.GONE);
                Glide.with(context).load(dataBean.getImageUrls().get(0)).into(holder.news_images_0);
                Glide.with(context).load(dataBean.getImageUrls().get(1)).into(holder.news_images_1);
                Glide.with(context).load(dataBean.getImageUrls().get(2)).into(holder.news_images_2);
            }

        }else {
            holder.news_image.setVisibility(View.GONE);
            holder.news_images.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        private TextView content;
        private TextView author_name;
        private TextView comment_count;
        private TextView news_datetime;
        private ImageView news_image;
        private LinearLayout news_images;
        private ImageView news_images_0;
        private ImageView news_images_1;
        private ImageView news_images_2;


        public NewsViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.news_content);
            author_name = itemView.findViewById(R.id.news_author_name);
            comment_count = itemView.findViewById(R.id.news_comment_count);
            news_datetime = itemView.findViewById(R.id.news_datetime);
            news_image = itemView.findViewById(R.id.news_image);
            news_images = itemView.findViewById(R.id.news_images);
            news_images_0 = itemView.findViewById(R.id.news_images_0);
            news_images_1 = itemView.findViewById(R.id.news_images_1);
            news_images_2 = itemView.findViewById(R.id.news_images_2);
        }
    }
}
