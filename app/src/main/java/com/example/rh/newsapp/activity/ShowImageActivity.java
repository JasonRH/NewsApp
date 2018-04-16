package com.example.rh.newsapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rh.newsapp.R;

/**
 * @author RH
 * @date 2018/3/13
 */
public class ShowImageActivity extends AppCompatActivity {
    private static final String TAG = "ShowImageActivity";
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageView = findViewById(R.id.show_Image);

        String url = getIntent().getStringExtra("imageUrl");

        //Picasso.with(this).load(url).into(imageView);
        Glide.with(this).load(url).into(imageView);

        findViewById(R.id.layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
