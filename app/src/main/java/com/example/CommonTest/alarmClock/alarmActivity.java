package com.example.CommonTest.alarmClock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.CommonTest.MainActivity;
import com.example.CommonTest.R;
import com.manu.mdatepicker.MDatePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class alarmActivity extends Activity {
    @BindView(R.id.alarm)
    TimePicker alarm;
    @BindView(R.id.btn_add_clock)
    Button btnAddClock;
    @BindView(R.id.btn_add_notice)
    Button btnAddNotice;

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
            diff = endTime.getTime() - beginTime.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return diff;
    }
    NotificationManager mNotificationManager;

    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alarm);
      //  mNotificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private ClockManager mClockManager = ClockManager.getInstance();

    @OnClick({R.id.btn_add_clock, R.id.btn_add_notice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_clock:

                Intent intent = new Intent();
              //  intent.putExtra(ClockReceiver.EXTRA_EVENT_ID, id);
                intent.setClass(this, ClockService.class);

               PendingIntent pendingIntent= PendingIntent.getService(this, 0x001, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                MDatePickerDialog dialog = new MDatePickerDialog.Builder(this)
                        //附加设置(非必须,有默认值)
                        .setCanceledTouchOutside(true)
                        .setGravity(Gravity.BOTTOM)
                        .setSupportTime(false)
                        .setSupportTime(true)
                        .setTitle("日期--时间")
                        .setTwelveHour(false)
                        .setCanceledTouchOutside(true)
                        //结果回调(必须)
                        .setOnDateResultListener(new MDatePickerDialog.OnDateResultListener() {
                            @Override
                            public void onDateResult(long date) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(date);
                                SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
                                Toast.makeText(alarmActivity.this, dateFormat.format(new Date(date)), Toast.LENGTH_SHORT).show();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date currentdate = new Date(System.currentTimeMillis());
                                String logindate2 = simpleDateFormat.format(currentdate);
                                String select = simpleDateFormat.format(new Date(date));
                                long triggerTime = TimeCompare(logindate2, select);
                                System.out.println(" time" + triggerTime);


                                mClockManager.addAlarm(pendingIntent, DateTimeUtil.str2Date(select));
                            }
                        })
                        .build();
                dialog.show();
                break;
            case R.id.btn_add_notice:
//                Notification.Builder builder=new Notification.Builder(this);
//                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com/"));
//                PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
//                builder.setContentIntent(pendingIntent);
//                builder.setSmallIcon(R.mipmap.ic_launcher);
//                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
//                builder.setAutoCancel(true);
//                builder.setContentTitle("普通通知");
//                mNotificationManager.notify(1, builder.build());



                //AlarmManagerUtils.setAlarm(MainActivity.this, 0, mHour, mMinute, 0, 0, 0, "提醒时间到了", mSoundOrVibrator);

                break;
        }
    }






}
