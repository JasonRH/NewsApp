package com.example.rh.newsapp.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.rh.newsapp.R;
import com.example.rh.newsapp.adapter.ChannelAdapter;
import com.example.rh.newsapp.database.ChannelDao;
import com.example.rh.newsapp.listener.ItemDragHelperCallback;
import com.example.rh.newsapp.model.Channel;
import com.example.rh.newsapp.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RH
 */
public class ChannelActivity extends AppCompatActivity {
    private List<Channel> selectedChannels = new ArrayList<>();
    private List<Channel> unselectedChannels = new ArrayList<>();
    private ChannelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //显示左上角的返回图标,并可点击
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initData();
        initView();
    }

    private void initData() {
        List<Channel> channelList = ChannelDao.findAll();
        for (Channel channel : channelList) {
            if (channel.isChannelSelect()) {
                selectedChannels.add(channel);
            } else {
                unselectedChannels.add(channel);
            }
        }
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recy);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        adapter = new ChannelAdapter(this, helper, selectedChannels, unselectedChannels);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == ChannelAdapter.TYPE_MY || viewType == ChannelAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        recyclerView.setAdapter(adapter);

        adapter.setOnMyChannelItemClickListener((v, position) -> Toast.makeText(ChannelActivity.this, selectedChannels.get(position).getChannelName(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onBackPressed() {
        //super执行后就不会再执行后面的代码
        //super.onBackPressed();
        if (adapter.savaChannel()) {
            setResult(2);
            finish();
        } else {
            MyToast.show("频道保存失败");
            finish();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (adapter.savaChannel()) {
                    setResult(2);
                    finish();
                } else {
                    MyToast.show("频道保存失败");
                    finish();
                }
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}

