package com.example.rh.newsapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rh.newsapp.R;
import com.example.rh.newsapp.listener.HttpCallbackListener;
import com.example.rh.newsapp.model.BingDailyBean;
import com.example.rh.newsapp.model.DailyArticleBean;
import com.example.rh.newsapp.network.common.HttpUtils;
import com.example.rh.newsapp.service.DownloadService;
import com.example.rh.newsapp.utils.MyToast;
import com.example.rh.newsapp.network.common.ParseJsonUtils;
import com.example.rh.newsapp.widget.MyDialog;

import org.json.JSONException;

/**
 * @author RH
 * @date 2017/11/3
 */

public class PictureActivity extends AppCompatActivity {
    private static final String TAG = "PictureActivity";
    private TextView pictureContentText, author, title;
    private String words;
    private DailyArticleBean dailyArticleBeanBean = null;
    private String pictureImageId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        //设置toolbar替代原ActionBar
        setSupportActionBar(toolbar1);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //显示左上角的返回图标,并可点击
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        BingDailyBean bingDailyBean = (BingDailyBean) getIntent().getSerializableExtra("BingDaily_data");
        String pictureName = bingDailyBean.getDate();
        pictureImageId = bingDailyBean.getUrl();
        words = bingDailyBean.getCopyright();

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_tool);
        ImageView pictureImageView = findViewById(R.id.picture_image_view);
        author = findViewById(R.id.picture_author);
        title = findViewById(R.id.picture_title);
        pictureContentText = findViewById(R.id.picture_content_text);
        collapsingToolbar.setTitle(pictureName);
        Glide.with(this).load(pictureImageId).into(pictureImageView);
        //首次，无网，默认加载必应Copyright
        title.setText(words);
        //加载每日一文
        intContent(pictureName);

        //下载按钮点击事件
        findViewById(R.id.activity_picture_floatButton).setOnClickListener(v -> {

            MyDialog myDialog = new MyDialog(this);
            myDialog.setTitle("是否下载当前图片？");
            myDialog.setMessage("默认下载路径为：/sdcard/Download");
            myDialog.setYesOnclickListener("立即下载", () -> {
                //获取写入外部存储的权限
                if (ContextCompat.checkSelfPermission(PictureActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PictureActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    if (pictureImageId != null && !TextUtils.isEmpty(pictureImageId)) {
                        MyToast.show("图片后台下载中，请稍等......");
                        Intent download = new Intent(PictureActivity.this, DownloadService.class);
                        download.putExtra("url", pictureImageId);
                        startService(download);
                    }
                }
                myDialog.dismiss();
            });
            myDialog.setNoOnClickListener("取消", myDialog::dismiss);
            myDialog.show();

        });
    }

    /**
     * 获取每日一文
     */
    private void intContent(String date) {
        //此接口用okhttp3请求会出错,可能将 //connection.setDoOutput(true)
        String url = "https://interface.meiriyiwen.com/article/day?dev=1&date=" + date;
        HttpUtils.sendHttpRequest(url, new HttpCallbackListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish(String string) {
                try {
                    dailyArticleBeanBean = ParseJsonUtils.handleDailyArticle(string);
                    words = dailyArticleBeanBean.getContent();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(() -> {
                    title.setText(dailyArticleBeanBean.getTitle());
                    author.setText("—" + dailyArticleBeanBean.getAuthor());
                    //也可以使用Android富文本来加载Html格式的文本
                    pictureContentText.setText(Html.fromHtml(words));
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> MyToast.show("加载每日一文失败"));
                Log.e(TAG, "onError: " + e);
            }
        });

    }

    /**
     * 左上角返回图标的点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 权限申请回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    MyToast.show("权限拒绝，无法下载！");
                } else {
                    if (pictureImageId != null && !TextUtils.isEmpty(pictureImageId)) {
                        MyToast.show("权限已获取，开始下载图片......");
                        Intent download = new Intent(this, DownloadService.class);
                        download.putExtra("url", pictureImageId);
                        startService(download);
                    }
                }
                break;
            default:
        }
    }

}
