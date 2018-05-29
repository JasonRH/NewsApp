package com.example.rh.newsapp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.rh.newsapp.R;
import com.example.rh.newsapp.adapter.MyFileAdapter;
import com.example.rh.newsapp.utils.FileUtils;
import com.example.rh.newsapp.utils.MyToast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RH
 * @date 2018/5/25
 */
public class OpenFileActivity extends AppCompatActivity {
    private final String PATH = "/sdcard";
    private MyFileAdapter adapter;
    List<File> files = new ArrayList<>();
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openfile);
        //Android6.0以后获取读取SDcard的权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(OpenFileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(OpenFileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                initView();
            }
        } else {
            initView();
        }
    }

    private void initView() {
        textView = findViewById(R.id.activity_openFile_text);

        RecyclerView recyclerView = findViewById(R.id.activity_openFile_dirLists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyFileAdapter(this, files);
        recyclerView.setAdapter(adapter);

        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //添加头布局
        View view = LayoutInflater.from(this).inflate(R.layout.adapter_file_head, null, false);
        //头布局点击事件，点击返回上一级目录
        adapter.addHeaderView(view);
        view.findViewById(R.id.adapter_file_item_relativeLayout).setOnClickListener(v -> {
            if (!"".equals(FileUtils.currPath) && !PATH.equals(FileUtils.currPath)) {
                File f = new File(FileUtils.currPath);
                textView.setText(f.getParent());
                files.clear();
                files.addAll(FileUtils.scanFiles(f.getParent()));
                adapter.notifyDataSetChanged();
            }
        });

        //item点击事件，点击进入下级子目录
        adapter.setOnItemClickListener((adapter, view1, position) -> {
            //在此处做点击之后的逻辑处理
            File f = files.get(position);
            if (f.isDirectory()) {
                textView.setText(f.getPath());
                files.clear();
                files.addAll(FileUtils.scanFiles(f.getPath()));
                adapter.notifyDataSetChanged();
            } else {
                FileUtils.openFile(f.getPath(), f.getName().toLowerCase(), view1.getContext());
            }
        });
        //更新文件目录
        files.clear();
        files.addAll(FileUtils.scanFiles(PATH));
        textView.setText(FileUtils.currPath);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    MyToast.show("拒绝权限，无法访问存储空间！");
                    finish();
                } else {
                    initView();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        if (adapter != null) {
            adapter.recycleBitmap();
        }
        super.onDestroy();
    }
}
