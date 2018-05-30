package com.example.rh.newsapp.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.rh.newsapp.R;
import com.example.rh.newsapp.utils.FileUtils;

import java.io.File;
import java.util.List;

import static com.example.rh.newsapp.utils.FileUtils.getFilesSize;

/**
 * @author RH
 * @date 2018/5/28
 */
public class MyFileAdapter extends BaseQuickAdapter<File, BaseViewHolder> {
    private Activity activity;
    private Bitmap bmpFolder, bmpFile, bmpDoc, bmpJpg, bmpMovie, bmpMusic, bmpPdf,
            bmpTxt, bmpZip, bmpXls, bmpPpt, bmpPng, bmpRtf, bmpGif, bmpApk;

    public MyFileAdapter(Activity activity, List<File> files) {
        super(R.layout.adapter_file_item, files);
        this.activity = activity;
        //文件夹,decodeResource图片解码，source资源，解码为Bitmap类型；
        bmpFolder = FileUtils.readBitmapFromResource(activity, R.mipmap.folder);
        //文件
        bmpFile = FileUtils.readBitmapFromResource(activity, R.mipmap.whitepage32);
        bmpDoc = FileUtils.readBitmapFromResource(activity, R.mipmap.doc);
        bmpJpg = FileUtils.readBitmapFromResource(activity, R.mipmap.jpg32);
        bmpMovie = FileUtils.readBitmapFromResource(activity, R.mipmap.movie);
        bmpMusic = FileUtils.readBitmapFromResource(activity, R.mipmap.music);
        bmpPdf = FileUtils.readBitmapFromResource(activity, R.mipmap.pdf);
        bmpTxt = FileUtils.readBitmapFromResource(activity, R.mipmap.txt32);
        bmpZip = FileUtils.readBitmapFromResource(activity, R.mipmap.zip32);
        bmpXls = FileUtils.readBitmapFromResource(activity, R.mipmap.xls);
        bmpPpt = FileUtils.readBitmapFromResource(activity, R.mipmap.ppt);
        bmpPng = FileUtils.readBitmapFromResource(activity, R.mipmap.png32);
        bmpRtf = FileUtils.readBitmapFromResource(activity, R.mipmap.rtf32);
        bmpGif = FileUtils.readBitmapFromResource(activity, R.mipmap.gif32);
        bmpApk = FileUtils.readBitmapFromResource(activity, R.mipmap.android32);
    }

    @Override
    protected void convert(BaseViewHolder helper, File f) {
        helper.setText(R.id.text_Name, f.getName());
        helper.setText(R.id.text_Size, getFilesSize(f));
        //将字符串转换成小写
        String str = f.getName().toLowerCase();

        if (f.isDirectory()) {
            helper.setImageBitmap(R.id.image_ico, bmpFolder);
        } else if ((str.endsWith(".xls")) || (str.endsWith(".xlsx"))) {
            helper.setImageBitmap(R.id.image_ico, bmpXls);
        } else if ((str.endsWith(".doc")) || (str.endsWith(".docx"))) {
            helper.setImageBitmap(R.id.image_ico, bmpDoc);
        } else if ((str.endsWith(".ppt")) || (f.getName().endsWith(".pptx"))) {
            helper.setImageBitmap(R.id.image_ico, bmpPpt);
        } else if (str.endsWith(".pdf")) {
            helper.setImageBitmap(R.id.image_ico, bmpPdf);
        } else if (str.endsWith(".apk")) {
            helper.setImageBitmap(R.id.image_ico, bmpApk);
        } else if (str.endsWith(".txt")) {
            helper.setImageBitmap(R.id.image_ico, bmpTxt);
        } else if ((str.endsWith(".jpg")) || (str.endsWith(".jpeg"))) {
            helper.setImageBitmap(R.id.image_ico, bmpJpg);
        } else if (str.endsWith(".png")) {
            helper.setImageBitmap(R.id.image_ico, bmpPng);
        } else if (str.endsWith(".zip")) {
            helper.setImageBitmap(R.id.image_ico, bmpZip);
        } else if (str.endsWith(".rtf")) {
            helper.setImageBitmap(R.id.image_ico, bmpRtf);
        } else if (str.endsWith(".gif")) {
            helper.setImageBitmap(R.id.image_ico, bmpGif);
        } else if ((str.endsWith(".mp4")) || (str.endsWith(".mkv")) || (str.endsWith(".avi")) || (str.endsWith(".mov")) || (str.endsWith(".webm")) || (str.endsWith(".3gp"))) {
            helper.setImageBitmap(R.id.image_ico, bmpMovie);
        } else if ((str.endsWith(".mp3")) || (str.endsWith(".wav"))) {
            helper.setImageBitmap(R.id.image_ico, bmpMusic);
        } else {
            helper.setImageBitmap(R.id.image_ico, bmpFile);
        }
    }

    public void recycleBitmap() {
        if (bmpFolder != null) {
            bmpFolder.recycle();
            bmpFolder = null;
        }
        if (bmpFile != null) {
            bmpFile.recycle();
            bmpFile = null;
        }
        if (bmpDoc != null) {
            bmpDoc.recycle();
            bmpDoc = null;
        }
        if (bmpJpg != null) {
            bmpJpg.recycle();
            bmpJpg = null;
        }
        if (bmpMovie != null) {
            bmpMovie.recycle();
            bmpMovie = null;
        }
        if (bmpMusic != null) {
            bmpMusic.recycle();
            bmpMusic = null;
        }
        if (bmpPdf != null) {
            bmpPdf.recycle();
            bmpPdf = null;
        }
        if (bmpTxt != null) {
            bmpTxt.recycle();
            bmpTxt = null;
        }
        if (bmpZip != null) {
            bmpZip.recycle();
            bmpZip = null;
        }
        if (bmpXls != null) {
            bmpXls.recycle();
            bmpXls = null;
        }
        if (bmpPpt != null) {
            bmpPpt.recycle();
            bmpPpt = null;
        }
        if (bmpPng != null) {
            bmpPng.recycle();
            bmpPng = null;
        }
        if (bmpRtf != null) {
            bmpRtf.recycle();
            bmpRtf = null;
        }
        if (bmpGif != null) {
            bmpGif.recycle();
            bmpGif = null;
        }
        if (bmpApk != null) {
            bmpApk.recycle();
            bmpApk = null;
        }
    }

}
