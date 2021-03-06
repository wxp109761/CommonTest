package com.example.CommonTest.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;

import java.util.Locale;

/**
 * 工具类
 */
public class Utils {

    /**
     * 根据手机的分辨率从dp单位转成为px
     */
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从px单位转成为dp
     */
    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 设置时间格式
     *
     * @param tv          TextView
     * @param millisecond 毫秒数
     */
    public static void updateTimeFormat(TextView tv, int millisecond) {
        int second = millisecond / 1000;
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        tv.setText(String.format(Locale.CHINA, "%02d:%02d:%02d", hh, mm, ss));
    }


    public static void getPer(Context context, Activity activity)
    {
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED)
        {
            // 请求权限
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
//

    }



}
