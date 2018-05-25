package com.example.rh.newsapp.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.rh.newsapp.R;
import com.example.rh.newsapp.model.SettingItem;

import java.util.List;

/**
 * @author RH
 * @date 2018/5/24
 */
public class SettingAdapter extends BaseQuickAdapter<SettingItem, BaseViewHolder> {

    public SettingAdapter(int setting_adapter_item, List<SettingItem> data) {
        super(setting_adapter_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SettingItem item) {
        if (item.getTitle() != null) {
            helper.setText(R.id.setting_adapter_item_tv, item.getTitle())
                    .setImageResource(R.id.setting_adapter_item_img, item.getImgUrl());
        }
    }

}
