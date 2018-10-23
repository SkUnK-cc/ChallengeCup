package com.example.hp.challengecup.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
    public static File createLookFile(){
        long timeStamp = System.currentTimeMillis();
        String time = stampToDate(timeStamp);
        String lookFile = getLookDir()+time+".jpg";
        File file = new File(lookFile);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private static String stampToDate(long timeStamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeStamp);
        return simpleDateFormat.format(date);
    }

    private static String getLookDir(){
        String dir = getAppDir()+"/look/";
        return mkdir(dir);
    }

    private static String mkdir(String dir) {
        File file = new File(dir);
        if(!file.exists()){
            file.mkdirs();      //mkdirs若路径不存在，则创建路径并创建文件夹
        }
        return dir;
    }

    private static String getAppDir(){
        return Environment.getExternalStorageDirectory()+"/ChallengeCup";
    }

    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
