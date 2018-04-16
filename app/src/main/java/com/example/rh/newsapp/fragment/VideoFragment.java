package com.example.rh.newsapp.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.rh.newsapp.R;


/**
 * @author RH
 */
public class VideoFragment extends Fragment {

    private static final String TAG = "VideoFragment";
    public static VideoFragment videoFragment;

    public static VideoFragment getInstance() {
        if (videoFragment == null) {
            videoFragment = new VideoFragment();
        }
        return videoFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        initView(view);
        return view;
    }

    private void initView(final View view) {
    }

}
