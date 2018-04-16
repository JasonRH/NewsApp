package com.example.rh.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rh.newsapp.R;
import com.example.rh.newsapp.activity.ShowImageActivity;
import com.example.rh.newsapp.model.PhotoArticleBean;
import com.example.rh.newsapp.utils.ScreenUtils;


import java.util.List;

/**
 * @author RH
 * @date 2018/3/12
 */
public class PhotoMultipleAdapter extends RecyclerView.Adapter<PhotoMultipleAdapter.PictureViewHolder> {
    private Context context;
    private List<PhotoArticleBean.ThumbImage> thumbImageList;
    String category_name;


    public PhotoMultipleAdapter(List<PhotoArticleBean.ThumbImage> thumbImageList, String category_name) {
        this.thumbImageList = thumbImageList;
        this.category_name = category_name;
    }

    class PictureViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        private PictureViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photo_item_multiple_recycle_view_item_image);
            setImageViewSize(imageView);
        }
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_multiple_photo_item_item, parent, false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureViewHolder holder,  int position) {
        final String imgUrl = thumbImageList.get(position).getUrl();
        Glide.with(context).load(imgUrl).into(holder.imageView);
        holder.imageView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ShowImageActivity.class);
                intent.putExtra("imageUrl", imgUrl);
                context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return thumbImageList.size();
    }

    private void setImageViewSize(ImageView imageView) {
        ViewGroup.LayoutParams lp= imageView.getLayoutParams();
       //将宽度设置为屏幕的1/3
        lp.width= ScreenUtils.getScreenWidth()/3-15;
        imageView.setLayoutParams(lp);
    }

}
