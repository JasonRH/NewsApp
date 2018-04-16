package com.example.rh.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rh.newsapp.R;
import com.example.rh.newsapp.activity.WebActivity;
import com.example.rh.newsapp.model.JokeBean;
import com.example.rh.newsapp.utils.DateUtils;
import com.example.rh.newsapp.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RH
 * @date 2018/1/18
 */
public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHold> {
    private Context context;
    private List<JokeBean.JokeDataDataEntity> jokeDataDataEntityList = new ArrayList<>();
    private int lastPosition = -1;

    public JokeAdapter(List<JokeBean.JokeDataDataEntity> jokeDataDataGroupEntityList) {
        this.jokeDataDataEntityList = jokeDataDataGroupEntityList;
    }

    class ViewHold extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView ivUserIcon;
        TextView tvAuthor;
        TextView tvContent;
        TextView tvCategory;
        TextView tvTime;
        TextView tvDiggCount;
        TextView tvBurryCount;
        TextView tvCommentCount;
        ImageView imgShareLink;
        LinearLayout linearLayout;

        private ViewHold(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.joke_adapter_item_card_view);
            ivUserIcon = itemView.findViewById(R.id.joke_adapter_item_iv_user);
            tvAuthor = itemView.findViewById(R.id.joke_adapter_item_tv_author);
            tvContent = itemView.findViewById(R.id.joke_adapter_item_tv_content);
            tvCategory = itemView.findViewById(R.id.joke_adapter_item_tv_category);
            tvTime = itemView.findViewById(R.id.joke_adapter_item_tv_time);
            tvDiggCount = itemView.findViewById(R.id.joke_adapter_item_tv_like);
            tvBurryCount = itemView.findViewById(R.id.joke_adapter_item_tv_unlike);
            tvCommentCount = itemView.findViewById(R.id.joke_adapter_item_tv_comment_count);
            imgShareLink = itemView.findViewById(R.id.joke_adapter_item_img_share);
            linearLayout = itemView.findViewById(R.id.joke_adapter_item_linearLayout);
        }
    }


    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.joke_adapter_item, parent, false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(ViewHold holder, int position) {
        JokeBean.JokeDataDataEntity jokeDataDataEntity = jokeDataDataEntityList.get(position);
        if (null != jokeDataDataEntity.getGroup()) {
            Glide.with(context).load(jokeDataDataEntity.getGroup().getUser().getAvatar_url()).into(holder.ivUserIcon);
            holder.tvAuthor.setText(jokeDataDataEntity.getGroup().getUser().getName());
            holder.tvContent.setText(jokeDataDataEntity.getGroup().getContent());
            holder.tvCategory.setText(String.format("#%s", jokeDataDataEntity.getGroup().getCategory_name()));
            holder.tvTime.setText(DateUtils.millis2CurrentTime(jokeDataDataEntity.getGroup().getCreate_time()));
            holder.tvDiggCount.setText(jokeDataDataEntity.getGroup().getDigg_count());
            holder.tvBurryCount.setText(jokeDataDataEntity.getGroup().getBury_count());
            holder.tvCommentCount.setText(jokeDataDataEntity.getGroup().getComment_count());
            //holder.imgShareLink.setOnClickListener(v -> {});

            holder.linearLayout.setOnClickListener(v -> {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("joke_url", "http://m.neihanshequ.com/group/" + jokeDataDataEntity.getGroup().getGroup_id());
                intent.putExtra("joke_content", jokeDataDataEntity.getGroup().getContent());
                context.startActivity(intent);
            });

        } else {
            MyToast.show("加载中......");
        }

        setAnimation(holder.cardView, position);
    }

    protected void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.adapter_item_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHold holder) {
        super.onViewDetachedFromWindow(holder);
        //一定要清除每个item的动画，否者快速滑动的时候，动画不会回收会出现重叠背景，出现卡屏现象。
        holder.cardView.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return jokeDataDataEntityList.size();
    }
}
