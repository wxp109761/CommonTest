package com.example.CommonTest.Notification;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.CommonTest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends Activity {
    @BindView(R.id.normal_notification)
    Button normalNotification;
    @BindView(R.id.custom_notification)
    Button customNotification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            createNotificationChannel("subscribe","订阅消息",NotificationManager.IMPORTANCE_DEFAULT);

        }
     }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick({R.id.normal_notification, R.id.custom_notification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.normal_notification:
            NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = notificationManager.getNotificationChannel("subscribe");
                    if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                        startActivity(intent);
                        Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
                    }
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com/?tn=80035161_1_dg"));

                PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
                Notification notification=new NotificationCompat.Builder(this,"subscribe")
                        .setContentText("你好啊，我是一条通知")
                        .setContentTitle("订阅通知")
                        .setWhen(15000)
                        .setSmallIcon(R.drawable.c_img2)
                        .setAutoCancel(true)
                        .setNumber(3)
                        //设置震动，注意这里需要在AndroidManifest.xml中设置
                        .setVibrate(new long[]{0,300,500,700})
                        //设置默认的三色灯与振动器
                        .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                        //设置LED闪烁
                        .setLights(Color.BLUE,2000,1000)
                        .setContentIntent(pi)
                        .build();
                notificationManager.notify(1,notification);
                break;
            case R.id.custom_notification:
                break;
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        channel.setShowBadge(true);
        notificationManager.createNotificationChannel(channel);
    }
}
