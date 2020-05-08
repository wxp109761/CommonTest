package com.example.CommonTest.alarmClock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.CommonTest.MainActivity;
import com.example.CommonTest.R;
import com.manu.mdatepicker.MDatePickerDialog;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class alarmActivity extends Activity {
    @BindView(R.id.alarm)
    TimePicker alarm;
    @BindView(R.id.btn_add_clock)
    Button btnAddClock;


    long selecttime=0;
    @OnClick(R.id.btn_add_clock)
    public void onViewClicked() {

        MDatePickerDialog dialog = new MDatePickerDialog.Builder(this)
                //附加设置(非必须,有默认值)

                .setCanceledTouchOutside(true)
                .setGravity(Gravity.BOTTOM)
                .setSupportTime(false)
                .setSupportTime(true)
                .setTitle("日期--时间")
                .setTwelveHour(true)
                .setCanceledTouchOutside(false)
                //结果回调(必须)
                .setOnDateResultListener(new MDatePickerDialog.OnDateResultListener() {
                    @Override
                    public void onDateResult(long date) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(date);
                        SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                        dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
                        Toast.makeText(alarmActivity.this, dateFormat.format(new Date(date)), Toast.LENGTH_SHORT).show();
                        selecttime=date;
                    }
                })
                .build();
        dialog.show();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String logindate2 = simpleDateFormat.format(date);
        String select =  simpleDateFormat.format(new Date(selecttime));
        long triggerTime=TimeCompare(logindate2, select);
        System.out.println(" time"+triggerTime);
        alarm(triggerTime);
    }
    private long TimeCompare(String date1, String date2) {
        long diff = 0;
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        //格式化时间
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date1 = "2015-01-25 09:12:09";
//        String date2 = "2015-01-29 09:12:11";
        try {
            Date beginTime = simpleDateFormat.parse(date1);
            Date endTime = simpleDateFormat.parse(date2);
             diff= endTime.getTime() - beginTime.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return diff;
    }



String path=Environment.getExternalStorageDirectory().getAbsolutePath()+"";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 取消闹钟
     */
    private void cancleAlarm() {
        // Create the same intent, and thus a matching IntentSender, for
        // the one that was scheduled.
        Intent intent = new Intent(alarmActivity.this, RepeatingAlarm.class);
        intent.setAction("com.gcc.alarm");
        PendingIntent sender = PendingIntent.getBroadcast(alarmActivity.this,
                0, intent, 0);

        // And cancel the alarm.
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }

    private void alarm(long triggerTime) {
        // 获取系统的闹钟服务
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // 触发闹钟的时间（毫秒）
        Intent intent = new Intent(this, RepeatingAlarm.class);
        intent.setAction("com.gcc.alarm");
        PendingIntent op = PendingIntent.getBroadcast(this, 0, intent, 0);
        // 启动一次只会执行一次的闹钟
        am.set(AlarmManager.RTC, triggerTime, op);
        // 指定时间重复执行闹钟
        // am.setRepeating(AlarmManager.RTC,triggerTime,2000,op);
    }


}