package com.example.rh.newsapp.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rh.newsapp.MyApplication;
import com.example.rh.newsapp.R;


/**
 * @author RH
 * @date 2018/1/15
 */
public class MyToast {
    private static Toast toast;
    private static Context context;

    public static void initToast(Context mContext) {
        toast = new Toast(mContext);
        context = mContext;
    }

    public static void show(String message) {
        View view = LayoutInflater.from(context).inflate(R.layout.mytoast,null);
        toast.setView(view);
        TextView textView = view.findViewById(R.id.my_toast);
        Typeface typeface = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
        textView.setTypeface(typeface);
        textView.setText(message);
        //toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 80);
        //toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void systemshow(String message ) {
        //int type = true ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        Toast toast1 = Toast.makeText(context,message, Toast.LENGTH_LONG);
       /* View view = LayoutInflater.from(context).inflate(R.layout.mytoast,null);
        toast1.setView(view);
        TextView textView = view.findViewById(R.id.my_toast);
        textView.setText(message);*/
        toast1.show();
    }
}
