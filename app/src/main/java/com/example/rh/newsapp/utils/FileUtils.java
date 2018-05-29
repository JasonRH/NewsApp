package com.example.rh.newsapp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author RH
 * @date 2018/5/28
 */
public class FileUtils {

    public static String currPath = "";

    public static List<File> scanFiles(String path) {
        //数据源（文件列表）
        List<File> list = new LinkedList<>();
        File dir = new File(path);
        File[] subFiles = dir.listFiles();
        if (subFiles != null) {
            Collections.addAll(list, subFiles);
        }
        currPath = path;
        return list;
    }


    public static String getFilesSize(File f) {
        int index;
        String show = "";
        if (f.isFile()) {
            long length = f.length();
            if (length >= 1073741824) {
                index = String.valueOf((float) length / 1073741824).indexOf(".");
                show = ((float) length / 1073741824 + "000").substring(0, index + 3) + "GB";
            } else if (length >= 1048576) {
                index = (String.valueOf((float) length / 1048576)).indexOf(".");
                show = ((float) length / 1048576 + "000").substring(0, index + 3) + "MB";
            } else if (length >= 1024) {
                index = (String.valueOf((float) length / 1024)).indexOf(".");
                show = ((float) length / 1024 + "000").substring(0, index + 3) + "KB";
            } else if (length < 1024) {
                show = String.valueOf(length) + "B";
            }
        }
        return show;
    }

    /**
     * 打开文件
     */
    public static void openFile(String paramString1, String paramString2, Context context) {
        Intent localIntent = new Intent("android.intent.action.VIEW");
        Uri localUri = Uri.fromFile(new File(paramString1));
        String str = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(localUri.toString()));

        try {
            if ((paramString2.endsWith(".jpg")) || (paramString2.endsWith(".jpeg"))) {
                localIntent.setDataAndType(localUri, "image/jpeg");
            } else if (paramString2.endsWith(".3gp")) {
                localIntent.setDataAndType(localUri, "video/3gpp");
            } else if (paramString2.endsWith(".webm")) {
                localIntent.setDataAndType(localUri, "video/webm");
            } else if (paramString2.endsWith(".mkv")) {
                localIntent.setDataAndType(localUri, "video/x-matroska");
            } else {
                if (str == null) {
                    str = "*/*";
                }
                localIntent.setDataAndType(localUri, str);
            }
            context.startActivity(localIntent);
        } catch (Exception localException) {
            Toast.makeText(context, "打开失败", Toast.LENGTH_SHORT).show();
        }


    }


    /**
     *  
     *  * 以最省内存的方式读取本地资源的图片 
     *  * @param context 
     *  * @param resId 
     *  * @return 
     *  
     */
    public static Bitmap readBitmapFromResource(Context context, int resourcesId) {
        //获取资源图片
        InputStream ins = context.getResources().openRawResource(resourcesId);
        BitmapFactory.Options options = new BitmapFactory.Options();
        int inSampleSize = 1;
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = true;
        return BitmapFactory.decodeStream(ins, null, options);
    }
}
