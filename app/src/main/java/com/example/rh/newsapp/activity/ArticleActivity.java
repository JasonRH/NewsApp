package com.example.rh.newsapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.rh.newsapp.R;
import com.example.rh.newsapp.base.BaseActivity;
import com.example.rh.newsapp.model.NewsArticleBean;
import com.example.rh.newsapp.utils.DateUtils;
import com.example.rh.newsapp.widget.ObservableScrollView;
import com.kennyc.view.MultiStateView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author RH
 * @date 2018/4/21
 */
public class ArticleActivity extends BaseActivity<ArticlePresenter> implements IArticle.View {
    private static final String TAG = "ArticleActivity";
    private CompositeDisposable disposable = new CompositeDisposable();
    private WebView webView;
    private MultiStateView multiStateView;
    private ObservableScrollView scrollView;

    private RelativeLayout relativeLayout;
    private RelativeLayout topRrelativeLayout;
    private ImageView back;
    private ImageView topLogo;
    private TextView topName;
    private TextView topCateName;
    private Button topButton;
    private TextView title;
    private ImageView logo;
    private TextView name;
    private TextView updateTime;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //StatusBarUtil.getStatusBarLightMode(getWindow());
    }

    @Override
    protected void setPresenter() {
        presenter = new ArticlePresenter(disposable);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_article;
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.activity_article_webView);
        multiStateView = findViewById(R.id.activity_article_MultiStateView);
        //加载失败后，点击重新加载
        findViewById(R.id.view_retry_bt).setOnClickListener(v -> {
            presenter.getData(getIntent().getStringExtra("url"));
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        });

        scrollView = findViewById(R.id.activity_article_ObservableScrollView);
        title = findViewById(R.id.activity_article_title);
        relativeLayout = findViewById(R.id.activity_article_relative);
        topRrelativeLayout = findViewById(R.id.activity_article_topRelative);
        //根据scrollView滑动的Y方向上的位移决定显示和隐藏头布局
        scrollView.setScrollViewListener((scrollView, x, scrollY, oldx, oldy) -> {
            if (scrollY > relativeLayout.getHeight()) {
                topRrelativeLayout.setVisibility(View.VISIBLE);
            } else {
                topRrelativeLayout.setVisibility(View.GONE);

            }
        });

        back = findViewById(R.id.activity_article_back);
        topLogo = findViewById(R.id.activity_article_topLogo);
        topName = findViewById(R.id.activity_article_topname);
        topCateName = findViewById(R.id.activity_article_topCateName);
        topButton = findViewById(R.id.activity_article_topButton);
        title = findViewById(R.id.activity_article_title);
        logo = findViewById(R.id.activity_article_logo);
        name = findViewById(R.id.activity_article_name);
        updateTime = findViewById(R.id.activity_article_updateTime);
        button = findViewById(R.id.activity_article_button);
        back.setOnClickListener(v -> finish());

        initWebView();
    }

    @Override
    protected void onLazyLoad() {
        //懒加载，加载 一次后第二次就不会再加载
        //multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
    }

    @Override
    public void loadData(NewsArticleBean articleBean) {
        if (articleBean != null) {
            title.setText(articleBean.getBody().getTitle());
            updateTime.setText(DateUtils.timeAgo(DateUtils.string2Date(articleBean.getBody().getUpdateTime(), "yyyy/MM/dd HH:mm:ss")));

            if (articleBean.getBody().getSubscribe() != null) {
                Glide.with(this).load(articleBean.getBody().getSubscribe().getLogo())
                        .apply(new RequestOptions()
                                .transform(new CircleCrop())
                                //.placeholder()
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(logo);
                Glide.with(this).load(articleBean.getBody().getSubscribe().getLogo())
                        .apply(new RequestOptions()
                                .transform(new CircleCrop())
                                //.placeholder()
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(topLogo);
                topName.setText(articleBean.getBody().getSubscribe().getCateSource());
                name.setText(articleBean.getBody().getSubscribe().getCateSource());
                topCateName.setText(articleBean.getBody().getSubscribe().getCatename());
            } else {
                topName.setText(articleBean.getBody().getSource());
                name.setText(articleBean.getBody().getSource());
                topCateName.setText(!TextUtils.isEmpty(articleBean.getBody().getAuthor()) ? articleBean.getBody().getAuthor() : articleBean.getBody().getEditorcode());
            }

            webView.post(() -> {
                final String content = articleBean.getBody().getText();
                String url = "javascript:show_content(\'" + content + "\')";
                multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                Log.e(TAG, "url: " + url);
                webView.loadUrl(url);
            });

        } else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView.setVerticalScrollBarEnabled(false);
        webView.setVerticalScrollbarOverlay(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setHorizontalScrollbarOverlay(false);
        webView.getSettings().setDomStorageEnabled(true);

        WebSettings webSettings = webView.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //缩放操作
        //支持缩放，默认为true。是下面那个的前提。
        webSettings.setSupportZoom(true);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        webSettings.setDisplayZoomControls(false);
        //其他细节操作
        //设置缓存，根据cache-control决定是否从网络上取数据
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");

        //设置不用系统浏览器打开,直接显示在当前Webview
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                presenter.getData(getIntent().getStringExtra("url"));
            }
        });

        webView.loadUrl("file:///android_asset/ifeng/post_detail.html");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

}
