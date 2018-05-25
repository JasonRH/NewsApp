package com.example.rh.newsapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.rh.newsapp.R;
import com.example.rh.newsapp.adapter.SettingAdapter;
import com.example.rh.newsapp.model.SettingItem;
import com.example.rh.newsapp.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RH
 * @date 2018/5/17
 */
public class SettingFragment extends Fragment {

    public static SettingFragment settingFragment;
    private List<SettingItem> data;
    String[] titles = {"文件管理", "夜间模式", "关于"};
    int[] imgUrls = {R.drawable.ic_sd_storage, R.drawable.ic_brightness_2, R.drawable.ic_smartphone};

    public static SettingFragment getInstance() {
        if (settingFragment == null) {
            settingFragment = new SettingFragment();
        }
        return settingFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            SettingItem item = new SettingItem();
            item.setTitle(titles[i]);
            item.setImgUrl(imgUrls[i]);
            data.add(item);
        }
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.setting_fragment_recycle);
        SettingAdapter adapter = new SettingAdapter(R.layout.setting_adapter_item, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        RelativeLayout layout = view.findViewById(R.id.setting_clear);
        TextView cache = view.findViewById(R.id.setting_cache);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        MyToast.show("功能开发中，敬请关注！");
                        break;
                    case 1:
                        MyToast.show("功能开发中，敬请关注！");
                        break;
                    case 2:
                        MyToast.show("功能开发中，敬请关注！");
                        break;
                    default:
                        break;
                }
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.show("开发中......");
            }
        });

    }
}
