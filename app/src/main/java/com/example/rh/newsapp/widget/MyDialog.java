package com.example.rh.newsapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.rh.newsapp.R;


/**
 * @author RH
 * @date 2018/1/11
 */
public class MyDialog extends Dialog {
    private TextView yesBtn;
    private TextView noBtn;
    /**消息标题文本*/
    private TextView mTitle;
    /**消息提示文本*/
    private TextView mContent;
    /**从外界设置的title文本*/
    private String titleStr;
    /**从外界设置的消息文本*/
    private String messageStr;
    /**确定文本和取消文本的显示内容*/
    private String yesStr, noStr;
    /**取消按钮被点击了的监听器*/
    private NoOnclickListener noOnClickListener;
    /**确定按钮被点击了的监听器*/
    private YesOnclickListener yesOnclickListener;

    public MyDialog(Context context) {
        super(context, R.style.Theme_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydialog);
        //设置窗体必须放在setContentView（）后面否则不生效
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        // set width,height by density and gravity
        // float density = getDensity(context);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        window.setWindowAnimations(0);
        intView();
        initData();
        initEvent();
    }

    private void intView() {
        mContent = findViewById(R.id.dialog_update_content);
        mTitle = findViewById(R.id.dialog_title);
        yesBtn = findViewById(R.id.dialog_confrim);
        noBtn = findViewById(R.id.dialog_cancel);
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (titleStr != null) {
            mTitle.setText(titleStr);
        }
        if (messageStr != null) {
            mContent.setText(messageStr);
        }
        //如果设置按钮的文字
        if (yesStr != null) {
            yesBtn.setText(yesStr);
        }
        if (noStr != null) {
            noBtn.setText(noStr);
        }
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yesBtn.setOnClickListener(v -> {
            if (yesOnclickListener != null) {
                yesOnclickListener.onYesClick();
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        noBtn.setOnClickListener(v -> {
            if (noOnClickListener != null) {
                noOnClickListener.onNoClick();
            }
        });
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface YesOnclickListener {
         void onYesClick();
    }

    public interface NoOnclickListener {
         void onNoClick();
    }




    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title 弹出框标题显示的文字内容
     */
    public void setTitle(String title) {
        titleStr = title;
    }

    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message 弹出框中间显示的文字内容
     */
    public void setMessage(String message) {
        messageStr = message;
    }


    /**
     *
     * @param str 取消按钮显示的文字内容
     * @param onNoOnClickListener 取消按钮的监听接口
     */
    public void setNoOnClickListener(String str, NoOnclickListener onNoOnClickListener) {
        if (str != null) {
            noStr = str;
        }
        this.noOnClickListener = onNoOnClickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str 确定按钮显示的文字内容
     * @param onYesOnclickListener 确定按钮的监听接口
     */
    public void setYesOnclickListener(String str, YesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
            noOnClickListener.onNoClick();
    }
}
